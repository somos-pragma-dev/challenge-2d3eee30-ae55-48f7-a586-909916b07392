Feature: Apertura de Producto Bancario
  Como equipo de calidad del banco digital
  Necesito automatizar el flujo crítico de apertura de producto
  Para validar que el proceso cumple con los requisitos de negocio y las políticas de riesgo

  Background:
    Given que el sistema tiene capacidad para procesar hasta 10000 solicitudes por hora
    And el tiempo máximo de procesamiento por solicitud es de 2 segundos

  @smoke @exito
  Scenario: Apertura exitosa de producto bancario
    Given el cliente "CLIENTE_001" no tiene una solicitud de producto activa en las últimas 24 horas
    When el cliente inicia una solicitud de apertura del producto "CUENTA_AHORRO" a través del canal "APP_MOVIL"
    And el sistema verifica los datos del cliente en el buró de crédito
    And el buró de crédito retorna una puntuación de 750
    And el motor de antifraude aprueba la solicitud
    And el core bancario confirma la apertura del producto
    Then la solicitud debe estar en estado "APROBADA"
    And el sistema debe registrar la confirmación del core bancario
    And la respuesta debe incluir el identificador de sesión generado

  @rechazo @buro
  Scenario: Rechazo por puntuación baja en buró de crédito
    Given el cliente "CLIENTE_002" no tiene una solicitud de producto activa en las últimas 24 horas
    When el cliente inicia una solicitud de apertura del producto "TARJETA_CREDITO" a través del canal "WEB"
    And el sistema verifica los datos del cliente en el buró de crédito
    And el buró de crédito retorna una puntuación de 580
    Then la solicitud debe estar en estado "RECHAZADA"
    And el motivo de rechazo debe ser "PUNTUACION_BURO_INSUFICIENTE"
    And el sistema no debe invocar al motor de antifraude

  @rechazo @antifraude
  Scenario: Rechazo por motor de antifraude
    Given el cliente "CLIENTE_003" no tiene una solicitud de producto activa en las últimas 24 horas
    When el cliente inicia una solicitud de apertura del producto "CREDITO_PERSONAL" a través del canal "SUCURSAL"
    And el sistema verifica los datos del cliente en el buró de crédito
    And el buró de crédito retorna una puntuación de 700
    And el motor de antifraude rechaza la solicitud por riesgo detectado
    Then la solicitud debe estar en estado "RECHAZADA"
    And el motivo de rechazo debe ser "RIESGO_ANTIFRAUDE"

  @error @timeout
  Scenario: Timeout del buró de crédito
    Given el cliente "CLIENTE_004" no tiene una solicitud de producto activa en las últimas 24 horas
    When el cliente inicia una solicitud de apertura del producto "CUENTA_AHORRO" a través del canal "APP_MOVIL"
    And el sistema verifica los datos del cliente en el buró de crédito
    And el buró de crédito no responde dentro del tiempo límite de 2 segundos
    Then la solicitud debe estar en estado "RECHAZADA"
    And el motivo de rechazo debe ser "TIMEOUT_BURO"
    And el sistema debe registrar el intento fallido

  @validacion @duplicado
  Scenario: Rechazo por solicitud duplicada en las últimas 24 horas
    Given el cliente "CLIENTE_005" tiene una solicitud de producto "CUENTA_AHORRO" aprobada hace 12 horas
    When el cliente intenta iniciar una nueva solicitud de apertura del mismo producto
    Then el sistema debe rechazar la solicitud por duplicidad
    And el motivo de rechazo debe ser "SOLICITUD_DUPLICADA_24H"
    And no se debe procesar la solicitud en el buró de crédito

  @error @core
  Scenario: Fallo en confirmación del core bancario
    Given el cliente "CLIENTE_006" no tiene una solicitud de producto activa en las últimas 24 horas
    When el cliente inicia una solicitud de apertura del producto "CUENTA_AHORRO" a través del canal "APP_MOVIL"
    And el sistema verifica los datos del cliente en el buró de crédito
    And el buró de crédito retorna una puntuación de 720
    And el motor de antifraude aprueba la solicitud
    And el core bancario falla al confirmar la apertura
    Then la solicitud debe estar en estado "ERROR"
    And el sistema debe registrar el error del core bancario
    And se debe notificar al equipo de operaciones

  @reintento @exitoso
  Scenario: Reintento exitoso después de timeout en buró
    Given el cliente "CLIENTE_007" tiene una solicitud en estado "PENDIENTE" por timeout del buró
    When el sistema reintenta la verificación en el buró de crédito
    And el buró de crédito retorna una puntuación de 680
    And el motor de antifraude aprueba la solicitud
    And el core bancario confirma la apertura
    Then la solicitud debe actualizarse al estado "APROBADA"
    And el número de intentos debe ser 2

  @smoke @multi-canal
  Scenario: Apertura exitosa desde múltiples canales
    Given el cliente "CLIENTE_008" no tiene una solicitud de producto activa en las últimas 24 horas
    When el cliente inicia una solicitud de apertura del producto "CUENTA_AHORRO" a través del canal "CAJERO"
    And el sistema verifica los datos del cliente en el buró de crédito
    And el buró de crédito retorna una puntuación de 710
    And el motor de antifraude aprueba la solicitud
    And el core bancario confirma la apertura del producto
    Then la solicitud debe estar en estado "APROBADA"
    And el canal origen debe ser "CAJERO"

  @rechazo @puntuacion-limite
  Scenario: Validación de puntuación límite del buró
    Given el cliente "CLIENTE_009" no tiene una solicitud de producto activa en las últimas 24 horas
    When el cliente inicia una solicitud de apertura del producto "CREDITO_PERSONAL" a través del canal "WEB"
    And el sistema verifica los datos del cliente en el buró de crédito
    And el buró de crédito retorna una puntuación de 650
    Then la solicitud debe estar en estado "RECHAZADA"
    And el motivo de rechazo debe ser "PUNTUACION_BURO_INSUFICIENTE"
    And la puntuación должна быть menor a 700 para crédito personal

  @volumen @rendimiento
  Scenario: Procesamiento de alto volumen
    Given que el sistema está configurado para procesar hasta 10000 solicitudes por hora
    When se reciben 100 solicitudes simultáneas de diferentes clientes
    Then todas las solicitudes deben procesarse dentro de los 2 segundos por solicitud
    And el tiempo promedio de procesamiento no debe exceder 1.5 segundos
    And el sistema debe mantener la consistencia en los estados de las solicitudes