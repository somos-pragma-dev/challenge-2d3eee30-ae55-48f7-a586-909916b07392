package com.banco.digital.tasks;

import com.banco.digital.abilities.UsarServicioBuro;
import com.banco.digital.models.SolicitudProducto;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.questions.LastResponse;
import net.serenitybdd.screenplay.rest.questions.RestQuestion;
import net.thucydides.core.annotations.Step;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.CoreMatchers.*;

public class VerificarDatosEnBuro implements Task {

    private static final int TIMEOUT_SECONDS = 2;
    private static final int MAX_REINTENTOS = 3;
    private static final int PUNTUACION_MINIMA_APROBACION = 650;

    private final String solicitudId;
    private String puntuacionBuro;
    private boolean timeoutOcurrido = false;
    private boolean respuestaFallida = false;
    private int intentos = 0;

    public VerificarDatosEnBuro(String solicitudId) {
        this.solicitudId = solicitudId;
    }

    public static VerificarDatosEnBuro paraSolicitud(String solicitudId) {
        return new VerificarDatosEnBuro(solicitudId);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.can(UsarServicioBuro.conEndpoint("https://buro-credito.banco-digital.com/api"));

        do {
            intentos++;
            try {
                Map<String, Object> cuerpoPeticion = new HashMap<>();
                cuerpoPeticion.put("solicitudId", solicitudId);
                cuerpoPeticion.put("tipoConsulta", "historico-completo");
                cuerpoPeticion.put("timestamp", LocalDateTime.now().toString());

                actor.attemptsTo(
                    RestQuestion.about("/consulta-buro")
                        .withRequest(request -> request
                            .header("Content-Type", "application/json")
                            .header("X-Transaction-ID", "buro-" + System.currentTimeMillis())
                            .body(cuerpoPeticion))
                        .setTimeout(Duration.ofSeconds(TIMEOUT_SECONDS))
                );

                String respuesta = LastResponse.received().answeredBy(actor).asString();

                actor.should(seeThat("Código de respuesta del buró",
                    LastResponse.statusCode(), is(200)));

                if (respuesta.contains(""puntuacion":")) {
                    String puntuacionExtraida = respuesta.split("\"puntuacion\"")[1].split("[,}")[0].replace("\"", "").replace(":", "");
                    this.puntuacionBuro = puntuacionExtraida.trim();
                    this.timeoutOcurrido = false;
                    this.respuestaFallida = false;
                } else {
                    this.respuestaFallida = true;
                }

            } catch (Exception e) {
                String mensajeError = e.getMessage();
                if (mensajeError.contains("timeout") || mensajeError.contains("Read timed out")) {
                    this.timeoutOcurrido = true;
                    this.respuestaFallida = true;
                } else {
                    this.respuestaFallida = true;
                }
            }

        } while ((timeoutOcurrido || respuestaFallida) && intentos < MAX_REINTENTOS);

        if (timeoutOcurrido && intentos >= MAX_REINTENTOS) {
            throw new RuntimeException(
                "Timeout del servicio de buró de crédito después de " + MAX_REINTENTOS + " intentos. " +
                "Tiempo máximo de espera: " + (TIMEOUT_SECONDS * MAX_REINTENTOS) + " segundos."
            );
        }

        if (respuestaFallida) {
            throw new RuntimeException(
                "Respuesta fallida del servicio de buró de crédito después de " + intentos + " intentos."
            );
        }

        SolicitudProducto solicitud = actor.recall("solicitudActual");
        if (solicitud != null) {
            solicitud.setPuntuacionBuro(Integer.parseInt(this.puntuacionBuro));
            solicitud.setFechaActualizacion(LocalDateTime.now());
            solicitud.incrementarIntentos();
            actor.remember("solicitudActual", solicitud);
        }

        actor.should(seeThat("Puntuación del buró procesada",
            () -> this.puntuacionBuro, notNullValue()));

        actor.should(seeThat("Timeout no ocurrido",
            () -> !this.timeoutOcurrido, is(true)));
    }

    public String getPuntuacionBuro() {
        return this.puntuacionBuro;
    }

    public boolean isTimeoutOcurrido() {
        return this.timeoutOcurrido;
    }

    public boolean isRespuestaFallida() {
        return this.respuestaFallida;
    }

    public int getIntentos() {
        return this.intentos;
    }
}