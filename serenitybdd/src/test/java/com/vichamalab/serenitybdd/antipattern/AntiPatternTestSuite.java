package com.vichamalab.serenitybdd.antipattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.time.Duration;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import net.serenitybdd.annotations.Managed;
import net.serenitybdd.annotations.WithTag;
import net.serenitybdd.junit5.SerenityJUnit5Extension;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
@Disabled

@ExtendWith(SerenityJUnit5Extension.class)
public class AntiPatternTestSuite {
	@Managed(driver="firefox")
	private WebDriver navegador;	
	private String baseUrl="https://wikitravel.org/en/Peru";
	
	@Test
	@Tag("antipattern")
	@DisplayName("Buscar en wiki de viaje mediante anti patron")
	public void buscarPalabrasClave() {
		try {
		String palabraClave="Arequipa";
		navegador.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		navegador.get(baseUrl);
		capturarImagen(navegador,"paso1");
		navegador
			.findElement(By.id("searchInput"))
			.sendKeys(palabraClave, Keys.ENTER);

		WebDriverWait wait = new WebDriverWait(navegador, Duration.ofSeconds(6));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".searchresults")));
		capturarImagen(navegador,"paso2");
		List<WebElement> resultLinks = navegador.findElements(By.cssSelector(".mw-search-result-heading > a"));
		assertTrue(resultLinks.size()>0);
		resultLinks.get(0).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#mf-pagebanner > div.topbanner > div.name")));	
		capturarImagen(navegador,"paso3");
		assertThat(navegador.findElement(By.cssSelector("#mf-pagebanner > div.topbanner > div.name")).getText().toUpperCase().contains(palabraClave.toUpperCase()));
		} catch(Exception ex) {
			ex.printStackTrace();
			navegador.close();
		}
	}
	
	public void capturarImagen(WebDriver navegador,String filename) throws Exception{
		File scrFile = ((TakesScreenshot)navegador).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File(String.format("E:\\screenshots\\%1$s.png",filename)));
	}

}
