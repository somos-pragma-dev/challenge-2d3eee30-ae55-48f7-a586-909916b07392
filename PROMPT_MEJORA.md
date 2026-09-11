# Prompt para Mejorar el Codigo Base

Copia y pega el contenido del bloque de abajo en un asistente de IA (Claude, ChatGPT)
para obtener un ZIP con el proyecto completo y arrancable.

Si preferis trabajar en tu editor con un agente local (Claude Code, Cursor, Copilot), usa `AGENTS.md` en vez de este archivo: dice lo mismo pero para que escriba los archivos en disco.

## Las dos reglas que no se negocian

1. **Completa el boilerplate.** Todo lo que el proyecto necesita para compilar y arrancar: manifiesto de dependencias, punto de entrada, configuracion, capa de interfaz, y las capas del patron arquitectonico declarado. Eso es andamiaje y es tu trabajo.
2. **NO resuelvas el reto.** Los entregables de las fases son el trabajo de la persona. El hueco pedagogico se deja como esta: el proyecto arranca, pero lo que el reto pide implementar NO esta implementado.

Dicho de otra forma: si algo impide compilar, arreglalo. Si algo es logica de negocio incompleta, validaciones ausentes, un secreto hardcodeado o un patron mejorable, dejalo exactamente como esta — es lo que la persona tiene que encontrar.

## Lo que le falta a este proyecto

Esto NO lo tenes que adivinar: salio de comparar el proyecto contra la arquitectura declarada del reto y de un analisis estatico del codigo. Completalo TODO.

### Boilerplate del stack que falta

Sin esto no compila ni arranca. Es andamiaje, no toca nada de lo pedagogico:

- **Clase runner (ej. RunCucumberTest.java)** — Sin la clase runner anotada, JUnit no tiene punto de entrada para ejecutar los features.

### Referencias colgando en el codigo que si esta

Cada una rompe la compilacion:

- `src/test/java/com/banco/digital/steps/AperturaProductoSteps.java` — `org.hamcrest.CoreMatchers`: El import org.hamcrest.CoreMatchers pertenece a org.hamcrest.CoreMatchers, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/test/java/com/banco/digital/tasks/VerificarDatosEnBuro.java` — `org.hamcrest.CoreMatchers`: El import org.hamcrest.CoreMatchers pertenece a org.hamcrest.CoreMatchers, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/test/java/com/banco/digital/tasks/AprobarAntifraude.java` — `org.hamcrest.CoreMatchers`: El import org.hamcrest.CoreMatchers pertenece a org.hamcrest.CoreMatchers, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/test/java/com/banco/digital/abilities/UsarServicioBuro.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/test/java/com/banco/digital/abilities/UsarServicioBuro.java` — `org.hamcrest.Matchers`: El import org.hamcrest.Matchers pertenece a org.hamcrest.Matchers, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/test/java/com/banco/digital/abilities/UsarServicioAntifraude.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/test/java/com/banco/digital/tasks/ConfirmarCoreBancario.java` — `UsarCoreBancario.confirmarApertura`: Se invoca `confirmarApertura` sobre `UsarCoreBancario`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/banco/digital/interactions/EsperarRespuestaBuro.java` — `UsarServicioBuro.consultarScore`: Se invoca `consultarScore` sobre `UsarServicioBuro`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/banco/digital/abilities/UsarServicioBuro.java` — `ConsultaBuroCache.estaDentroVentana`: Se invoca `estaDentroVentana` sobre `ConsultaBuroCache`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/banco/digital/abilities/UsarServicioAntifraude.java` — `ResultadoAntifraudeCache.getResultado`: Se invoca `getResultado` sobre `ResultadoAntifraudeCache`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/banco/digital/abilities/UsarCoreBancario.java` — `ConfirmacionCore.isConfirmada`: Se invoca `isConfirmada` sobre `ConfirmacionCore`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/banco/digital/abilities/UsarCoreBancario.java` — `ConfirmacionCore.getSolicitudId`: Se invoca `getSolicitudId` sobre `ConfirmacionCore`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/banco/digital/abilities/UsarCoreBancario.java` — `ConfirmacionCore.getClienteId`: Se invoca `getClienteId` sobre `ConfirmacionCore`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/banco/digital/abilities/UsarCoreBancario.java` — `ConfirmacionCore.getProductoId`: Se invoca `getProductoId` sobre `ConfirmacionCore`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/banco/digital/abilities/UsarCoreBancario.java` — `ConfirmacionCore.getTimestampConfirmacion`: Se invoca `getTimestampConfirmacion` sobre `ConfirmacionCore`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.

## Como saber que terminaste

```bash
mvn clean test-compile
```

Ese comando corriendo sin errores es la definicion de "listo".

---

```
## Briefing del reto (autoridad)
Este bloque manda sobre los archivos adjuntos. El stack y el rol salen de AQUÍ, no de un topic genérico ni de markdown placeholder.

### Perfil
Chapter Calidad de Software, Especialidad Automatizador, Tecnología Java Selenium, Advanced

### Brecha de conocimiento
Aplica metodologias de desarrollo basadas en comportamiento como BDD y trabaja con herramientas de automatizacion

### Misión / candidato
Automatizar el flujo critico de apertura de producto

### Reto
- Tema: Automatizacion de pruebas con BDD
- Seniority: advanced-l1
- Tipo: mixed
- Título: Automatización del flujo crítico de apertura de producto con BDD
- Tiempo estimado: 15 horas

### Fases (trabajo del HUMANO — PROHIBIDO completarlas)
No implementes estos entregables. Dejalos como hueco pedagógico. El asistente solo materializa el proyecto arrancable para que el participante pueda trabajar.
- Fase 1: Definición de escenarios de prueba — objetivo: Identificar y documentar los escenarios de prueba para el flujo de apertura de producto. — entregable (NO resolver): Documento Gherkin con escenarios de prueba definidos.
- Fase 2: Implementación de pruebas automatizadas — objetivo: Implementar las pruebas automatizadas para los escenarios definidos en la fase anterior. — entregable (NO resolver): Pruebas automatizadas implementadas y verificadas.
- Fase 3: Evaluación y optimización de las pruebas — objetivo: Evaluar y optimizar las pruebas automatizadas implementadas. — entregable (NO resolver): Informe de evaluación y optimización de las pruebas automatizadas.

Eres un asistente experto en análisis, corrección y generación de archivos de cualquier tipo:
código fuente, documentación, hojas de cálculo, documentos Word, configuraciones, entre otros.
Voy a enviarte una cadena de texto que contiene uno o más archivos. Cada archivo está delimitado por un marcador con el siguiente formato:
// === ARCHIVO: ruta/del/archivo.extension ===
o también puede aparecer como:
## === ARCHIVO: ruta/del/archivo.extension ===
Lo que sigue al marcador puede ser:

El contenido real del archivo (código, texto, YAML, etc.)
Una descripción en lenguaje natural de lo que debe contener el archivo


TU TAREA
PASO 0 — ¿Esto es un proyecto o una carcasa?
Antes de extraer archivos, leé el Briefing (si está) y diagnosticá el adjunto.

Es CARCASA si ocurre CUALQUIERA de estas:
- No hay manifiesto de dependencias del stack del briefing (manifest.json de VTEX IO / package.json / pom.xml / build.gradle / requirements.txt / go.mod / *.tf / *.csproj, según corresponda)
- Hay un "binario" que en realidad es un comentario ("no puede ser mostrado como texto plano", placeholder .fig/.docx vacío)
- Los markdowns ya completan entregables de fases posteriores ("se implementó fade-in", lista de áreas ya resuelta)

Si es CARCASA:
- MATERIALIZÁ un proyecto que arranca en el stack del briefing (VTEX IO Store Framework, Angular, Terraform, pytest, Nest, etc.). Incluí manifiesto, punto de entrada y capa de interfaz reales.
- NO copies los markdowns de "solución" como si fueran el producto. Son ruido de generación.
- NO resuelvas las fases del briefing (están marcadas PROHIBIDO). Dejá el hueco pedagógico: el flujo existe, las microinteracciones/calidad/infra que el reto pide NO están hechas.
- Después seguí al PASO 5 (ZIP).

Si es un proyecto REAL (manifiesto + código que compila o arranca):
- Seguí PASO 1 en adelante. 🔴 compilación sí. 🟡 pedagógico no.

PASO 1 — Detección y extracción
Identifica todos los archivos presentes en la cadena. Para cada archivo extrae:

Su ruta completa (ej: src/main/java/com/pragma/Service.java)
Su contenido o descripción

PASO 2 — Clasificación por tipo
Clasifica cada archivo en una de estas categorías:
A) Código fuente (Java, Python, TypeScript, JavaScript, Kotlin, etc.)
B) Configuración / documentación (YAML, properties, Markdown, JSON, txt, etc.)
C) Excel (.xlsx, .xls, .csv)
D) Word (.docx, .doc)
E) Otro tipo de archivo binario o especial
PASO 3 — Clasificación de errores en código fuente

Objetivo prioritario: que el proyecto compile. No corrijas flujo de negocio ni lógica funcional.

Antes de modificar cualquier archivo de código fuente, clasifica cada problema encontrado en una de estas dos categorías:
🔴 ERROR DE COMPILACIÓN — corregir siempre
Son errores que impiden que el proyecto arranque, sin valor pedagógico:

Import faltante o incorrecto
Clase, método o variable referenciada que no existe en ningún archivo del proyecto
Error de sintaxis
Anotación con atributos inválidos
Dependencia ausente en pom.xml, package.json, etc.
Archivo referenciado que no existe y debe ser creado con implementación mínima

→ CORREGIR estos errores.
🟡 PROBLEMA FUNCIONAL O DE CALIDAD — preservar siempre
Son problemas que no impiden compilar. Pueden ser intencionales para el aprendizaje:

Clave secreta hardcodeada ("secret", "password123")
API deprecada que funciona pero tiene reemplazo moderno
Lógica de negocio incorrecta o incompleta
Código redundante o de baja legibilidad
Falta de validaciones en flujo de negocio
Patrones de diseño incorrectos pero funcionales
Concurrencia no segura
Configuración funcional pero no óptima

→ PRESERVAR tal cual. No corregir, no mejorar, no comentar.
PASO 4 — Procesamiento según tipo de archivo
Tipo A — Código fuente
Aplica únicamente las correcciones clasificadas como 🔴 ERROR DE COMPILACIÓN.
No alteres ningún elemento clasificado como 🟡 PROBLEMA FUNCIONAL O DE CALIDAD.
Si falta un archivo referenciado, créalo con la implementación mínima necesaria para compilar.
Tipo B — Configuración / documentación
Extrae el contenido tal cual, sin modificaciones salvo errores evidentes de sintaxis
(ej: YAML mal indentado).
Tipo C — Excel (.xlsx)
Si viene con contenido real, genera el archivo respetando ese contenido.
Si viene con descripción en lenguaje natural, genera un archivo Excel funcional con:

Fila de encabezados en negrita con color de fondo distintivo
Columnas con ancho ajustado al contenido
Tipos de dato correctos por columna
Validaciones si la descripción lo indica
Hojas nombradas descriptivamente si hay más de una
Filas de ejemplo si no hay datos reales

Tipo D — Word (.docx)
Si viene con contenido real, genera el archivo respetando ese contenido.
Si viene con descripción en lenguaje natural, genera un documento Word funcional con:

Estilos de título (Título 1, Título 2) para jerarquía de secciones
Fuente legible (Calibri o equivalente), tamaño 11-12pt para cuerpo
Márgenes estándar
Tabla de contenido si tiene múltiples secciones
Tablas con encabezados en negrita si aplica

Tipo E — Otro
Genera el archivo con el contenido o estructura más apropiada según la descripción.
PASO 5 — Exportación en ZIP
Empaqueta todos los archivos en un único archivo ZIP descargable respetando exactamente
la estructura de rutas indicada por los marcadores.
El ZIP debe incluir:

Archivos de código con únicamente los errores de compilación corregidos
Archivos de configuración y documentación sin cambios
Archivos nuevos creados para resolver dependencias de compilación faltantes
Archivos Excel y Word generados desde descripción

IMPORTANTE: El ZIP debe estar listo para descargar al finalizar. No preguntes si el usuario
quiere generarlo. Simplemente genera el archivo y proporciona el enlace de descarga; No debes desplegar en el chat el resumen de lo que arreglaste al Zip, solo entregalo.

REGLAS IMPORTANTES

No omitas ningún archivo aunque no tenga errores ni modificaciones
Respeta los nombres y rutas exactas indicadas por los marcadores
Si un archivo no tiene marcador claro, infiere el nombre desde su contenido
Si la cadena contiene solo documentación, placeholders o binarios fake, NO la reproduzcas:
aplicá PASO 0 (materializar el proyecto del briefing). Reproducir la carcasa es un fallo.
No agregues texto después del enlace de descarga del ZIP
No preguntes si el usuario quiere el ZIP: simplemente generalo siempre
Si detectas que falta un archivo de configuración necesario para compilar
(pom.xml, package.json, requirements.txt, build.gradle, etc.), créalo e inclúyelo
inferiendo su contenido desde los imports y frameworks detectados en el código
Nunca corrijas problemas 🟡 aunque parezcan obvios o fáciles de mejorar.
El participante que recibirá este proyecto los debe encontrar y resolver él mismo.


INPUT
Aquí está la cadena con los archivos:

// === ARCHIVO: pom.xml ===
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.banco.digital</groupId>
    <artifactId>pruebas-automatizadas-banco</artifactId>
    <version>1.0.0</version>
    <packaging>jar</packaging>

    <name>Pruebas Automatizadas Banco Digital</name>
    <description>Suite de pruebas automatizadas para flujo de apertura de productos</description>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <java.version>21</java.version>
        <maven.compiler.source>21</maven.compiler.source>
        <maven.compiler.target>21</maven.compiler.target>
        <serenity.version>4.1.5</serenity.version>
        <cucumber.version>7.15.0</cucumber.version>
        <junit.version>5.10.2</junit.version>
        <selenium.version>4.22.0</selenium.version>
    </properties>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>net.serenity-bdd</groupId>
                <artifactId>serenity-bom</artifactId>
                <version>${serenity.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <dependency>
                <groupId>io.cucumber</groupId>
                <artifactId>cucumber-bom</artifactId>
                <version>${cucumber.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <dependencies>
        <dependency>
            <groupId>net.serenity-bdd</groupId>
            <artifactId>serenity-core</artifactId>
        </dependency>
        <dependency>
            <groupId>net.serenity-bdd</groupId>
            <artifactId>serenity-cucumber</artifactId>
        </dependency>
        <dependency>
            <groupId>net.serenity-bdd</groupId>
            <artifactId>serenity-screenplay-webdriver</artifactId>
        </dependency>
        <dependency>
            <groupId>net.serenity-bdd</groupId>
            <artifactId>serenity-rest-assured</artifactId>
        </dependency>
        <dependency>
            <groupId>io.cucumber</groupId>
            <artifactId>cucumber-java</artifactId>
        </dependency>
        <dependency>
            <groupId>io.cucumber</groupId>
            <artifactId>cucumber-junit-platform-engine</artifactId>
        </dependency>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
        </dependency>
        <dependency>
            <groupId>org.seleniumhq.selenium</groupId>
            <artifactId>selenium-java</artifactId>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>3.3.0</version>
                <configuration>
                    <includes>
                        <include>**/runners/*Test.java</include>
                    </includes>
                    <testFailureIgnore>false</testFailureIgnore>
                    <parallel>classes</parallel>
                    <threadCount>1</threadCount>
                </configuration>
            </plugin>
            <plugin>
                <groupId>net.serenity-bdd</groupId>
                <artifactId>serenity-maven-plugin</artifactId>
                <version>${serenity.version}</version>
                <executions>
                    <execution>
                        <id>serenity-reports</id>
                        <phase>post-integration-test</phase>
                        <goals>
                            <goal>aggregate</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.12.1</version>
                <configuration>
                    <source>21</source>
                    <target>21</target>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>

<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.banco.digital</groupId>
    <artifactId>automatizacion-pruebas-bdd</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <packaging>jar</packaging>

    <name>Automatización de Pruebas BDD - Banco Digital</name>
    <description>Suite de automatización de pruebas con BDD para el flujo de apertura de productos</description>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>21</maven.compiler.source>
        <maven.compiler.target>21</maven.compiler.target>
        <serenity.version>4.1.5</serenity.version>
        <cucumber.version>7.15.0</cucumber.version>
        <junit.version>5.10.2</junit.version>
        <selenium.version>4.22.0</selenium.version>
        <maven.surefire.version>3.3.0</maven.surefire.version>
    </properties>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>net.serenity-bdd</groupId>
                <artifactId>serenity-bom</artifactId>
                <version>${serenity.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <dependencies>
        <dependency>
            <groupId>net.serenity-bdd</groupId>
            <artifactId>serenity-core</artifactId>
        </dependency>
        <dependency>
            <groupId>net.serenity-bdd</groupId>
            <artifactId>serenity-cucumber</artifactId>
        </dependency>
        <dependency>
            <groupId>net.serenity-bdd</groupId>
            <artifactId>serenity-screenplay-webdriver</artifactId>
        </dependency>
        <dependency>
            <groupId>net.serenity-bdd</groupId>
            <artifactId>serenity-rest-assured</artifactId>
        </dependency>
        <dependency>
            <groupId>io.cucumber</groupId>
            <artifactId>cucumber-java</artifactId>
            <version>${cucumber.version}</version>
        </dependency>
        <dependency>
            <groupId>io.cucumber</groupId>
            <artifactId>cucumber-junit-platform-engine</artifactId>
            <version>${cucumber.version}</version>
        </dependency>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <version>${junit.version}</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.junit.platform</groupId>
            <artifactId>junit-platform-engine</artifactId>
            <version>1.10.2</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.junit.platform</groupId>
            <artifactId>junit-platform-launcher</artifactId>
            <version>1.10.2</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.seleniumhq.selenium</groupId>
            <artifactId>selenium-java</artifactId>
            <version>${selenium.version}</version>
        </dependency>
        <dependency>
            <groupId>org.seleniumhq.selenium</groupId>
            <artifactId>selenium-api</artifactId>
            <version>${selenium.version}</version>
        </dependency>
        <dependency>
            <groupId>org.seleniumhq.selenium</groupId>
            <artifactId>selenium-support</artifactId>
            <version>${selenium.version}</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.12.1</version>
                <configuration>
                    <source>21</source>
                    <target>21</target>
                    <encoding>UTF-8</encoding>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>${maven.surefire.version}</version>
                <configuration>
                    <includes>
                        <include>**/*Test.java</include>
                        <include>**/*Runner.java</include>
                    </includes>
                    <systemPropertyVariables>
                        <webdriver.driver>chrome</webdriver.driver>
                    </systemPropertyVariables>
                </configuration>
            </plugin>
            <plugin>
                <groupId>net.serenity-bdd</groupId>
                <artifactId>serenity-maven-plugin</artifactId>
                <version>${serenity.version}</version>
                <executions>
                    <execution>
                        <id>serenity-reports</id>
                        <phase>post-integration-test</phase>
                        <goals>
                            <goal>aggregate</goal>
                        </goals>
                    </execution>
                </executions>
                <configuration>
                    <reportsDirectory>target/site/serenity</reportsDirectory>
                    <concurrency>
                        <maxPoolSize>10</maxPoolSize>
                    </concurrency>
                    <testSourceDirectory>src/test/java</testSourceDirectory>
                    <featuresSourceDirectory>src/test/resources/features</featuresSourceDirectory>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-failsafe-plugin</artifactId>
                <version>${maven.surefire.version}</version>
                <executions>
                    <execution>
                        <goals>
                            <goal>integration-test</goal>
                            <goal>verify</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>

// === ARCHIVO: src/test/java/com/banco/digital/models/SolicitudProducto.java ===
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

// === ARCHIVO: src/test/resources/features/apertura_producto/apertura_producto.feature ===
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

// === ARCHIVO: src/test/java/com/banco/digital/runners/RunAperturaProductoTest.java ===
package com.banco.digital.runners;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
    features = "src/test/resources/features/apertura_producto/apertura_producto.feature",
    glue = {
        "com.banco.digital.steps",
        "com.banco.digital.steps.hooks"
    },
    plugin = {
        "pretty",
        "html:target/serenity-reports/serenity-html-report.html",
        "json:target/serenity-reports/serenity-json-report.json",
        "junit:target/serenity-reports/serenity-junit-report.xml"
    },
    tags = "@smoke or @rechazo or @error",
    strict = true,
    monochrome = true
)
public class RunAperturaProductoTest {
    
    public RunAperturaProductoTest() {
    }
}

// === ARCHIVO: src/test/java/com/banco/digital/steps/AperturaProductoSteps.java ===
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

// === ARCHIVO: src/test/java/com/banco/digital/tasks/IniciarSolicitud.java ===
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

// === ARCHIVO: src/test/java/com/banco/digital/tasks/VerificarDatosEnBuro.java ===
package com.banco.digital.tasks;

import com.banco.digital.abilities.UsarServicioBuro;
import com.banco.digital.models.SolicitudProducto;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.questions.LastResponse;
import net.serenitybdd.screenplay.rest.questions.RestQuestion;
import net.thucydides.core.annotations.Step;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.CoreMatchers.*;

public class VerificarDatosEnBuro implements Task {

    private static final int TIMEOUT_SECONDS = 2;
    private static final int MAX_REINTENTOS = 3;
    private static final int PUNTUACION_MINIMA_APROBACION = 650;

    private final String solicitudId;
    private String puntuacionBuro;
    private boolean timeoutOcurrido = false;
    private boolean respuestaFallida = false;
    private int intentos = 0;

    public VerificarDatosEnBuro(String solicitudId) {
        this.solicitudId = solicitudId;
    }

    public static VerificarDatosEnBuro paraSolicitud(String solicitudId) {
        return new VerificarDatosEnBuro(solicitudId);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.can(UsarServicioBuro.conEndpoint("https://buro-credito.banco-digital.com/api"));

        do {
            intentos++;
            try {
                Map<String, Object> cuerpoPeticion = new HashMap<>();
                cuerpoPeticion.put("solicitudId", solicitudId);
                cuerpoPeticion.put("tipoConsulta", "historico-completo");
                cuerpoPeticion.put("timestamp", LocalDateTime.now().toString());

                actor.attemptsTo(
                    RestQuestion.about("/consulta-buro")
                        .withRequest(request -> request
                            .header("Content-Type", "application/json")
                            .header("X-Transaction-ID", "buro-" + System.currentTimeMillis())
                            .body(cuerpoPeticion))
                        .setTimeout(Duration.ofSeconds(TIMEOUT_SECONDS))
                );

                String respuesta = LastResponse.received().answeredBy(actor).asString();

                actor.should(seeThat("Código de respuesta del buró",
                    LastResponse.statusCode(), is(200)));

                if (respuesta.contains(""puntuacion":")) {
                    String puntuacionExtraida = respuesta.split("\"puntuacion\"")[1].split("[,}")[0].replace("\"", "").replace(":", "");
                    this.puntuacionBuro = puntuacionExtraida.trim();
                    this.timeoutOcurrido = false;
                    this.respuestaFallida = false;
                } else {
                    this.respuestaFallida = true;
                }

            } catch (Exception e) {
                String mensajeError = e.getMessage();
                if (mensajeError.contains("timeout") || mensajeError.contains("Read timed out")) {
                    this.timeoutOcurrido = true;
                    this.respuestaFallida = true;
                } else {
                    this.respuestaFallida = true;
                }
            }

        } while ((timeoutOcurrido || respuestaFallida) && intentos < MAX_REINTENTOS);

        if (timeoutOcurrido && intentos >= MAX_REINTENTOS) {
            throw new RuntimeException(
                "Timeout del servicio de buró de crédito después de " + MAX_REINTENTOS + " intentos. " +
                "Tiempo máximo de espera: " + (TIMEOUT_SECONDS * MAX_REINTENTOS) + " segundos."
            );
        }

        if (respuestaFallida) {
            throw new RuntimeException(
                "Respuesta fallida del servicio de buró de crédito después de " + intentos + " intentos."
            );
        }

        SolicitudProducto solicitud = actor.recall("solicitudActual");
        if (solicitud != null) {
            solicitud.setPuntuacionBuro(Integer.parseInt(this.puntuacionBuro));
            solicitud.setFechaActualizacion(LocalDateTime.now());
            solicitud.incrementarIntentos();
            actor.remember("solicitudActual", solicitud);
        }

        actor.should(seeThat("Puntuación del buró procesada",
            () -> this.puntuacionBuro, notNullValue()));

        actor.should(seeThat("Timeout no ocurrido",
            () -> !this.timeoutOcurrido, is(true)));
    }

    public String getPuntuacionBuro() {
        return this.puntuacionBuro;
    }

    public boolean isTimeoutOcurrido() {
        return this.timeoutOcurrido;
    }

    public boolean isRespuestaFallida() {
        return this.respuestaFallida;
    }

    public int getIntentos() {
        return this.intentos;
    }
}

// === ARCHIVO: src/test/java/com/banco/digital/tasks/AprobarAntifraude.java ===
package com.banco.digital.tasks;

import com.banco.digital.abilities.UsarServicioAntifraude;
import com.banco.digital.models.SolicitudProducto;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.questions.LastResponse;
import net.serenitybdd.screenplay.rest.questions.RestQuestion;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.CoreMatchers.*;

public class AprobarAntifraude implements Task {

    private static final int TIMEOUT_SECONDS = 2;
    private static final int MAX_REINTENTOS = 3;

    private final String solicitudId;
    private boolean aprobacionConcedida = false;
    private String codigoRespuesta;
    private String mensajeRespuesta;
    private int intentos = 0;

    public AprobarAntifraude(String solicitudId) {
        this.solicitudId = solicitudId;
    }

    public static AprobarAntifraude paraSolicitud(String solicitudId) {
        return new AprobarAntifraude(solicitudId);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.can(UsarServicioAntifraude.conEndpoint("https://antifraude.banco-digital.com/api"));

        do {
            intentos++;
            try {
                Map<String, Object> cuerpoPeticion = new HashMap<>();
                cuerpoPeticion.put("solicitudId", solicitudId);
                cuerpoPeticion.put("tipoEvaluacion", "evaluacion-completa");
                cuerpoPeticion.put("timestamp", LocalDateTime.now().toString());
                cuerpoPeticion.put("origen", "sistema-apertura-productos");

                actor.attemptsTo(
                    RestQuestion.about("/evaluar-solicitud")
                        .withRequest(request -> request
                            .header("Content-Type", "application/json")
                            .header("X-Transaction-ID", "antifraude-" + System.currentTimeMillis())
                            .header("X-Request-Source", "bdd-tests")
                            .body(cuerpoPeticion))
                        .setTimeout(Duration.ofSeconds(TIMEOUT_SECONDS))
                );

                String respuesta = LastResponse.received().answeredBy(actor).asString();
                int codigoEstado = LastResponse.statusCode().answeredBy(actor);
                this.codigoRespuesta = String.valueOf(codigoEstado);

                actor.should(seeThat("Código de respuesta de antifraude",
                    LastResponse.statusCode(), anyOf(is(200), is(201))));

                if (respuesta.contains(""aprobado":")) {
                    String aprobado = respuesta.split("\"aprobado\"")[1].split("[,}")[0].replace("\"", "").replace(":", "").trim();
                    this.aprobacionConcedida = "true".equalsIgnoreCase(aprobado);
                    this.mensajeRespuesta = extraerMensaje(respuesta);
                } else if (respuesta.contains(""estado":")) {
                    String estado = respuesta.split("\"estado\"")[1].split("[,}")[0].replace("\"", "").replace(":", "").trim();
                    this.aprobacionConcedida = "APROBADO".equalsIgnoreCase(estado) || "APROBADA".equalsIgnoreCase(estado);
                    this.mensajeRespuesta = "Estado: " + estado;
                } else {
                    this.aprobacionConcedida = false;
                    this.mensajeRespuesta = "Respuesta sin formato esperado";
                }

            } catch (Exception e) {
                this.aprobacionConcedida = false;
                this.codigoRespuesta = "ERROR";
                this.mensajeRespuesta = "Excepción: " + e.getMessage();
            }

        } while (!this.aprobacionConcedida && intentos < MAX_REINTENTOS);

        if (!this.aprobacionConcedida && intentos >= MAX_REINTENTOS) {
            throw new RuntimeException(
                "Solicitud rechazada por motor antifraude después de " + MAX_REINTENTOS + " intentos. " +
                "Última respuesta: " + this.mensajeRespuesta
            );
        }

        SolicitudProducto solicitud = actor.recall("solicitudActual");
        if (solicitud != null) {
            solicitud.setAprobacionAntifraude(this.aprobacionConcedida);
            solicitud.setFechaActualizacion(LocalDateTime.now());
            solicitud.incrementarIntentos();
            actor.remember("solicitudActual", solicitud);
        }

        actor.should(seeThat("Aprobación antifraude concedida",
            () -> this.aprobacionConcedida, is(true)));

        actor.should(seeThat("Intentos realizados",
            () -> this.intentos, lessThanOrEqualTo(MAX_REINTENTOS)));
    }

    private String extraerMensaje(String respuesta) {
        if (respuesta.contains(""mensaje":")) {
            return respuesta.split("\"mensaje\"")[1].split("[,}")[0].replace("\"", "").trim();
        }
        if (respuesta.contains(""descripcion":")) {
            return respuesta.split("\"descripcion\"")[1].split("[,}")[0].replace("\"", "").trim();
        }
        return "Sin mensaje disponible";
    }

    public boolean isAprobacionConcedida() {
        return this.aprobacionConcedida;
    }

    public String getCodigoRespuesta() {
        return this.codigoRespuesta;
    }

    public String getMensajeRespuesta() {
        return this.mensajeRespuesta;
    }

    public int getIntentos() {
        return this.intentos;
    }
}

// === ARCHIVO: src/test/java/com/banco/digital/tasks/ConfirmarCoreBancario.java ===
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

// === ARCHIVO: src/test/java/com/banco/digital/questions/EstadoSolicitud.java ===
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

// === ARCHIVO: src/test/java/com/banco/digital/interactions/EsperarRespuestaBuro.java ===
package com.banco.digital.interactions;

import com.banco.digital.abilities.UsarServicioBuro;
import com.banco.digital.models.SolicitudProducto;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class EsperarRespuestaBuro implements Interaction {

    private static final Duration TIMEOUT_MAXIMO = Duration.ofSeconds(2);
    private static final int MAX_REINTENTOS = 3;

    private final String solicitudId;
    private final String clienteId;

    public EsperarRespuestaBuro(String solicitudId, String clienteId) {
        this.solicitudId = solicitudId;
        this.clienteId = clienteId;
    }

    public static EsperarRespuestaBuro por(String solicitudId, String clienteId) {
        return Tasks.instrumented(EsperarRespuestaBuro.class, solicitudId, clienteId);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        UsarServicioBuro servicioBuro = actor.abilityTo(UsarServicioBuro.class);
        
        if (servicioBuro == null) {
            throw new net.serenitybdd.core.exceptions.SerenityException(
                "El actor no tiene la habilidad de usar el servicio del buró de crédito"
            );
        }

        LocalDateTime tiempoInicio = LocalDateTime.now();
        int intentos = 0;
        SolicitudProducto respuesta = null;

        while (intentos < MAX_REINTENTOS && respuesta == null) {
            intentos++;
            Serenity.recordReportData()
                .withTitle("Intento de consulta al buró")
                .andContents("Intento " + intentos + " de " + MAX_REINTENTOS + " para solicitud: " + solicitudId);

            try {
                respuesta = servicioBuro.consultarScore(solicitudId, clienteId);
                
                if (respuesta == null) {
                    Duration tiempoTranscurrido = Duration.between(tiempoInicio, LocalDateTime.now());
                    if (tiempoTranscurrido.compareTo(TIMEOUT_MAXIMO) >= 0) {
                        throw new net.serenitybdd.core.exceptions.SerenityException(
                            "Timeout excedido esperando respuesta del buró de crédito. " +
                            "Tiempo transcurrido: " + tiempoTranscurrido.getSeconds() + " segundos"
                        );
                    }
                    
                    long tiempoRestante = TIMEOUT_MAXIMO.toMillis() - tiempoTranscurrido.toMillis();
                    tiempoRestante = Math.max(100, Math.min(tiempoRestante, 500));
                    
                    try {
                        Thread.sleep(tiempoRestante);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        throw new net.serenitybdd.core.exceptions.SerenityException(
                            "Hilo interrumpido mientras esperaba respuesta del buró", e
                        );
                    }
                }
            } catch (Exception e) {
                if (intentos >= MAX_REINTENTOS) {
                    throw new net.serenitybdd.core.exceptions.SerenityException(
                        "Error al consultar buró después de " + MAX_REINTENTOS + " intentos: " + e.getMessage(),
                        e
                    );
                }
                
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new net.serenitybdd.core.exceptions.SerenityException(
                        "Hilo interrumpido durante reintento", ie
                    );
                }
            }
        }

        if (respuesta == null) {
            Duration tiempoTotal = Duration.between(tiempoInicio, LocalDateTime.now());
            throw new net.serenitybdd.core.exceptions.SerenityException(
                "No se recibió respuesta del buró de crédito después de " + intentos + " intentos. " +
                "Tiempo total: " + tiempoTotal.getSeconds() + " segundos"
            );
        }

        actor.remember("respuestaBuro", respuesta);
        actor.remember("puntuacionBuro", respuesta.getPuntuacionBuro());
        
        Serenity.recordReportData()
            .withTitle("Respuesta del buró de crédito")
            .andContents("Solicitud: " + solicitudId + ", Score: " + respuesta.getPuntuacionBuro());
    }
}


// === ARCHIVO: src/test/resources/serenity.conf ===
webdriver {
  driver = chrome
  autodownload = true
  downloads.enabled = true
  chrome.switches = "--start-maximized;--no-sandbox;--disable-dev-shm-usage;--disable-gpu;--disable-extensions"
  timeouts {
    implicitlywait = 5000
    scripttimeout = 30000
    pageload = 30000
  }
}

serenity {
  project.name = "Banco Digital - Automatizacion de Pruebas BDD"
  test.root = "src/test/resources/features"
  report = {
    serenity.version = "4.1.5"
    show.step.details = true
    compress.filenames = false
    narrative.in.first.position = true
    dark.mode = false
  }
  logging = {
    level = INFO
    verbosity = NORMAL
    show.http.bodies = true
    show.stack.traces = true
  }
  take.screenshots = {
    before.and.after.each.step = true
    for.failures = true
    after.each.teeny.step = false
  }
  restart.browser.for.each = false
  browser.maximized = true
  clear.cookies.before.each.scenario = true
  proxy = {
    enabled = false
    http = ""
    https = ""
    no.proxy = ""
  }
}

environments {
  default = "local"
  local {
    base.url = "http://localhost:8080"
    api.buro.url = "http://localhost:8080/api/buro"
    api.antifraude.url = "http://localhost:8080/api/antifraude"
    api.core.url = "http://localhost:8080/api/core"
    timeout.default = 2000
    timeout.buro = 2000
    timeout.antifraude = 2000
    timeout.core = 2000
  }
  qa {
    base.url = "https://qa.bancodigital.com"
    api.buro.url = "https://qa.bancodigital.com/api/buro"
    api.antifraude.url = "https://qa.bancodigital.com/api/antifraude"
    api.core.url = "https://qa.bancodigital.com/api/core"
    timeout.default = 5000
    timeout.buro = 5000
    timeout.antifraude = 5000
    timeout.core = 5000
  }
  prod {
    base.url = "https://bancodigital.com"
    api.buro.url = "https://bancodigital.com/api/buro"
    api.antifraude.url = "https://bancodigital.com/api/antifraude"
    api.core.url = "https://bancodigital.com/api/core"
    timeout.default = 10000
    timeout.buro = 10000
    timeout.antifraude = 10000
    timeout.core = 10000
  }
}

buro {
  max.peticiones.por.hora = 10000
  duplicado.ventana.horas = 24
  puntuacion.minima.aprobacion = 650
  timeout.ms = 2000
}

antifraude {
  max.peticiones.por.hora = 10000
  timeout.ms = 2000
  nivel.riesgo.maximo = 75
}

core {
  max.peticiones.por.hora = 10000
  timeout.ms = 2000
  reintentos = 3
}

// === ARCHIVO: src/test/java/com/banco/digital/abilities/UsarServicioBuro.java ===
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

import static net.serenitybdd.rest.RestRequests.given;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.*;

public class UsarServicioBuro extends BaseAbility implements Ability {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsarServicioBuro.class);
    private static final String PROPERTY_BURO_URL = "api.buro.url";
    private static final String PROPERTY_TIMEOUT = "timeout.buro";
    private static final int DEFAULT_TIMEOUT_MS = 2000;
    private static final int PUNTUACION_MINIMA_APROBACION = 650;
    private static final int VENTANA_DUPLICADO_HORAS = 24;

    private final String baseUrl;
    private final int timeoutMs;
    private final EnvironmentVariables environmentVariables;
    private final Map<String, ConsultaBuroCache> cacheConsultas;

    public UsarServicioBuro(EnvironmentVariables environmentVariables) {
        this.environmentVariables = environmentVariables;
        this.baseUrl = Optional.ofNullable(environmentVariables.getProperty(PROPERTY_BURO_URL))
                .orElseThrow(() -> new IllegalStateException("Property " + PROPERTY_BURO_URL + " not configured"));
        this.timeoutMs = Optional.ofNullable(environmentVariables.getProperty(PROPERTY_TIMEOUT))
                .map(Integer::parseInt)
                .orElse(DEFAULT_TIMEOUT_MS);
        this.cacheConsultas = new HashMap<>();
        LOGGER.info("Ability UsarServicioBuro inicializada con URL: {} y timeout: {}ms", baseUrl, timeoutMs);
    }

    public static UsarServicioBuro configurado(EnvironmentVariables environmentVariables) {
        return new UsarServicioBuro(environmentVariables);
    }

    public static UsarServicioBuro como(Actor actor) {
        return actor.abilityTo(UsarServicioBuro.class);
    }

    public ResultadoConsultaBuro consultarPuntuacion(String clienteId) {
        LOGGER.info("Consultando puntuacion en buró para cliente: {}", clienteId);
        validarParametroRequerido(clienteId, "clienteId");
        verificarDuplicadoReciente(clienteId);

        long inicio = System.currentTimeMillis();
        try {
            var respuesta = given()
                    .baseUri(baseUrl)
                    .header("Content-Type", "application/json")
                    .header("X-Request-ID", generarIdRequest())
                    .body(construirPayloadConsulta(clienteId))
                    .timeout(Duration.ofMillis(timeoutMs))
                    .when()
                    .post("/consulta")
                    .then()
                    .statusCode(anyOf(is(200), is(201)))
                    .body("clienteId", equalTo(clienteId))
                    .extract()
                    .body();

            long duracion = System.currentTimeMillis() - inicio;
            LOGGER.info("Consulta de buró completada en {}ms para cliente: {}", duracion, clienteId);

            if (duracion > timeoutMs) {
                LOGGER.warn("Tiempo de respuesta ({}) excede el timeout configurado ({})", duracion, timeoutMs);
            }

            Integer puntuacion = respuesta.jsonPath().getInt("puntuacion");
            String nivelRiesgo = respuesta.jsonPath().getString("nivelRiesgo");
            Boolean tieneDeudasVencidas = respuesta.jsonPath().getBoolean("tieneDeudasVencidas");

            ResultadoConsultaBuro resultado = new ResultadoConsultaBuro(
                    clienteId,
                    puntuacion,
                    nivelRiesgo,
                    tieneDeudasVencidas,
                    duracion,
                    true
            );

            cacheConsultas.put(clienteId, new ConsultaBuroCache(clienteId, resultado));
            return resultado;

        } catch (Exception e) {
            long duracion = System.currentTimeMillis() - inicio;
            LOGGER.error("Error al consultar buró para cliente {}: {}", clienteId, e.getMessage());
            throw new RuntimeException("Error en consulta al buró de crédito: " + e.getMessage(), e);
        }
    }

    public boolean tienePuntuacionAprobada(Integer puntuacion) {
        if (puntuacion == null) {
            LOGGER.warn("Puntuación nula, no puede ser aprobada");
            return false;
        }
        boolean aprobada = puntuacion >= PUNTUACION_MINIMA_APROBACION;
        LOGGER.debug("Puntuación {} evaluada como aprobada: {}", puntuacion, aprobada);
        return aprobada;
    }

    public boolean esDuplicado(String clienteId) {
        return cacheConsultas.containsKey(clienteId);
    }

    private void verificarDuplicadoReciente(String clienteId) {
        if (cacheConsultas.containsKey(clienteId)) {
            ConsultaBuroCache cache = cacheConsultas.get(clienteId);
            if (cache.estaDentroVentana(VENTANA_DUPLICADO_HORAS)) {
                LOGGER.warn("Detectada consulta duplicada para cliente {} dentro de ventana de {} horas",
                        clienteId, VENTANA_DUPLICADO_HORAS);
            }
        }
    }

    private void validarParametroRequerido(String valor, String nombreParametro) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("Parámetro requerido: " + nombreParametro);
        }
    }

    private String generarIdRequest() {
        return "REQ-" + System.currentTimeMillis() + "-" + (int)(Math.random() * 10000);
    }

    private Map<String, Object> construirPayloadConsulta(String clienteId) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("clienteId", clienteId);
        payload.put("tipoConsulta", "AMPLIADA");
        payload.put("fechaConsulta", java.time.LocalDateTime.now().toString());
        return payload;
    }

    public static class ResultadoConsultaBuro {
        private final String clienteId;
        private final Integer puntuacion;
        private final String nivelRiesgo;
        private final Boolean tieneDeudasVencidas;
        private final long tiempoRespuestaMs;
        private final boolean exitosa;

        public ResultadoConsultaBuro(String clienteId, Integer puntuacion, String nivelRiesgo,
                                    Boolean tieneDeudasVencidas, long tiempoRespuestaMs, boolean exitosa) {
            this.clienteId = clienteId;
            this.puntuacion = puntuacion;
            this.nivelRiesgo = nivelRiesgo;
            this.tieneDeudasVencidas = tieneDeudasVencidas;
            this.tiempoRespuestaMs = tiempoRespuestaMs;
            this.exitosa = exitosa;
        }

        public String getClienteId() { return clienteId; }
        public Integer getPuntuacion() { return puntuacion; }
        public String getNivelRiesgo() { return nivelRiesgo; }
        public Boolean getTieneDeudasVencidas() { return tieneDeudasVencidas; }
        public long getTiempoRespuestaMs() { return tiempoRespuestaMs; }
        public boolean isExitosa() { return exitosa; }
    }

    private static class ConsultaBuroCache {
        private final String clienteId;
        private final ResultadoConsultaBuro resultado;
        private final long timestamp;

        public ConsultaBuroCache(String clienteId, ResultadoConsultaBuro resultado) {
            this.clienteId = clienteId;
            this.resultado = resultado;
            this.timestamp = System.currentTimeMillis();
        }

        public boolean estaDentroVentana(int horas) {
            long ventanaMs = horas * 60 * 60 * 1000L;
            return (System.currentTimeMillis() - timestamp) < ventanaMs;
        }
    }
}

// === ARCHIVO: src/test/java/com/banco/digital/abilities/UsarServicioAntifraude.java ===
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


// === ARCHIVO: src/test/java/com/banco/digital/abilities/UsarCoreBancario.java ===
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
```
