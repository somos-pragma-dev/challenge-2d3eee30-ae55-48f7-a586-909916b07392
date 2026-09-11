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