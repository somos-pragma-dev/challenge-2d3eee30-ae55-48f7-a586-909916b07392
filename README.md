# Automatización del flujo crítico de apertura de producto con BDD

El equipo de calidad de software necesita automatizar el flujo crítico de apertura de producto para un banco digital. El proceso involucra la interacción con el usuario, la verificación de datos en el buró de crédito, la aprobación por el motor antifraude y la confirmación final por el core bancario. Los actores involucrados son el cliente, el buró de crédito, el motor antifraude y el core bancario. El flujo debe manejar consistentemente volúmenes de hasta 10 000 solicitudes por hora con una latencia máxima de 2 segundos por solicitud. Las validaciones clave incluyen la prohibición de aperturas duplicadas en un plazo de 24 horas y la gestión de respuestas timeout del buró de crédito.

## Informacion General

| Campo | Valor |
|-------|-------|
| **Tema** | Automatizacion de pruebas con BDD |
| **Nivel** | advanced-l1 |
| **Tipo** | mixed |
| **Tiempo estimado** | 15 horas |

## Fases del Reto

### Fase 0: Configuración del Proyecto

**Objetivo:** Obtener el proyecto base funcional enviando el Código Base a un asistente de IA, que lo analizará, corregirá errores y generará un ZIP listo para usar.

**Tiempo estimado:** 15-30 minutos

**Instrucciones:**

- Asegúrate de tener instalado para ejecutar el proyecto: JDK 17+, Maven 3.9+, IDE con soporte Java.
- Copia todo el contenido del campo **Código Base** de este reto — incluyendo el texto de instrucciones que aparece al inicio.
- Abre un asistente de IA (Claude en claude.ai, ChatGPT o Gemini — se recomienda Claude), pega el contenido copiado en el chat y envíalo.
- El asistente analizará los archivos, corregirá errores y generará un archivo ZIP descargable. Descárgalo y extráelo en la carpeta donde quieras trabajar.
- Ejecuta `mvn compile` en la raíz. Si no hay errores, estás listo.

**Entregable:** El proyecto compila/arranca sin errores.

<details>
<summary>Pistas de conocimiento</summary>

- Copia el Código Base completo incluyendo el texto de instrucciones al inicio — esas instrucciones le indican al asistente exactamente qué hacer con los archivos.
- Si el asistente no genera el ZIP automáticamente al terminar el análisis, escríbele: "genera el ZIP ahora".
- Si el proyecto tiene errores al arrancar, comparte el mensaje de error con el mismo asistente para que lo corrija.

</details>

### Fase 1: Definición de escenarios de prueba

**Objetivo:** Identificar y documentar los escenarios de prueba para el flujo de apertura de producto.

**Tiempo estimado:** 3 horas

**Instrucciones:**

- Enumera los pasos clave del flujo de apertura de producto.
- Identifica las condiciones de validación y los posibles errores en cada paso.
- Documenta los escenarios de prueba en lenguaje de negocio utilizando Gherkin.

**Entregable:** Documento Gherkin con escenarios de prueba definidos.

<details>
<summary>Pistas de conocimiento</summary>

- Considera los diferentes actores y sus interacciones en el flujo.
- Piensa en los posibles casos de edge y cómo manejarlos.

</details>

### Fase 2: Implementación de pruebas automatizadas

**Objetivo:** Implementar las pruebas automatizadas para los escenarios definidos en la fase anterior.

**Tiempo estimado:** 8 horas

**Instrucciones:**

- Utiliza las definiciones de escenarios en Gherkin para implementar las pruebas automatizadas.
- Asegura que las pruebas manejen correctamente las condiciones de validación y los posibles errores.
- Verifica que las pruebas se ejecuten de forma consistente y produzcan resultados esperados.

**Entregable:** Pruebas automatizadas implementadas y verificadas.

<details>
<summary>Pistas de conocimiento</summary>

- Considera el uso de herramientas de automatización como Selenium para la implementación de las pruebas.
- Piensa en cómo manejar las dependencias entre los diferentes pasos del flujo.

</details>

### Fase 3: Evaluación y optimización de las pruebas

**Objetivo:** Evaluar y optimizar las pruebas automatizadas implementadas.

**Tiempo estimado:** 4 horas

**Instrucciones:**

- Analiza los resultados de las pruebas y identifica áreas de mejora.
- Optimiza las pruebas para mejorar su eficiencia y cobertura.
- Documenta las mejoras realizadas y los resultados obtenidos.

**Entregable:** Informe de evaluación y optimización de las pruebas automatizadas.

<details>
<summary>Pistas de conocimiento</summary>

- Considera la posibilidad de parallelizar las pruebas para mejorar su eficiencia.
- Piensa en cómo puedes aumentar la cobertura de las pruebas incluyendo más escenarios de edge.

</details>

## Dimensiones Evaluadas

- **queEs**: ¿Qué es BDD y cómo se aplica en la automatización de pruebas?
- **paraQueSirve**: ¿Para qué sirve automatizar el flujo de apertura de producto con BDD?
- **comoSeUsa**: ¿Cómo se usan las herramientas de automatización como Selenium en el contexto de BDD?
- **erroresComunes**: ¿Cuáles son los errores comunes al implementar pruebas automatizadas con BDD y cómo se pueden evitar?
- **queDecisionesImplica**: ¿Qué decisiones implica la optimización de las pruebas automatizadas y cómo se justifican?

## Criterios de Evaluacion

- Definir escenarios de prueba en lenguaje de negocio utilizando Gherkin.
- Implementar pruebas automatizadas para los escenarios definidos.
- Evaluar y optimizar las pruebas automatizadas.

## Como trabajar con un asistente de IA

Hay dos caminos, elegi uno:

- **AGENTS.md** (recomendado) — instrucciones nativas del repo. Abri esta carpeta con tu agente local (Claude Code, Cursor, Codex, Copilot, Gemini) y las carga solo. Sabe que archivos faltan y con que comando se verifica, y completa el scaffold escribiendo en disco.
- **PROMPT_MEJORA.md** — para copiar y pegar en un chat (claude.ai, ChatGPT). Devuelve un ZIP con el proyecto. Sirve si no tenes un agente en el IDE.

Ninguno de los dos resuelve las fases del reto: eso es tu trabajo.

## Verificacion

El proyecto esta listo para trabajar cuando este comando corre sin errores:

```bash
mvn clean test-compile
```

---

*Reto generado automaticamente por Challenge Generator - Pragma*
