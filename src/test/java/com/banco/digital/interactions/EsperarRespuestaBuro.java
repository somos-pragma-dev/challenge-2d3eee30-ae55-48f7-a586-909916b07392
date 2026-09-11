package com.banco.digital.interactions;

import com.banco.digital.abilities.UsarServicioBuro;
import com.banco.digital.models.SolicitudProducto;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class EsperarRespuestaBuro implements Interaction {

    private static final Duration TIMEOUT_MAXIMO = Duration.ofSeconds(2);
    private static final int MAX_REINTENTOS = 3;

    private final String solicitudId;
    private final String clienteId;

    public EsperarRespuestaBuro(String solicitudId, String clienteId) {
        this.solicitudId = solicitudId;
        this.clienteId = clienteId;
    }

    public static EsperarRespuestaBuro por(String solicitudId, String clienteId) {
        return Tasks.instrumented(EsperarRespuestaBuro.class, solicitudId, clienteId);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        UsarServicioBuro servicioBuro = actor.abilityTo(UsarServicioBuro.class);
        
        if (servicioBuro == null) {
            throw new net.serenitybdd.core.exceptions.SerenityException(
                "El actor no tiene la habilidad de usar el servicio del buró de crédito"
            );
        }

        LocalDateTime tiempoInicio = LocalDateTime.now();
        int intentos = 0;
        SolicitudProducto respuesta = null;

        while (intentos < MAX_REINTENTOS && respuesta == null) {
            intentos++;
            Serenity.recordReportData()
                .withTitle("Intento de consulta al buró")
                .andContents("Intento " + intentos + " de " + MAX_REINTENTOS + " para solicitud: " + solicitudId);

            try {
                respuesta = servicioBuro.consultarScore(solicitudId, clienteId);
                
                if (respuesta == null) {
                    Duration tiempoTranscurrido = Duration.between(tiempoInicio, LocalDateTime.now());
                    if (tiempoTranscurrido.compareTo(TIMEOUT_MAXIMO) >= 0) {
                        throw new net.serenitybdd.core.exceptions.SerenityException(
                            "Timeout excedido esperando respuesta del buró de crédito. " +
                            "Tiempo transcurrido: " + tiempoTranscurrido.getSeconds() + " segundos"
                        );
                    }
                    
                    long tiempoRestante = TIMEOUT_MAXIMO.toMillis() - tiempoTranscurrido.toMillis();
                    tiempoRestante = Math.max(100, Math.min(tiempoRestante, 500));
                    
                    try {
                        Thread.sleep(tiempoRestante);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        throw new net.serenitybdd.core.exceptions.SerenityException(
                            "Hilo interrumpido mientras esperaba respuesta del buró", e
                        );
                    }
                }
            } catch (Exception e) {
                if (intentos >= MAX_REINTENTOS) {
                    throw new net.serenitybdd.core.exceptions.SerenityException(
                        "Error al consultar buró después de " + MAX_REINTENTOS + " intentos: " + e.getMessage(),
                        e
                    );
                }
                
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new net.serenitybdd.core.exceptions.SerenityException(
                        "Hilo interrumpido durante reintento", ie
                    );
                }
            }
        }

        if (respuesta == null) {
            Duration tiempoTotal = Duration.between(tiempoInicio, LocalDateTime.now());
            throw new net.serenitybdd.core.exceptions.SerenityException(
                "No se recibió respuesta del buró de crédito después de " + intentos + " intentos. " +
                "Tiempo total: " + tiempoTotal.getSeconds() + " segundos"
            );
        }

        actor.remember("respuestaBuro", respuesta);
        actor.remember("puntuacionBuro", respuesta.getPuntuacionBuro());
        
        Serenity.recordReportData()
            .withTitle("Respuesta del buró de crédito")
            .andContents("Solicitud: " + solicitudId + ", Score: " + respuesta.getPuntuacionBuro());
    }
}