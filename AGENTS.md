# AGENTS.md

Instrucciones para el agente de IA que abra este repositorio (Claude Code, Cursor, Codex, Copilot, Gemini). Se cargan solas: no hay que pegar nada en ningun chat.

## Que es este repositorio

Es el codigo base de un reto de aprendizaje de Pragma: **Automatización del flujo crítico de apertura de producto con BDD**.

| | |
|---|---|
| Tema | Automatizacion de pruebas con BDD |
| Nivel | advanced-l1 |
| Chapter | Calidad de Software |
| Especialidad | Automatizador |
| Stack | Java 21 / Serenity BDD 4.1.5 |
| Patron arquitectonico | Screenplay con capas para actores, tareas, preguntas y interacciones |
| Tiempo estimado | 15 horas |

## Tu tarea

Dejar este proyecto en estado **verificable**: que el comando de verificacion corra sin errores. Escribi los archivos en disco, en este repositorio. No generes ZIPs ni archivos adjuntos.

En orden:

1. Corre `mvn clean test-compile` y mira que falla.
2. Completa lo que falte de la lista de abajo: manifiesto de dependencias, punto de entrada, capa de interfaz y las capas del patron declarado.
3. Arregla SOLO los errores que impiden compilar o arrancar.
4. Volve a correr `mvn clean test-compile` hasta que pase.
5. Pará ahí.

## Regla dura: las fases son trabajo del humano

**PROHIBIDO implementar los entregables de las fases.** El valor del reto esta en que la persona los resuelva. Tu trabajo es que tenga un proyecto que arranca; el hueco pedagogico se queda como esta.

No resuelvas nada de esto:

- **Fase 1 — Definición de escenarios de prueba**: Documento Gherkin con escenarios de prueba definidos.
- **Fase 2 — Implementación de pruebas automatizadas**: Pruebas automatizadas implementadas y verificadas.
- **Fase 3 — Evaluación y optimización de las pruebas**: Informe de evaluación y optimización de las pruebas automatizadas.

Distincion operativa:

- **Arreglar** (si): import faltante, tipo que no existe, dependencia sin declarar, error de sintaxis, archivo referenciado que no existe.
- **No tocar** (no): logica de negocio incompleta, validaciones ausentes, secretos hardcodeados, APIs deprecadas que funcionan, concurrencia insegura, patrones mejorables. Eso es lo que la persona tiene que encontrar.

## Lo que falta y tenes que completar

### 1. Boilerplate del stack (1)

Sin esto el proyecto no compila ni arranca. **Es tu trabajo crearlo**, y no toca nada de lo pedagogico: es andamiaje del stack.

- [ ] **Clase runner (ej. RunCucumberTest.java)** — Sin la clase runner anotada, JUnit no tiene punto de entrada para ejecutar los features.

### 2. Referencias colgando (15)

Salieron de un analisis estatico del codigo que SI esta en el repo. Cada una rompe la compilacion:

- [ ] `src/test/java/com/banco/digital/steps/AperturaProductoSteps.java` — `org.hamcrest.CoreMatchers`
      El import org.hamcrest.CoreMatchers pertenece a org.hamcrest.CoreMatchers, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/test/java/com/banco/digital/tasks/VerificarDatosEnBuro.java` — `org.hamcrest.CoreMatchers`
      El import org.hamcrest.CoreMatchers pertenece a org.hamcrest.CoreMatchers, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/test/java/com/banco/digital/tasks/AprobarAntifraude.java` — `org.hamcrest.CoreMatchers`
      El import org.hamcrest.CoreMatchers pertenece a org.hamcrest.CoreMatchers, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/test/java/com/banco/digital/abilities/UsarServicioBuro.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/test/java/com/banco/digital/abilities/UsarServicioBuro.java` — `org.hamcrest.Matchers`
      El import org.hamcrest.Matchers pertenece a org.hamcrest.Matchers, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/test/java/com/banco/digital/abilities/UsarServicioAntifraude.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/test/java/com/banco/digital/tasks/ConfirmarCoreBancario.java` — `UsarCoreBancario.confirmarApertura`
      Se invoca `confirmarApertura` sobre `UsarCoreBancario`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/banco/digital/interactions/EsperarRespuestaBuro.java` — `UsarServicioBuro.consultarScore`
      Se invoca `consultarScore` sobre `UsarServicioBuro`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/banco/digital/abilities/UsarServicioBuro.java` — `ConsultaBuroCache.estaDentroVentana`
      Se invoca `estaDentroVentana` sobre `ConsultaBuroCache`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/banco/digital/abilities/UsarServicioAntifraude.java` — `ResultadoAntifraudeCache.getResultado`
      Se invoca `getResultado` sobre `ResultadoAntifraudeCache`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/banco/digital/abilities/UsarCoreBancario.java` — `ConfirmacionCore.isConfirmada`
      Se invoca `isConfirmada` sobre `ConfirmacionCore`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/banco/digital/abilities/UsarCoreBancario.java` — `ConfirmacionCore.getSolicitudId`
      Se invoca `getSolicitudId` sobre `ConfirmacionCore`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/banco/digital/abilities/UsarCoreBancario.java` — `ConfirmacionCore.getClienteId`
      Se invoca `getClienteId` sobre `ConfirmacionCore`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/banco/digital/abilities/UsarCoreBancario.java` — `ConfirmacionCore.getProductoId`
      Se invoca `getProductoId` sobre `ConfirmacionCore`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/banco/digital/abilities/UsarCoreBancario.java` — `ConfirmacionCore.getTimestampConfirmacion`
      Se invoca `getTimestampConfirmacion` sobre `ConfirmacionCore`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.

### Presentes (15)

- `pom.xml`
- `src/test/java/com/banco/digital/models/SolicitudProducto.java`
- `src/test/resources/features/apertura_producto/apertura_producto.feature`
- `src/test/java/com/banco/digital/runners/RunAperturaProductoTest.java`
- `src/test/java/com/banco/digital/steps/AperturaProductoSteps.java`
- `src/test/java/com/banco/digital/tasks/IniciarSolicitud.java`
- `src/test/java/com/banco/digital/tasks/VerificarDatosEnBuro.java`
- `src/test/java/com/banco/digital/tasks/AprobarAntifraude.java`
- `src/test/java/com/banco/digital/tasks/ConfirmarCoreBancario.java`
- `src/test/java/com/banco/digital/questions/EstadoSolicitud.java`
- `src/test/java/com/banco/digital/interactions/EsperarRespuestaBuro.java`
- `src/test/resources/serenity.conf`
- `src/test/java/com/banco/digital/abilities/UsarServicioBuro.java`
- `src/test/java/com/banco/digital/abilities/UsarServicioAntifraude.java`
- `src/test/java/com/banco/digital/abilities/UsarCoreBancario.java`

### Capas del patron declarado

Cada una tiene que existir como directorio real con al menos un archivo. Codigo plano en la raiz no satisface el patron.

- `src/test/java/com/banco/digital`
- `src/test/java/com/banco/digital/abilities`
- `src/test/java/com/banco/digital/tasks`
- `src/test/java/com/banco/digital/questions`
- `src/test/java/com/banco/digital/interactions`
- `src/test/java/com/banco/digital/models`
- `src/test/java/com/banco/digital/runners`
- `src/test/java/com/banco/digital/steps`
- `src/test/resources/features`
- `src/test/resources/features/apertura_producto`
- `src/test/resources/serenity.conf`

## Verificacion

```bash
mvn clean test-compile
```

Ese comando pasando es la definicion de "terminado" para vos.

## Convenciones que tenes que respetar

- Un solo ecosistema: no declares librerias de otro lenguaje ni mezcles gestores de paquetes.
- Toda libreria que uses tiene que estar declarada en el manifiesto de dependencias.
- Todo import declarado tiene que usarse; todo tipo usado tiene que existir o venir de una dependencia declarada.
- El patron es **Screenplay con capas para actores, tareas, preguntas y interacciones**: los contratos (interfaces, puertos) los define la capa interna y los implementa la externa, nunca al revés.
- Los archivos que crees llevan implementacion real, no stubs: sin `TODO`, sin cuerpos vacios, sin `// getters y setters`.

## Contexto del candidato

Sirve para calibrar el nivel del codigo, no para resolver las fases.

- Perfil: Chapter Calidad de Software, Especialidad Automatizador, Tecnología Java Selenium, Advanced
- Brecha que el reto ataca: Aplica metodologias de desarrollo basadas en comportamiento como BDD y trabaja con herramientas de automatizacion
- Mision: Automatizar el flujo critico de apertura de producto

---

*Generado por Challenge Generator — Pragma. `README.md` tiene el enunciado completo del reto para la persona. `PROMPT_MEJORA.md` es la variante para pegar en un chat, si se prefiere ese flujo.*
