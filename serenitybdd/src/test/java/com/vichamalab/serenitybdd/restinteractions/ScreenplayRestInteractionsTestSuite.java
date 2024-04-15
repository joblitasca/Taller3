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
public class ScreenplayRestInteractionsTestSuite {
	private EnvironmentVariables environmentVariables;

	@Test
	@Tag("rest")
	@DisplayName("Listar productos")
	public void listar_productos() {
		String baseUrl = environmentVariables.optionalProperty("restapi.baseurl")
				.orElse("");

		Actor authorizeUser = Actor.named("Usuario Autorizado")
				.whoCan(CallAnApi.at(baseUrl));

		authorizeUser.attemptsTo(
				Get.resource("api/v1/product/"));
		authorizeUser.should(
				seeThatResponse("El codigo de respuesta es 200 y el estado es verdadero",
						response -> response.statusCode(200)
								.body("status", equalTo(true))));
	}

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

	// TAREA HAPPY PATH Y SAD PATH DELETE AND GET
	// HAPPY PATH

	@Test
	@Tag("rest")
	@DisplayName("obtener producto")
	public void obtener_producto() {
		Actor authorizeUser = Actor.named("Usuario Autorizado")
				.whoCan(CallAnApi.at("http://localhost:8081/"));

		authorizeUser.attemptsTo(
				Get.resource("api/v1/product/{sku}/").with(request -> request.pathParam("sku", this.crear_producto())));
		authorizeUser.should(
				seeThatResponse("El codigo de respuesta es 201 y el estado es verdadero",
						response -> response.statusCode(201)
								.body("status", equalTo(true))));
	}

	@Test
	@Tag("rest")
	@DisplayName("eliminar producto")
	public void eliminar_producto() {
		Actor authorizeUser = Actor.named("Usuario Autorizado")
				.whoCan(CallAnApi.at("http://localhost:8081/"));

		authorizeUser.attemptsTo(
				Delete.from("api/v1/product/{sku}/").with(request -> request.pathParam("sku", this.crear_producto())));
		authorizeUser.should(
				seeThatResponse("El codigo de respuesta es 201 y el estado es verdadero",
						response -> response.statusCode(201)
								.body("status", equalTo(true))));
	}

	// SAD PATH
	@Test
	@Tag("rest")
	@DisplayName("obtener producto sad path")
	public void obtener_producto_sad_path() {
		Actor authorizeUser = Actor.named("Usuario Autorizado")
				.whoCan(CallAnApi.at("http://localhost:8081/"));

		String skuTemporal = "6ab5af78-daea-4d48-aede-04f945084a9b";
		authorizeUser.attemptsTo(
				Get.resource("api/v1/product/" + skuTemporal));
		authorizeUser.should(
				seeThatResponse("El codigo de respuesta es 404 y el estado es 404",
						response -> response.statusCode(404)
								.body("status", equalTo(404))));
	}

	@Test
	@Tag("rest")
	@DisplayName("eliminar producto sad path")
	public void eliminar_producto_sad_path() {
		Actor authorizeUser = Actor.named("Usuario Autorizado")
				.whoCan(CallAnApi.at("http://localhost:8081/"));

		String skuTemporal = "28666904-32af-4ac6-a2a6-2c9cc6662721";
		authorizeUser.attemptsTo(
				Delete.from("api/v1/product/{sku}").with(request -> request.pathParam("sku", skuTemporal)));
		authorizeUser.should(
				seeThatResponse("El codigo de respuesta es 404 y el estado es 404",
						response -> response.statusCode(404)
								.body("status", equalTo(404))));
	}
}
