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
public class HPRestInteractionsTestSuite {
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
				.name("Iphone 19 Premium XD")
				.description("Telefono Super Alta Gama")
				.price(2500)
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
	@DisplayName("obtener producto hp1")
	public void obtener_producto_hp1() {
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
	@DisplayName("eliminar producto hp1")
	public void eliminar_producto_hp1() {
		Actor authorizeUser = Actor.named("Usuario Autorizado")
				.whoCan(CallAnApi.at("http://localhost:8081/"));

		authorizeUser.attemptsTo(
				Delete.from("api/v1/product/{sku}/").with(request -> request.pathParam("sku", this.crear_producto())));
		authorizeUser.should(
				seeThatResponse("El codigo de respuesta es 201 y el estado es verdadero",
						response -> response.statusCode(201)
								.body("status", equalTo(true))));
	}


}
