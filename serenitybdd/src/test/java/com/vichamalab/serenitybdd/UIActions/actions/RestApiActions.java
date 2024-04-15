package com.vichamalab.serenitybdd.UIActions.actions;

import static io.restassured.RestAssured.given;
import org.junit.jupiter.api.Assertions;

import com.vichamalab.serenitybdd.dto.ProductRequestDTO;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.serenitybdd.core.steps.UIInteractions;

public class RestApiActions extends UIInteractions {

	Response response;
	ProductRequestDTO productRequest;
	String originalSku="";
	String currentMethod ="";
	String nombreEsperado="";
	String descripcionEsperada="";
	float precioEsperado=0;
	String currentUrl="";
	
    @Given("Dada una api con url")
    public void dadoUnaApiConUrl() {
    	RestAssured.baseURI="http://localhost:8081";
    	currentUrl="/api/v1/product/";
    	productRequest = new ProductRequestDTO();
    }

    @When("Cuando se envia una peticion POST con el nombre, la descripcion y el precio a la api")
    public void cuandoSeEnviaPeticionConNombreDescripcionYPrecio(Long id) {
    	productRequest.setName("Nombre");
    	productRequest.setDescription("Descripcion");
    	productRequest.setPrice(1500);
		response = given().contentType(ContentType.JSON)// Headers
				.body(productRequest).when().post(currentUrl)// Uri
				.then().extract().response();
    }

    @Then("Entonces se recibe una respuesta exitosa con codigo y mensaje")
    public void entoncesRecibeRespuestaExitosayMensaje() {
    	int statusCode = 201;
    	Assertions.assertEquals(statusCode,response.getStatusCode());
    }
}