package com.banco.digital.tasks;

import com.banco.digital.abilities.UsarCoreBancario;
import com.banco.digital.models.SolicitudProducto;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;

public class ConfirmarCoreBancario implements Task {

    private final String solicitudId;
    private final String clienteId;
    private final String productoId;

    public ConfirmarCoreBancario(String solicitudId, String clienteId, String productoId) {
        this.solicitudId = solicitudId;
        this.clienteId = clienteId;
        this.productoId = productoId;
    }

    public static ConfirmarCoreBancario con(String solicitudId, String clienteId, String productoId) {
        return Tasks.instrumented(ConfirmarCoreBancario.class, solicitudId, clienteId, productoId);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
            net.serenitybdd.screenplay.interactions.Click.on(
                net.serenitybdd.screenplay.targets.Target.the("Botón confirmar en core bancario")
                    .locatedBy("//button[@id='confirmarCore']")
            )
        );

        UsarCoreBancario coreBancario = actor.abilityTo(UsarCoreBancario.class);
        SolicitudProducto solicitudConfirmada = coreBancario.confirmarApertura(solicitudId, clienteId, productoId);

        if (solicitudConfirmada == null) {
            throw new net.serenitybdd.core.exceptions.SerenityException(
                "El core bancario no respondió para la solicitud: " + solicitudId
            );
        }

        if (!solicitudConfirmada.getConfirmacionCore()) {
            throw new net.serenitybdd.core.exceptions.SerenityException(
                "El core bancario rechazo la confirmación para la solicitud: " + solicitudId +
                ". Motivo: " + solicitudConfirmada.getMotivoRechazo()
            );
        }

        actor.remember("solicitudConfirmada", solicitudConfirmada);
        actor.remember("estadoFinal", solicitudConfirmada.getEstado());
    }
}