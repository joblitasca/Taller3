package com.vichamalab.serenitybdd.screenplay.task;

import org.openqa.selenium.Keys;
import com.vichamalab.serenitybdd.screenplay.page.Wikipage;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Enter;

public class Buscar implements Task {
	private String palabra;

	public Buscar(String palabra) {
        this.palabra = palabra;
    }

    public static Task porPalabraClave(String palabra) {
        return Instrumented
          .instanceOf(Buscar.class)
          .withProperties(palabra);
    }
    
    @Step("{0} buscar por palabra clave '#palabra'")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
        	Enter
          .theValue(palabra)
          .into(Wikipage.CASILLA_BUSQUEDA)
          .thenHit(Keys.RETURN)
          );
    }
}
