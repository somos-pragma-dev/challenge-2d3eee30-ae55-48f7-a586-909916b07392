package com.banco.digital.abilities;

import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Ability;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BaseAbility;
import net.thucydides.core.util.EnvironmentVariables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static net.serenitybdd.rest.RestRequests.given;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.*;

public class UsarServicioBuro extends BaseAbility implements Ability {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsarServicioBuro.class);
    private static final String PROPERTY_BURO_URL = "api.buro.url";
    private static final String PROPERTY_TIMEOUT = "timeout.buro";
    private static final int DEFAULT_TIMEOUT_MS = 2000;
    private static final int PUNTUACION_MINIMA_APROBACION = 650;
    private static final int VENTANA_DUPLICADO_HORAS = 24;

    private final String baseUrl;
    private final int timeoutMs;
    private final EnvironmentVariables environmentVariables;
    private final Map<String, ConsultaBuroCache> cacheConsultas;

    public UsarServicioBuro(EnvironmentVariables environmentVariables) {
        this.environmentVariables = environmentVariables;
        this.baseUrl = Optional.ofNullable(environmentVariables.getProperty(PROPERTY_BURO_URL))
                .orElseThrow(() -> new IllegalStateException("Property " + PROPERTY_BURO_URL + " not configured"));
        this.timeoutMs = Optional.ofNullable(environmentVariables.getProperty(PROPERTY_TIMEOUT))
                .map(Integer::parseInt)
                .orElse(DEFAULT_TIMEOUT_MS);
        this.cacheConsultas = new HashMap<>();
        LOGGER.info("Ability UsarServicioBuro inicializada con URL: {} y timeout: {}ms", baseUrl, timeoutMs);
    }

    public static UsarServicioBuro configurado(EnvironmentVariables environmentVariables) {
        return new UsarServicioBuro(environmentVariables);
    }

    public static UsarServicioBuro como(Actor actor) {
        return actor.abilityTo(UsarServicioBuro.class);
    }

    public ResultadoConsultaBuro consultarPuntuacion(String clienteId) {
        LOGGER.info("Consultando puntuacion en buró para cliente: {}", clienteId);
        validarParametroRequerido(clienteId, "clienteId");
        verificarDuplicadoReciente(clienteId);

        long inicio = System.currentTimeMillis();
        try {
            var respuesta = given()
                    .baseUri(baseUrl)
                    .header("Content-Type", "application/json")
                    .header("X-Request-ID", generarIdRequest())
                    .body(construirPayloadConsulta(clienteId))
                    .timeout(Duration.ofMillis(timeoutMs))
                    .when()
                    .post("/consulta")
                    .then()
                    .statusCode(anyOf(is(200), is(201)))
                    .body("clienteId", equalTo(clienteId))
                    .extract()
                    .body();

            long duracion = System.currentTimeMillis() - inicio;
            LOGGER.info("Consulta de buró completada en {}ms para cliente: {}", duracion, clienteId);

            if (duracion > timeoutMs) {
                LOGGER.warn("Tiempo de respuesta ({}) excede el timeout configurado ({})", duracion, timeoutMs);
            }

            Integer puntuacion = respuesta.jsonPath().getInt("puntuacion");
            String nivelRiesgo = respuesta.jsonPath().getString("nivelRiesgo");
            Boolean tieneDeudasVencidas = respuesta.jsonPath().getBoolean("tieneDeudasVencidas");

            ResultadoConsultaBuro resultado = new ResultadoConsultaBuro(
                    clienteId,
                    puntuacion,
                    nivelRiesgo,
                    tieneDeudasVencidas,
                    duracion,
                    true
            );

            cacheConsultas.put(clienteId, new ConsultaBuroCache(clienteId, resultado));
            return resultado;

        } catch (Exception e) {
            long duracion = System.currentTimeMillis() - inicio;
            LOGGER.error("Error al consultar buró para cliente {}: {}", clienteId, e.getMessage());
            throw new RuntimeException("Error en consulta al buró de crédito: " + e.getMessage(), e);
        }
    }

    public boolean tienePuntuacionAprobada(Integer puntuacion) {
        if (puntuacion == null) {
            LOGGER.warn("Puntuación nula, no puede ser aprobada");
            return false;
        }
        boolean aprobada = puntuacion >= PUNTUACION_MINIMA_APROBACION;
        LOGGER.debug("Puntuación {} evaluada como aprobada: {}", puntuacion, aprobada);
        return aprobada;
    }

    public boolean esDuplicado(String clienteId) {
        return cacheConsultas.containsKey(clienteId);
    }

    private void verificarDuplicadoReciente(String clienteId) {
        if (cacheConsultas.containsKey(clienteId)) {
            ConsultaBuroCache cache = cacheConsultas.get(clienteId);
            if (cache.estaDentroVentana(VENTANA_DUPLICADO_HORAS)) {
                LOGGER.warn("Detectada consulta duplicada para cliente {} dentro de ventana de {} horas",
                        clienteId, VENTANA_DUPLICADO_HORAS);
            }
        }
    }

    private void validarParametroRequerido(String valor, String nombreParametro) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("Parámetro requerido: " + nombreParametro);
        }
    }

    private String generarIdRequest() {
        return "REQ-" + System.currentTimeMillis() + "-" + (int)(Math.random() * 10000);
    }

    private Map<String, Object> construirPayloadConsulta(String clienteId) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("clienteId", clienteId);
        payload.put("tipoConsulta", "AMPLIADA");
        payload.put("fechaConsulta", java.time.LocalDateTime.now().toString());
        return payload;
    }

    public static class ResultadoConsultaBuro {
        private final String clienteId;
        private final Integer puntuacion;
        private final String nivelRiesgo;
        private final Boolean tieneDeudasVencidas;
        private final long tiempoRespuestaMs;
        private final boolean exitosa;

        public ResultadoConsultaBuro(String clienteId, Integer puntuacion, String nivelRiesgo,
                                    Boolean tieneDeudasVencidas, long tiempoRespuestaMs, boolean exitosa) {
            this.clienteId = clienteId;
            this.puntuacion = puntuacion;
            this.nivelRiesgo = nivelRiesgo;
            this.tieneDeudasVencidas = tieneDeudasVencidas;
            this.tiempoRespuestaMs = tiempoRespuestaMs;
            this.exitosa = exitosa;
        }

        public String getClienteId() { return clienteId; }
        public Integer getPuntuacion() { return puntuacion; }
        public String getNivelRiesgo() { return nivelRiesgo; }
        public Boolean getTieneDeudasVencidas() { return tieneDeudasVencidas; }
        public long getTiempoRespuestaMs() { return tiempoRespuestaMs; }
        public boolean isExitosa() { return exitosa; }
    }

    private static class ConsultaBuroCache {
        private final String clienteId;
        private final ResultadoConsultaBuro resultado;
        private final long timestamp;

        public ConsultaBuroCache(String clienteId, ResultadoConsultaBuro resultado) {
            this.clienteId = clienteId;
            this.resultado = resultado;
            this.timestamp = System.currentTimeMillis();
        }

        public boolean estaDentroVentana(int horas) {
            long ventanaMs = horas * 60 * 60 * 1000L;
            return (System.currentTimeMillis() - timestamp) < ventanaMs;
        }
    }
}