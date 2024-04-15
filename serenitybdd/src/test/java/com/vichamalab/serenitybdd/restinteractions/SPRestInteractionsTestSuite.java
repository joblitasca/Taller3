package com.vichamalab.serenitybdd.restinteractions;

import org.junit.jupiter.api.extension.ExtendWith;

import com.vichamalab.serenitybdd.dto.ProductRequestDTO;

import io.cucumber.java.Before;
import net.serenitybdd.core.Serenity;
//import io.cucumber.java.Before;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.serenitybdd.screenplay.rest.interactions.Delete;
import net.serenitybdd.screenplay.rest.interactions.Get;
import net.serenitybdd.screenplay.rest.interactions.Post;
import net.thucydides.model.util.EnvironmentVariables;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("SerenityRestInteractions")
@ExtendWith(SerenityJUnit5Extension.class)
public class SPRestInteractionsTestSuite {
	private EnvironmentVariables environmentVariables;
	@Test
	@Tag("rest")
	@DisplayName("crear producto")
	public String crear_producto() {
		ProductRequestDTO productRequest = ProductRequestDTO.builder()
				.name("Iphone 13 Premium")
				.description("Telefono alta gama")
				.price(1400)
				.build();

		Actor authorizeUser = Actor.named("Usuario Autorizado")
				.whoCan(CallAnApi.at("http://localhost:8081/"));

		authorizeUser.attemptsTo(
				Post.to("api/v1/product/")
						.with(request -> request.header("Content-Type", "application/json")
								.body(productRequest)));
		authorizeUser.should(
				seeThatResponse("El codigo de respuesta es 200 y el estado es verdadero",
						response -> response.statusCode(201)
								.body("status", equalTo(true))));

		String sku_response = SerenityRest.lastResponse().path("sku");
		return sku_response;
	}

	// SAD PATH
	@Test
	@Tag("rest")
	@DisplayName("obtener producto sp1")
	public void obtener_producto_sp1() {
		Actor authorizeUser = Actor.named("Usuario Autorizado")
				.whoCan(CallAnApi.at("http://localhost:8081/"));

		String skuTemporal = "00";
		authorizeUser.attemptsTo(
				Get.resource("api/v1/product/" + skuTemporal));
		authorizeUser.should(
				seeThatResponse("El codigo de respuesta es 404 y el estado es 404",
						response -> response.statusCode(404)
								.body("status", equalTo(404))));
	}
	
	@Test
	@Tag("rest")
	@DisplayName("obtener producto sp2")
	public void obtener_producto_sp2() {
		Actor authorizeUser = Actor.named("Usuario Autorizado")
				.whoCan(CallAnApi.at("http://localhost:8081/"));

	String skuTemporal = "5b662dd1-7f46-49c0-a81d-7c9ac9e35d2d";
	authorizeUser.attemptsTo(
			Get.resource("api/v2/product/" + skuTemporal));
	authorizeUser.should(
			seeThatResponse("El codigo de respuesta es 404 y el estado es 404",
					response -> response.statusCode(404)
							.body("status", equalTo(404))));
	}

	@Test
	@Tag("rest")
	@DisplayName("obtener producto sp3")
	public void obtener_producto_sp3() {
		Actor authorizeUser = Actor.named("Usuario Autorizado")
				.whoCan(CallAnApi.at("http://localhost:8081/"));

	String skuTemporal = "";
	authorizeUser.attemptsTo(
			Get.resource("api/v1/producto/" + skuTemporal));
	authorizeUser.should(
			seeThatResponse("El codigo de respuesta es 404 y el estado es 404",
					response -> response.statusCode(404)
							.body("status", equalTo(404))));
	}

//ELIMINAR	
	@Test
	@Tag("rest")
	@DisplayName("eliminar producto sp1")
	public void eliminar_producto_sp1() {
		Actor authorizeUser = Actor.named("Usuario Autorizado")
				.whoCan(CallAnApi.at("http://localhost:8081/"));

		String skuTemporal = "abcd";
		authorizeUser.attemptsTo(
				Delete.from("api/v1/product/{sku}").with(request -> request.pathParam("sku", skuTemporal)));
		authorizeUser.should(
				seeThatResponse("El codigo de respuesta es 404 y el estado es 404",
						response -> response.statusCode(404)
								.body("status", equalTo(404))));
	}
	
	@Test
	@Tag("rest")
	@DisplayName("eliminar producto sp2")
	public void eliminar_producto_sp2() {
		Actor authorizeUser = Actor.named("Usuario Autorizado")
				.whoCan(CallAnApi.at("http://localhost:8081/"));

		String priceTemporal = "17";
		authorizeUser.attemptsTo(
				Delete.from("api/v1/product/{price}").with(request -> request.pathParam("price", priceTemporal)));
		authorizeUser.should(
				seeThatResponse("El codigo de respuesta es 404 y el estado es 404",
						response -> response.statusCode(404)
								.body("status", equalTo(404))));
	}
	@Test
	@Tag("rest")
	@DisplayName("eliminar producto sp3")
	public void eliminar_producto_sp3() {
		Actor authorizeUser = Actor.named("Usuario Autorizado")
				.whoCan(CallAnApi.at("http://localhost:8081/"));

		String skuTemporal = "";
		authorizeUser.attemptsTo(
				Delete.from("api/v1/product/{sku}").with(request -> request.pathParam("sku", skuTemporal)));
		authorizeUser.should(
				seeThatResponse("El codigo de respuesta es 405 y el estado es 405",
						response -> response.statusCode(405)
								.body("status", equalTo(405))));
	}

}
