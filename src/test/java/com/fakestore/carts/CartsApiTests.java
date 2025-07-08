package com.fakestore.carts;

import com.fakestore.base.TestBase;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class CartsApiTests extends TestBase {

    @Test
    public void testGetAllCarts() {
        get("/carts")
            .then().statusCode(200)
            .body("[0].id", notNullValue())
            .body("[0].userId", notNullValue())
            .body("[0].date", notNullValue())
            .body("[0].products", notNullValue());
    }

    @Test
    public void testGetSingleCart() {
        get("/carts/1")
            .then().statusCode(200)
            .body("id", equalTo(1))
            .body("userId", notNullValue())
            .body("date", notNullValue())
            .body("products", notNullValue());
    }

    @Test
    public void testGetUserCarts() {
        get("/carts/user/2")
            .then().statusCode(200);
    }

    @Test
    public void testGetLimitedCarts() {
        given().queryParam("limit", 3)
        .when().get("/carts")
        .then().statusCode(200)
        .body("size()", equalTo(3));
    }

    @Test
    public void testAddCart() {
        String payload = "{ \"userId\": 5, \"date\": \"2020-02-03\", \"products\": [ { \"productId\": 1, \"quantity\": 2 } ] }";
        given().contentType(ContentType.JSON).body(payload)
        .when().post("/carts")
        .then().statusCode(200)
        .body("userId", equalTo(5));
    }

    @Test
    public void testUpdateCart() {
        String payload = "{ \"userId\": 5, \"date\": \"2020-03-03\", \"products\": [ { \"productId\": 1, \"quantity\": 4 } ] }";
        given().contentType(ContentType.JSON).body(payload)
        .when().put("/carts/7")
        .then().statusCode(200);
    }

    @Test
    public void testDeleteCart() {
        delete("/carts/7")
            .then().statusCode(anyOf(is(200), is(204)));
    }
}
