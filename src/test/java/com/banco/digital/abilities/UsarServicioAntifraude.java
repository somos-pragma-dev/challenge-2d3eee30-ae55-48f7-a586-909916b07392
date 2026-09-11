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
import java.util.concurrent.ConcurrentHashMap;

import static net.serenitybdd.rest.RestRequests.given;

public class UsarServicioAntifraude extends BaseAbility implements Ability {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsarServicioAntifraude.class);
    private static final String PROPERTY_ANTIFRAUDE_URL = "api.antifraude.url";
    private static final String PROPERTY_TIMEOUT = "timeout.antifraude";
    private static final int DEFAULT_TIMEOUT_MS = 2000;
    private static final int NIVEL_RIESGO_MAXIMO = 75;

    private final String baseUrl;
    private final int timeoutMs;
    private final EnvironmentVariables environmentVariables;
    private final Map<String, ResultadoAntifraudeCache> cacheResultados;
    private final Map<String, ContadorSolicitudes> contadores;

    public UsarServicioAntifraude(EnvironmentVariables environmentVariables) {
        this.environmentVariables = environmentVariables;
        this.baseUrl = Optional.ofNullable(environmentVariables.getProperty(PROPERTY_ANTIFRAUDE_URL))
                .orElseThrow(() -> new IllegalStateException("Property " + PROPERTY_ANTIFRAUDE_URL + " not configured"));
        this.timeoutMs = Optional.ofNullable(environmentVariables.getProperty(PROPERTY_TIMEOUT))
                .map(Integer::parseInt)
                .orElse(DEFAULT_TIMEOUT_MS);
        this.cacheResultados = new ConcurrentHashMap<>();
        this.contadores = new ConcurrentHashMap<>();
        LOGGER.info("Ability UsarServicioAntifraude inicializada con URL: {} y timeout: {}ms", baseUrl, timeoutMs);
    }

    public static UsarServicioAntifraude configurado(EnvironmentVariables environmentVariables) {
        return new UsarServicioAntifraude(environmentVariables);
    }

    public static UsarServicioAntifraude como(Actor actor) {
        return actor.abilityTo(UsarServicioAntifraude.class);
    }

    public ResultadoVerificacionAntifraude verificarSolicitud(String clienteId, String productoId, String identificadorSesion) {
        LOGGER.info("Verificando solicitud en antifraude para cliente: {}, producto: {}", clienteId, productoId);
        validarParametrosRequeridos(clienteId, productoId);
        registrar Solicitud(identificadorSesion);

        long inicio = System.currentTimeMillis();
        try {
            var respuesta = given()
                    .baseUri(baseUrl)
                    .header("Content-Type", "application/json")
                    .header("X-Request-ID", generarIdRequest())
                    .header("X-Session-ID", identificadorSesion)
                    .body(construirPayloadVerificacion(clienteId, productoId, identificadorSesion))
                    .timeout(Duration.ofMillis(timeoutMs))
                    .when()
                    .post("/verificar")
                    .then()
                    .statusCode(anyOf(is(200), is(201)))
                    .body("clienteId", equalTo(clienteId))
                    .extract()
                    .body();

            long duracion = System.currentTimeMillis() - inicio;
            LOGGER.info("Verificación antifraude completada en {}ms para cliente: {}", duracion, clienteId);

            if (duracion > timeoutMs) {
                LOGGER.warn("Tiempo de respuesta ({}) excede el timeout configurado ({})", duracion, timeoutMs);
            }

            Boolean aprobado = respuesta.jsonPath().getBoolean("aprobado");
            Integer nivelRiesgo = respuesta.jsonPath().getInt("nivelRiesgo");
            String motivo = respuesta.jsonPath().getString("motivo");
            Boolean requiereRevisionManual = respuesta.jsonPath().getBoolean("requiereRevisionManual");

            ResultadoVerificacionAntifraude resultado = new ResultadoVerificacionAntifraude(
                    clienteId,
                    productoId,
                    aprobado,
                    nivelRiesgo,
                    motivo,
                    requiereRevisionManual,
                    duracion,
                    true
            );

            String keyCache = clienteId + "-" + productoId;
            cacheResultados.put(keyCache, new ResultadoAntifraudeCache(keyCache, resultado));
            return resultado;

        } catch (Exception e) {
            long duracion = System.currentTimeMillis() - inicio;
            LOGGER.error("Error en verificación antifraude para cliente {}: {}", clienteId, e.getMessage());
            throw new RuntimeException("Error en verificación antifraude: " + e.getMessage(), e);
        }
    }

    public boolean estaAprobadoNivelRiesgo(Integer nivelRiesgo) {
        if (nivelRiesgo == null) {
            LOGGER.warn("Nivel de riesgo nulo, no puede ser aprobado");
            return false;
        }
        boolean aprobado = nivelRiesgo <= NIVEL_RIESGO_MAXIMO;
        LOGGER.debug("Nivel de riesgo {} evaluado como aprobado: {}", nivelRiesgo, aprobado);
        return aprobado;
    }

    public boolean requiereRevisionManual(String clienteId, String productoId) {
        String keyCache = clienteId + "-" + productoId;
        ResultadoAntifraudeCache cache = cacheResultados.get(keyCache);
        if (cache != null && cache.getResultado() != null) {
            return cache.getResultado().getRequiereRevisionManual();
        }
        return false;
    }

    private void registrarSolicitud(String identificadorSesion) {
        if (identificadorSesion != null) {
            contadores.computeIfAbsent(identificadorSesion, k -> new ContadorSolicitudes(identificadorSesion))
                    .incrementar();
        }
    }

    private void validarParametrosRequeridos(String clienteId, String productoId) {
        if (clienteId == null || clienteId.isBlank()) {
            throw new IllegalArgumentException("Parámetro requerido: clienteId");
        }
        if (productoId == null || productoId.isBlank()) {
            throw new IllegalArgumentException("Parámetro requerido: productoId");
        }
    }

    private String generarIdRequest() {
        return "AF-" + System.currentTimeMillis() + "-" + (int)(Math.random() * 10000);
    }

    private Map<String, Object> construirPayloadVerificacion(String clienteId, String productoId, String identificadorSesion) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("clienteId", clienteId);
        payload.put("productoId", productoId);
        payload.put("identificadorSesion", identificadorSesion);
        payload.put("tipoOperacion", "APERTURA_PRODUCTO");
        payload.put("timestamp", java.time.LocalDateTime.now().toString());
        return payload;
    }

    public static class ResultadoVerificacionAntifraude {
        private final String clienteId;
        private final String productoId;
        private final Boolean aprobado;
        private final Integer nivelRiesgo;
        private final String motivo;
        private final Boolean requiereRevisionManual;
        private final long tiempoRespuestaMs;
        private final boolean exitosa;

        public ResultadoVerificacionAntifraude(String clienteId, String productoId, Boolean aprobado,
                                              Integer nivelRiesgo, String motivo,
                                              Boolean requiereRevisionManual, long tiempoRespuestaMs,
                                              boolean exitosa) {
            this.clienteId = clienteId;
            this.productoId = productoId;
            this.aprobado = aprobado;
            this.nivelRiesgo = nivelRiesgo;
            this.motivo = motivo;
            this.requiereRevisionManual = requiereRevisionManual;
            this.tiempoRespuestaMs = tiempoRespuestaMs;
            this.exitosa = exitosa;
        }

        public String getClienteId() { return clienteId; }
        public String getProductoId() { return productoId; }
        public Boolean getAprobado() { return aprobado; }
        public Integer getNivelRiesgo() { return nivelRiesgo; }
        public String getMotivo() { return motivo; }
        public Boolean getRequiereRevisionManual() { return requiereRevisionManual; }
        public long getTiempoRespuestaMs() { return tiempoRespuestaMs; }
        public boolean isExitosa() { return exitosa; }
    }

    private static class ResultadoAntifraudeCache {
        private final String key;
        private final ResultadoVerificacionAntifraude resultado;
        private final long timestamp;

        public ResultadoAntifraudeCache(String key, ResultadoVerificacionAntifraude resultado) {
            this.key = key;
            this.resultado = resultado;
            this.timestamp = System.currentTimeMillis();
        }

        public ResultadoVerificacionAntifraude getResultado() { return resultado; }
    }

    private static class ContadorSolicitudes {
        private final String identificadorSesion;
        private int cantidad;

        public ContadorSolicitudes(String identificadorSesion) {
            this.identificadorSesion = identificadorSesion;
            this.cantidad = 0;
        }

        public synchronized void incrementar() {
            this.cantidad++;
        }

        public int getCantidad() { return cantidad; }
    }
}