package com.vichamalab.serenitybdd.pageobject;

import java.time.Duration;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import net.serenitybdd.annotations.Managed;
import net.serenitybdd.annotations.WithTag;
import net.serenitybdd.junit5.SerenityJUnit5Extension;

@ExtendWith(SerenityJUnit5Extension.class)
public class PageObjectTestSuite {
	@Managed(driver="firefox")
	private WebDriver navegador;	
	private String baseUrl="https://wikitravel.org/en/Peru";
	
	WikiPage wikipedia;
	
	@Test
	@Tag("pageobject")
	@DisplayName("Buscar en wiki de viaje usando Page Object Pattern")
	public void buscarPalabrasClaveconPageObject() {
		String palabraClave="arequipa";
		navegador.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		wikipedia.setDriver(navegador);
		wikipedia.open();
		wikipedia.buscarPalabraClave(palabraClave);
		wikipedia.seleccionarElPrimerResultado();
		wikipedia.validarTitulo(palabraClave);
	}
}
