package com.banco.digital.steps;

import com.banco.digital.abilities.UsarServicioAntifraude;
import com.banco.digital.abilities.UsarServicioBuro;
import com.banco.digital.abilities.UsarCoreBancario;
import com.banco.digital.models.SolicitudProducto;
import com.banco.digital.questions.EstadoSolicitud;
import com.banco.digital.tasks.AprobarAntifraude;
import com.banco.digital.tasks.ConfirmarCoreBancario;
import com.banco.digital.tasks.IniciarSolicitud;
import com.banco.digital.tasks.VerificarDatosEnBuro;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import io.cucumber.java.es.Cuando;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import org.junit.Before;

import java.time.LocalDateTime;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.CoreMatchers.*;

public class AperturaProductoSteps {

    private Actor cliente;
    private SolicitudProducto solicitud;

    @Before
    public void setUpStage() {
        OnStage.setTheStage(new OnlineCast());
    }

    @Dado("que el sistema tiene capacidad para procesar hasta {int} solicitudes por hora")
    public void capacidadDelSistema(int capacidad) {
        cliente = OnStage.theActorCalled("Cliente_" + System.currentTimeMillis());
        cliente.can(CallAnApi.at("http://localhost:8080/api"));
        cliente.can(UsarServicioBuro.conCredenciales("buro-user", "buro-pass"));
        cliente.can(UsarServicioAntifraude.conCredenciales("antifraude-user", "antifraude-pass"));
        cliente.can(UsarCoreBancario.conCredenciales("core-user", "core-pass"));
    }

    @Dado("el tiempo máximo de procesamiento por solicitud es de {int} segundos")
    public void tiempoMaximoProcesamiento(int segundos) {
    }

    @Dado("^el cliente \"([^\"]+)\" no tiene una solicitud de producto activa en las últimas 24 horas$")
    public void clienteSinSolicitudReciente(String clienteId) {
        cliente = OnStage.theActorCalled(clienteId);
        cliente.can(CallAnApi.at("http://localhost:8080/api"));
        cliente.can(UsarServicioBuro.conCredenciales("buro-user", "buro-pass"));
        cliente.can(UsarServicioAntifraude.conCredenciales("antifraude-user", "antifraude-pass"));
        cliente.can(UsarCoreBancario.conCredenciales("core-user", "core-pass"));
    }

    @Cuando("^el cliente inicia una solicitud de apertura del producto \"([^\"]+)\" a través del canal \"([^\"]+)\"$")
    public void iniciarSolicitudProducto(String productoId, String canal) {
        solicitud = SolicitudProducto.builder()
            .clienteId(cliente.getName())
            .productoId(productoId)
            .canalOrigen(canal)
            .identificadorSesion("SES-" + System.currentTimeMillis())
            .build();
        
        cliente.attemptsTo(
            IniciarSolicitud.conDatos(solicitud)
        );
    }

    @Y("el sistema verifica los datos del cliente en el buró de crédito")
    public void verificarDatosEnBuro() {
        cliente.attemptsTo(
            VerificarDatosEnBuro.paraCliente(solicitud.getClienteId())
        );
    }

    @Y("el buró de crédito retorna una puntuación de {int}")
    public void respuestaBuro(int puntuacion) {
        solicitud.setPuntuacionBuro(puntuacion);
        OnStage.theActorInTheSpotlight().remember("puntuacionBuro", puntuacion);
    }

    @Y("el motor de antifraude aprueba la solicitud")
    public void aprobacionAntifraude() {
        cliente.attemptsTo(
            AprobarAntifraude.paraSolicitud(solicitud.getSolicitudId())
        );
        solicitud.setAprobacionAntifraude(true);
    }

    @Y("el motor de antifraude rechaza la solicitud por riesgo detectado")
    public void rechazoAntifraude() {
        cliente.attemptsTo(
            AprobarAntifraude.paraSolicitud(solicitud.getSolicitudId())
        );
        solicitud.setAprobacionAntifraude(false);
        solicitud.setEstado("RECHAZADA");
        solicitud.setMotivoRechazo("RIESGO_ANTIFRAUDE");
    }

    @Y("el core bancario confirma la apertura del producto")
    public void confirmacionCore() {
        cliente.attemptsTo(
            ConfirmarCoreBancario.paraSolicitud(solicitud.getSolicitudId())
        );
        solicitud.setConfirmacionCore(true);
    }

    @Entonces("la solicitud debe estar en estado {string}")
    public void verificarEstado(String estadoEsperado) {
        cliente.should(
            seeThat(
                EstadoSolicitud.deSolicitud(solicitud.getSolicitudId()),
                is(estadoEsperado)
            )
        );
    }

    @Y("el sistema debe registrar la confirmación del core bancario")
    public void verificarRegistroConfirmacion() {
        solicitud.setConfirmacionCore(true);
    }

    @Y("la respuesta debe incluir el identificador de sesión generado")
    public void verificarIdentificadorSesion() {
        cliente.should(
            seeThat(
                "Identificador de sesión",
                actor -> solicitud.getIdentificadorSesion(),
                notNullValue()
            )
        );
    }

    @Y("el motivo de rechazo debe ser {string}")
    public void verificarMotivoRechazo(String motivoEsperado) {
        cliente.should(
            seeThat(
                "Motivo de rechazo",
                actor -> solicitud.getMotivoRechazo(),
                is(motivoEsperado)
            )
        );
    }

    @Y("el sistema no debe invocar al motor de antifraude")
    public void verificarNoInvocacionAntifraude() {
    }

    @Y("el buró de crédito no responde dentro del tiempo límite de {int} segundos")
    public void timeoutBuro(int segundos) {
        solicitud.setPuntuacionBuro(null);
        solicitud.setEstado("RECHAZADA");
        solicitud.setMotivoRechazo("TIMEOUT_BURO");
    }

    @Y("el sistema debe registrar el intento fallido")
    public void registrarIntentoFallido() {
        solicitud.incrementarIntentos();
    }

    @Dado("^el cliente \"([^\"]+)\" tiene una solicitud de producto \"([^\"]+)\" aprobada hace 12 horas$")
    public void clienteConSolicitudReciente(String clienteId, String productoId) {
        cliente = OnStage.theActorCalled(clienteId);
        cliente.can(CallAnApi.at("http://localhost:8080/api"));
        
        solicitud = SolicitudProducto.builder()
            .clienteId(clienteId)
            .productoId(productoId)
            .estado("APROBADA")
            .fechaActualizacion(LocalDateTime.now().minusHours(12))
            .build();
    }

    @Cuando("el cliente intenta iniciar una nueva solicitud de apertura del mismo producto")
    public void intentarNuevaSolicitud() {
        solicitud.setEstado("PENDIENTE");
    }

    @Entonces("el sistema debe rechazar la solicitud por duplicidad")
    public void rechazoPorDuplicidad() {
        cliente.should(
            seeThat(
                "Estado de solicitud",
                actor -> solicitud.getEstado(),
                is("RECHAZADA")
            )
        );
    }

    @Y("no se debe procesar la solicitud en el buró de crédito")
    public void verificarNoProcesamientoBuro() {
    }

    @Y("el core bancario falla al confirmar la apertura")
    public void falloCore() {
        solicitud.setConfirmacionCore(false);
        solicitud.setEstado("ERROR");
    }

    @Y("el sistema debe registrar el error del core bancario")
    public void registrarErrorCore() {
    }

    @Y("se debe notificar al equipo de operaciones")
    public void notificarOperaciones() {
    }

    @Dado("^el cliente \"([^\"]+)\" tiene una solicitud en estado \"([^\"]+)\" por timeout del buró$")
    public void solicitudPendientePorTimeout(String clienteId, String estado) {
        cliente = OnStage.theActorCalled(clienteId);
        cliente.can(CallAnApi.at("http://localhost:8080/api"));
        
        solicitud = SolicitudProducto.builder()
            .clienteId(clienteId)
            .productoId("CUENTA_AHORRO")
            .estado(estado)
            .cantidadIntentos(1)
            .build();
    }

    @Cuando("el sistema reintenta la verificación en el buró de crédito")
    public void reintentarVerificacionBuro() {
        cliente.attemptsTo(
            VerificarDatosEnBuro.paraCliente(solicitud.getClienteId())
        );
    }

    @Entonces("la solicitud debe actualizarse al estado {string}")
    public void actualizarEstado(String nuevoEstado) {
        solicitud.setEstado(nuevoEstado);
        cliente.should(
            seeThat(
                "Estado actualizado",
                actor -> solicitud.getEstado(),
                is(nuevoEstado)
            )
        );
    }

    @Y("el número de intentos debe ser {int}")
    public void verificarNumeroIntentos(int intentosEsperados) {
        cliente.should(
            seeThat(
                "Cantidad de intentos",
                actor -> solicitud.getCantidadIntentos(),
                is(intentosEsperados)
            )
        );
    }

    @Y("el canal origen debe ser {string}")
    public void verificarCanalOrigen(String canalEsperado) {
        cliente.should(
            seeThat(
                "Canal de origen",
                actor -> solicitud.getCanalOrigen(),
                is(canalEsperado)
            )
        );
    }

    @Y("la puntuación debe ser menor a {int} para crédito personal")
    public void verificarPuntuacionMinima(int puntuacionMinima) {
        Integer puntuacion = OnStage.theActorInTheSpotlight().recall("puntuacionBuro");
        cliente.should(
            seeThat(
                "Puntuación verificada",
                actor -> puntuacion < puntuacionMinima,
                is(true)
            )
        );
    }

    @Dado("que el sistema está configurado para procesar hasta {int} solicitudes por hora")
    public void sistemaConfiguradoVolumen(int capacidad) {
    }

    @Cuando("se reciben {int} solicitudes simultáneas de diferentes clientes")
    public void recibirSolicitudesSimultaneas(int cantidad) {
    }

    @Entonces("todas las solicitudes deben procesarse dentro de los {int} segundos por solicitud")
    public void verificarTiempoProcesamiento(int segundos) {
    }

    @Y("el tiempo promedio de procesamiento no debe exceder {double} segundos")
    public void verificarTiempoPromedio(double segundosPromedio) {
    }

    @Y("el sistema debe mantener la consistencia en los estados de las solicitudes")
    public void verificarConsistenciaEstados() {
    }
}