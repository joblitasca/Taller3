package com.vichamalab.serenitybdd.screenplay.page;

import net.serenitybdd.annotations.DefaultUrl;
import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.screenplay.targets.Target;


import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
@Disabled

@DefaultUrl("https://wikitravel.org/en/Peru")
public class Wikipage extends PageObject {
	//public static final Target CASILLA_BUSQUEDA = Target.the("casilla de búsqueda").locatedBy("#searchInput");
	public static final Target CASILLA_BUSQUEDA = Target.the("casilla de búsqueda").located(By.cssSelector("#searchInput"));
	public static final Target TITULO_PAGINA = Target.the("titulo de pagina").locatedBy("#mf-pagebanner > div.topbanner > div.name");
}

