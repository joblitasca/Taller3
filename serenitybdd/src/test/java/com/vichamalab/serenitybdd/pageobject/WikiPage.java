package com.vichamalab.serenitybdd.pageobject;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import net.serenitybdd.annotations.DefaultUrl;
import net.serenitybdd.core.pages.PageObject;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
@Disabled

@DefaultUrl("https://wikitravel.org/en/Peru")
public class WikiPage   extends PageObject{
		@FindBy(id="searchInput")
		private WebElement casillaBusqueda;
		
		@FindBy(css="#mf-pagebanner > div.topbanner > div.name")
		private WebElement tituloPagina;
		
		public void abrirPagina() {
			this.open();
		}
		
		public void buscarPalabraClave(String palabraClave) {
			casillaBusqueda.sendKeys(palabraClave,Keys.ENTER);
		}
		
		public void seleccionarElPrimerResultado() {
			List<WebElement> resultLinks = this.getDriver().findElements(By.cssSelector(".mw-search-result-heading > a"));
			assertTrue(resultLinks.size()>0);
			resultLinks.get(0).click();
		}
		
		public void validarTitulo(String tituloEsperado) {
			assertThat(tituloPagina.getText().contains(tituloEsperado));
		}
}
