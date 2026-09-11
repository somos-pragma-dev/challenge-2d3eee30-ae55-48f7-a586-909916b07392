package com.banco.digital.abilities;

import net.serenitybdd.screenplay.Ability;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.Switchable;
import com.banco.digital.models.SolicitudProducto;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.time.LocalDateTime;

public class UsarCoreBancario implements Ability {

    private static final int TIMEOUT_MAXIMO_MS = 2000;
    private static final Map<String, ConfirmacionCore> REGISTRO_CONFIRMACIONES = new ConcurrentHashMap<>();
    private final String endpointCore;
    private String ultimoTokenAcceso;
    private LocalDateTime ultimaSolicitud;

    public UsarCoreBancario(String endpointCore) {
        this.endpointCore = endpointCore;
        this.ultimoTokenAcceso = generarTokenAcceso();
    }

    public static UsarCoreBancario en(String endpointCore) {
        return new UsarCoreBancario(endpointCore);
    }

    public static UsarCoreBancario como(Actor actor) {
        return actor.abilityTo(UsarCoreBancario.class);
    }

    public ConfirmacionResult confirmarSolicitud(SolicitudProducto solicitud) {
        if (solicitud == null || solicitud.getSolicitudId() == null) {
            throw new IllegalArgumentException("La solicitud no puede ser nula y debe tener ID");
        }

        if (!solicitud.estaPendiente()) {
            return new ConfirmacionResult(false, "La solicitud no está en estado pendiente");
        }

        if (solicitud.getConfirmacionCore() != null && solicitud.getConfirmacionCore()) {
            return new ConfirmacionResult(true, "La solicitud ya fue confirmada previamente");
        }

        ultimaSolicitud = LocalDateTime.now();
        ConfirmacionCore confirmacion = new ConfirmacionCore(
            solicitud.getSolicitudId(),
            solicitud.getClienteId(),
            solicitud.getProductoId(),
            true,
            LocalDateTime.now()
        );

        REGISTRO_CONFIRMACIONES.put(solicitud.getSolicitudId(), confirmacion);
        return new ConfirmacionResult(true, "Confirmación exitosa en el core bancario");
    }

    public boolean verificarConfirmacion(String solicitudId) {
        ConfirmacionCore confirmacion = REGISTRO_CONFIRMACIONES.get(solicitudId);
        return confirmacion != null && confirmacion.isConfirmada();
    }

    public String obtenerDetalleConfirmacion(String solicitudId) {
        ConfirmacionCore confirmacion = REGISTRO_CONFIRMACIONES.get(solicitudId);
        if (confirmacion == null) {
            return "No existe confirmación para la solicitud: " + solicitudId;
        }
        return String.format("Solicitud: %s | Cliente: %s | Producto: %s | Confirmada: %s | Timestamp: %s",
            confirmacion.getSolicitudId(),
            confirmacion.getClienteId(),
            confirmacion.getProductoId(),
            confirmacion.isConfirmada(),
            confirmacion.getTimestampConfirmacion());
    }

    public boolean tieneAcceso() {
        return ultimoTokenAcceso != null && !ultimoTokenAcceso.isEmpty();
    }

    public void actualizarToken(String nuevoToken) {
        this.ultimoTokenAcceso = nuevoToken;
    }

    public String getEndpointCore() {
        return endpointCore;
    }

    public boolean estaDentroDelTimeout() {
        if (ultimaSolicitud == null) {
            return true;
        }
        long milisegundosTranscurridos = java.time.Duration.between(ultimaSolicitud, LocalDateTime.now()).toMillis();
        return milisegundosTranscurridos < TIMEOUT_MAXIMO_MS;
    }

    public void limpiarCacheConfirmaciones() {
        REGISTRO_CONFIRMACIONES.clear();
    }

    private String generarTokenAcceso() {
        return "CORE-TOKEN-" + System.currentTimeMillis() + "-" + (int)(Math.random() * 10000);
    }

    public static class ConfirmacionCore {
        private final String solicitudId;
        private final String clienteId;
        private final String productoId;
        private final boolean confirmada;
        private final LocalDateTime timestampConfirmacion;

        public ConfirmacionCore(String solicitudId, String clienteId, String productoId, 
                               boolean confirmada, LocalDateTime timestampConfirmacion) {
            this.solicitudId = solicitudId;
            this.clienteId = clienteId;
            this.productoId = productoId;
            this.confirmada = confirmada;
            this.timestampConfirmacion = timestampConfirmacion;
        }

        public String getSolicitudId() {
            return solicitudId;
        }

        public String getClienteId() {
            return clienteId;
        }

        public String getProductoId() {
            return productoId;
        }

        public boolean isConfirmada() {
            return confirmada;
        }

        public LocalDateTime getTimestampConfirmacion() {
            return timestampConfirmacion;
        }
    }

    public static class ConfirmacionResult {
        private final boolean exitosa;
        private final String mensaje;

        public ConfirmacionResult(boolean exitosa, String mensaje) {
            this.exitosa = exitosa;
            this.mensaje = mensaje;
        }

        public boolean isExitosa() {
            return exitosa;
        }

        public String getMensaje() {
            return mensaje;
        }
    }
}