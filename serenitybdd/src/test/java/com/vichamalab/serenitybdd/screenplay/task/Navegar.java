package com.vichamalab.serenitybdd.screenplay.task;

import static net.serenitybdd.screenplay.Tasks.instrumented;

import com.vichamalab.serenitybdd.screenplay.page.Wikipage;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Open;

public class Navegar  implements Task {
	Wikipage homepage;
	
    public static Navegar paginainicio() {
        return instrumented(Navegar.class);
    }  

    @Step("{0} abre la pagina de inicio del wiki")
    public <T extends Actor> void performAs(T t) {
        t.attemptsTo(Open
          .browserOn()
          .the(homepage));
    }
}

