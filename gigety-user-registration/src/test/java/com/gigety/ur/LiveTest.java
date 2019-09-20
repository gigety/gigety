package com.gigety.ur;

import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class LiveTest {

	private static String APP_ROOT = "http://localhost:8084";
	
	/**
	 *  givenUser_whenGetCreateForm_thenForbidden
	 *  Test the @preauthorize tags for /user/?form
	 */
	@Test
	public void givenUser_whenGetCreateForm_thenForbidden() {
		givenAuth("q@q.com", "password")
		.then().statusCode(403)
		.when().get("/user/?form").prettyPeek();
	}

	/**
	 *  givenAdmin_whenGetCreateForm_thenOK
	 *  Test the @preauthorize tags for /user/?form
	 */
	@Test
	public void givenAdmin_whenGetCreateForm_thenOK() {
		givenAuth("samuelmosessegal@gmail.com", "password")
		.then().statusCode(200)
		.when().get("/user/?form").prettyPeek();
	}

	
	private final RequestSpecification givenAuth(String username, String password) {
		return RestAssured.given().baseUri(APP_ROOT).auth().form(username, password);
	}
}
