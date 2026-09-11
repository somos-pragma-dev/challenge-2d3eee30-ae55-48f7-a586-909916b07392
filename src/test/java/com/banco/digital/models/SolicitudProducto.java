package com.banco.digital.models;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Modelo de datos que representa una solicitud de apertura de producto bancario.
 * Encapsula la información del cliente, el producto solicitado y el estado del proceso.
 */
public class SolicitudProducto {

    private String solicitudId;
    private String clienteId;
    private String productoId;
    private String nombreProducto;
    private String estado;
    private LocalDateTime fechaSolicitud;
    private LocalDateTime fechaActualizacion;
    private String motivoRechazo;
    private Integer puntuacionBuro;
    private Boolean aprobacionAntifraude;
    private Boolean confirmacionCore;
    private Integer cantidadIntentos;
    private String canalOrigen;
    private String identificadorSesion;

    public SolicitudProducto() {
        this.fechaSolicitud = LocalDateTime.now();
        this.cantidadIntentos = 0;
        this.estado = "PENDIENTE";
    }

    public SolicitudProducto(String clienteId, String productoId, String canalOrigen) {
        this();
        this.clienteId = clienteId;
        this.productoId = productoId;
        this.canalOrigen = canalOrigen;
    }

    public String getSolicitudId() {
        return solicitudId;
    }

    public void setSolicitudId(String solicitudId) {
        this.solicitudId = solicitudId;
    }

    public String getClienteId() {
        return clienteId;
    }

    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }

    public String getProductoId() {
        return productoId;
    }

    public void setProductoId(String productoId) {
        this.productoId = productoId;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
        this.fechaActualizacion = LocalDateTime.now();
    }

    public LocalDateTime getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(LocalDateTime fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public String getMotivoRechazo() {
        return motivoRechazo;
    }

    public void setMotivoRechazo(String motivoRechazo) {
        this.motivoRechazo = motivoRechazo;
    }

    public Integer getPuntuacionBuro() {
        return puntuacionBuro;
    }

    public void setPuntuacionBuro(Integer puntuacionBuro) {
        this.puntuacionBuro = puntuacionBuro;
    }

    public Boolean getAprobacionAntifraude() {
        return aprobacionAntifraude;
    }

    public void setAprobacionAntifraude(Boolean aprobacionAntifraude) {
        this.aprobacionAntifraude = aprobacionAntifraude;
    }

    public Boolean getConfirmacionCore() {
        return confirmacionCore;
    }

    public void setConfirmacionCore(Boolean confirmacionCore) {
        this.confirmacionCore = confirmacionCore;
    }

    public Integer getCantidadIntentos() {
        return cantidadIntentos;
    }

    public void setCantidadIntentos(Integer cantidadIntentos) {
        this.cantidadIntentos = cantidadIntentos;
    }

    public void incrementarIntentos() {
        this.cantidadIntentos = (this.cantidadIntentos == null ? 0 : this.cantidadIntentos) + 1;
    }

    public String getCanalOrigen() {
        return canalOrigen;
    }

    public void setCanalOrigen(String canalOrigen) {
        this.canalOrigen = canalOrigen;
    }

    public String getIdentificadorSesion() {
        return identificadorSesion;
    }

    public void setIdentificadorSesion(String identificadorSesion) {
        this.identificadorSesion = identificadorSesion;
    }

    public boolean estaAprobada() {
        return "APROBADA".equals(this.estado) || 
               "CONFIRMADA".equals(this.estado) ||
               "ACTIVA".equals(this.estado);
    }

    public boolean estaRechazada() {
        return "RECHAZADA".equals(this.estado) || 
               "RECHAZADA_BURO".equals(this.estado) ||
               "RECHAZADA_ANTIFRAUDE".equals(this.estado) ||
               "RECHAZADA_CORE".equals(this.estado);
    }

    public boolean estaPendiente() {
        return "PENDIENTE".equals(this.estado) || 
               "EN_PROCESO".equals(this.estado);
    }

    public boolean cumpleTiempoMaximoProcesamiento() {
        if (this.fechaSolicitud == null || this.fechaActualizacion == null) {
            return false;
        }
        long segundos = java.time.Duration.between(this.fechaSolicitud, this.fechaActualizacion).getSeconds();
        return segundos <= 2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SolicitudProducto that = (SolicitudProducto) o;
        return Objects.equals(solicitudId, that.solicitudId) &&
               Objects.equals(clienteId, that.clienteId) &&
               Objects.equals(productoId, that.productoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(solicitudId, clienteId, productoId);
    }

    @Override
    public String toString() {
        return "SolicitudProducto{" +
                "solicitudId='" + solicitudId + '\'' +
                ", clienteId='" + clienteId + '\'' +
                ", productoId='" + productoId + '\'' +
                ", nombreProducto='" + nombreProducto + '\'' +
                ", estado='" + estado + '\'' +
                ", fechaSolicitud=" + fechaSolicitud +
                ", cantidadIntentos=" + cantidadIntentos +
                '}';
    }

    public static SolicitudProductoBuilder builder() {
        return new SolicitudProductoBuilder();
    }

    public static class SolicitudProductoBuilder {
        private String solicitudId;
        private String clienteId;
        private String productoId;
        private String nombreProducto;
        private String estado;
        private LocalDateTime fechaSolicitud;
        private String canalOrigen;

        public SolicitudProductoBuilder solicitudId(String solicitudId) {
            this.solicitudId = solicitudId;
            return this;
        }

        public SolicitudProductoBuilder clienteId(String clienteId) {
            this.clienteId = clienteId;
            return this;
        }

        public SolicitudProductoBuilder productoId(String productoId) {
            this.productoId = productoId;
            return this;
        }

        public SolicitudProductoBuilder nombreProducto(String nombreProducto) {
            this.nombreProducto = nombreProducto;
            return this;
        }

        public SolicitudProductoBuilder estado(String estado) {
            this.estado = estado;
            return this;
        }

        public SolicitudProductoBuilder fechaSolicitud(LocalDateTime fechaSolicitud) {
            this.fechaSolicitud = fechaSolicitud;
            return this;
        }

        public SolicitudProductoBuilder canalOrigen(String canalOrigen) {
            this.canalOrigen = canalOrigen;
            return this;
        }

        public SolicitudProducto build() {
            SolicitudProducto solicitud = new SolicitudProducto();
            solicitud.setSolicitudId(this.solicitudId);
            solicitud.setClienteId(this.clienteId);
            solicitud.setProductoId(this.productoId);
            solicitud.setNombreProducto(this.nombreProducto);
            solicitud.setEstado(this.estado);
            solicitud.setFechaSolicitud(this.fechaSolicitud);
            solicitud.setCanalOrigen(this.canalOrigen);
            return solicitud;
        }
    }
}