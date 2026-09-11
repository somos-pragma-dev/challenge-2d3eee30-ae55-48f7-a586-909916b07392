package com.banco.digital.tasks;

import com.banco.digital.models.SolicitudProducto;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.questions.Text;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isClickable;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class IniciarSolicitud implements Task {

    private final String clienteId;
    private final String productoId;
    private final String canalOrigen;
    private String nombreProducto;

    public IniciarSolicitud(String clienteId, String productoId, String canalOrigen) {
        this.clienteId = clienteId;
        this.productoId = productoId;
        this.canalOrigen = canalOrigen;
    }

    public static IniciarSolicitud conDatos(String clienteId, String productoId, String canalOrigen) {
        return new IniciarSolicitud(clienteId, productoId, canalOrigen);
    }

    public IniciarSolicitud conNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
        return this;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
            WaitUntil.the(By.id("menu-productos"), isClickable()).forNoMoreThan(5).seconds(),
            Click.on(By.id("menu-productos"))
        );

        actor.attemptsTo(
            WaitUntil.the(By.xpath(//button[contains(@class, 'btn-solicitar') and contains(., '" + productoId + "')]), isClickable()).forNoMoreThan(3).seconds(),
            Click.on(By.xpath(//button[contains(@class, 'btn-solicitar') and contains(., '" + productoId + "')]))
        );

        actor.attemptsTo(
            WaitUntil.the(By.id("campo-cliente-id"), isVisible()).forNoMoreThan(2).seconds(),
            Enter.theValue(clienteId).into(By.id("campo-cliente-id")),
            Enter.theValue(productoId).into(By.id("campo-producto-id")),
            Click.on(By.id("btn-confirmar-solicitud"))
        );

        String solicitudGenerada = Text.of(By.id("solicitud-id-generada")).answeredBy(actor);
        String estadoInicial = Text.of(By.id("estado-solicitud")).answeredBy(actor);

        SolicitudProducto solicitud = SolicitudProducto.builder()
            .solicitudId(solicitudGenerada)
            .clienteId(clienteId)
            .productoId(productoId)
            .nombreProducto(nombreProducto != null ? nombreProducto : "Producto-" + productoId)
            .estado(estadoInicial)
            .fechaSolicitud(LocalDateTime.now())
            .fechaActualizacion(LocalDateTime.now())
            .puntuacionBuro(null)
            .aprobacionAntifraude(null)
            .confirmacionCore(null)
            .cantidadIntentos(1)
            .canalOrigen(canalOrigen)
            .identificadorSesion(UUID.randomUUID().toString())
            .build();

        actor.remember("solicitudActual", solicitud);
    }
}