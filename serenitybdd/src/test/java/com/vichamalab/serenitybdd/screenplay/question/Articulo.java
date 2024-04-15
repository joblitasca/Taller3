package com.vichamalab.serenitybdd.screenplay.question;

import com.vichamalab.serenitybdd.screenplay.page.Wikipage;

import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;

public class Articulo {
	public static Question<String> titulo(){
		return actor -> Text.of(Wikipage.TITULO_PAGINA).answeredBy(actor);
		
		//return actor -> Text.of(WikiTravelSearchPage.TITULO_PAGINA).viewedBy(actor).asString();
	}
}
