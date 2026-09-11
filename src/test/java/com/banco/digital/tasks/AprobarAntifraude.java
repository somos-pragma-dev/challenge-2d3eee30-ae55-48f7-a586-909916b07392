package com.banco.digital.tasks;

import com.banco.digital.abilities.UsarServicioAntifraude;
import com.banco.digital.models.SolicitudProducto;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.questions.LastResponse;
import net.serenitybdd.screenplay.rest.questions.RestQuestion;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.CoreMatchers.*;

public class AprobarAntifraude implements Task {

    private static final int TIMEOUT_SECONDS = 2;
    private static final int MAX_REINTENTOS = 3;

    private final String solicitudId;
    private boolean aprobacionConcedida = false;
    private String codigoRespuesta;
    private String mensajeRespuesta;
    private int intentos = 0;

    public AprobarAntifraude(String solicitudId) {
        this.solicitudId = solicitudId;
    }

    public static AprobarAntifraude paraSolicitud(String solicitudId) {
        return new AprobarAntifraude(solicitudId);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.can(UsarServicioAntifraude.conEndpoint("https://antifraude.banco-digital.com/api"));

        do {
            intentos++;
            try {
                Map<String, Object> cuerpoPeticion = new HashMap<>();
                cuerpoPeticion.put("solicitudId", solicitudId);
                cuerpoPeticion.put("tipoEvaluacion", "evaluacion-completa");
                cuerpoPeticion.put("timestamp", LocalDateTime.now().toString());
                cuerpoPeticion.put("origen", "sistema-apertura-productos");

                actor.attemptsTo(
                    RestQuestion.about("/evaluar-solicitud")
                        .withRequest(request -> request
                            .header("Content-Type", "application/json")
                            .header("X-Transaction-ID", "antifraude-" + System.currentTimeMillis())
                            .header("X-Request-Source", "bdd-tests")
                            .body(cuerpoPeticion))
                        .setTimeout(Duration.ofSeconds(TIMEOUT_SECONDS))
                );

                String respuesta = LastResponse.received().answeredBy(actor).asString();
                int codigoEstado = LastResponse.statusCode().answeredBy(actor);
                this.codigoRespuesta = String.valueOf(codigoEstado);

                actor.should(seeThat("Código de respuesta de antifraude",
                    LastResponse.statusCode(), anyOf(is(200), is(201))));

                if (respuesta.contains(""aprobado":")) {
                    String aprobado = respuesta.split("\"aprobado\"")[1].split("[,}")[0].replace("\"", "").replace(":", "").trim();
                    this.aprobacionConcedida = "true".equalsIgnoreCase(aprobado);
                    this.mensajeRespuesta = extraerMensaje(respuesta);
                } else if (respuesta.contains(""estado":")) {
                    String estado = respuesta.split("\"estado\"")[1].split("[,}")[0].replace("\"", "").replace(":", "").trim();
                    this.aprobacionConcedida = "APROBADO".equalsIgnoreCase(estado) || "APROBADA".equalsIgnoreCase(estado);
                    this.mensajeRespuesta = "Estado: " + estado;
                } else {
                    this.aprobacionConcedida = false;
                    this.mensajeRespuesta = "Respuesta sin formato esperado";
                }

            } catch (Exception e) {
                this.aprobacionConcedida = false;
                this.codigoRespuesta = "ERROR";
                this.mensajeRespuesta = "Excepción: " + e.getMessage();
            }

        } while (!this.aprobacionConcedida && intentos < MAX_REINTENTOS);

        if (!this.aprobacionConcedida && intentos >= MAX_REINTENTOS) {
            throw new RuntimeException(
                "Solicitud rechazada por motor antifraude después de " + MAX_REINTENTOS + " intentos. " +
                "Última respuesta: " + this.mensajeRespuesta
            );
        }

        SolicitudProducto solicitud = actor.recall("solicitudActual");
        if (solicitud != null) {
            solicitud.setAprobacionAntifraude(this.aprobacionConcedida);
            solicitud.setFechaActualizacion(LocalDateTime.now());
            solicitud.incrementarIntentos();
            actor.remember("solicitudActual", solicitud);
        }

        actor.should(seeThat("Aprobación antifraude concedida",
            () -> this.aprobacionConcedida, is(true)));

        actor.should(seeThat("Intentos realizados",
            () -> this.intentos, lessThanOrEqualTo(MAX_REINTENTOS)));
    }

    private String extraerMensaje(String respuesta) {
        if (respuesta.contains(""mensaje":")) {
            return respuesta.split("\"mensaje\"")[1].split("[,}")[0].replace("\"", "").trim();
        }
        if (respuesta.contains(""descripcion":")) {
            return respuesta.split("\"descripcion\"")[1].split("[,}")[0].replace("\"", "").trim();
        }
        return "Sin mensaje disponible";
    }

    public boolean isAprobacionConcedida() {
        return this.aprobacionConcedida;
    }

    public String getCodigoRespuesta() {
        return this.codigoRespuesta;
    }

    public String getMensajeRespuesta() {
        return this.mensajeRespuesta;
    }

    public int getIntentos() {
        return this.intentos;
    }
}