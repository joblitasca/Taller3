package com.vichamalab.serenitybdd.screenplay;

import static net.serenitybdd.screenplay.GivenWhenThen.givenThat;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.GivenWhenThen.then;
import static net.serenitybdd.screenplay.GivenWhenThen.when;
import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import com.vichamalab.serenitybdd.screenplay.question.Articulo;
import com.vichamalab.serenitybdd.screenplay.task.Buscar;
import com.vichamalab.serenitybdd.screenplay.task.Navegar;
import net.serenitybdd.annotations.Managed;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.annotations.CastMember;

@ExtendWith(SerenityJUnit5Extension.class)
public class ScreenplayTestSuite {
	@Managed(driver="firefox")
	private WebDriver navegador;
	
	@CastMember(name = "UsuarioAnonimo")    
	Actor anonimo;	
    
	@Test
	@Tag("screenplay")
	@DisplayName("Buscar en wiki de viaje usando ScreenPlay Pattern")
	public void buscarPalabrasClave() {
		anonimo.can(BrowseTheWeb.with(navegador));
		anonimo.has(Navegar.paginainicio());
		anonimo.attemptsTo(Buscar.porPalabraClave("Arequipa"));
		anonimo.should(
				seeThat("El titulo del articulo", Articulo.titulo(),equalTo("Arequipa"))
				);
	}
	
	@Test
	@Tag("screenplay-bdd")
	@DisplayName("Buscar en wiki de viaje usando ScreenPlay Pattern with BDD")
	public void buscarPalabrasClaveBDD() {	
		anonimo.can(BrowseTheWeb.with(navegador));
		givenThat(anonimo).wasAbleTo(Navegar.paginainicio());
		when(anonimo).attemptsTo(Buscar.porPalabraClave("Arequipa"));
		then(anonimo).should(
				seeThat("El titulo del articulo", Articulo.titulo(),equalTo("Arequipa"))
				);
	}	
}
