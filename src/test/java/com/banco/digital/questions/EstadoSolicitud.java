package com.banco.digital.questions;

import com.banco.digital.models.SolicitudProducto;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;
import net.serenitybdd.screenplay.targets.Target;

public class EstadoSolicitud implements Question<String> {

    private final Target estadoElemento;

    private EstadoSolicitud(Target estadoElemento) {
        this.estadoElemento = estadoElemento;
    }

    public static EstadoSolicitud enPantalla() {
        return new EstadoSolicitud(
            Target.the("Estado de la solicitud")
                .locatedBy("//div[@class='estado-solicitud']/span")
        );
    }

    public static EstadoSolicitud enBaseDeDatos() {
        return new EstadoSolicitud(null);
    }

    @Override
    public String answeredBy(Actor actor) {
        if (estadoElemento != null) {
            return Text.of(estadoElemento).answeredBy(actor);
        }

        SolicitudProducto solicitud = actor.recall("solicitudConfirmada");
        if (solicitud == null) {
            solicitud = actor.recall("solicitudProcesada");
        }
        if (solicitud == null) {
            throw new net.serenitybdd.core.exceptions.SerenityException(
                "No se encontró ninguna solicitud en la memoria del actor"
            );
        }

        if (solicitud.estaAprobada()) {
            return "APROBADA";
        } else if (solicitud.estaRechazada()) {
            return "RECHAZADA";
        } else if (solicitud.estaPendiente()) {
            return "PENDIENTE";
        }

        return solicitud.getEstado();
    }

    public static class Verificador {

        public static Question<Boolean> estaAprobada() {
            return actor -> {
                String estado = new EstadoSolicitud.enBaseDeDatos().answeredBy(actor);
                return "APROBADA".equals(estado);
            };
        }

        public static Question<Boolean> estaRechazada() {
            return actor -> {
                String estado = new EstadoSolicitud.enBaseDeDatos().answeredBy(actor);
                return "RECHAZADA".equals(estado);
            };
        }

        public static Question<Boolean> cumpleTiempoMaximo() {
            return actor -> {
                SolicitudProducto solicitud = actor.recall("solicitudConfirmada");
                if (solicitud == null) {
                    solicitud = actor.recall("solicitudProcesada");
                }
                return solicitud != null && solicitud.cumpleTiempoMaximoProcesamiento();
            };
        }
    }
}