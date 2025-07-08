package com.fakestore.products;

import com.fakestore.base.TestBase;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ProductsApiTests extends TestBase {

    @Test
    public void testGetAllProducts() {
        get("/products")
            .then().statusCode(200)
            .body("[0].id", notNullValue())
            .body("[0].title", notNullValue())
            .body("[0].price", notNullValue())
            .body("[0].description", notNullValue())
            .body("[0].image", notNullValue())
            .body("[0].category", notNullValue());
    }

    @Test
    public void testGetSingleProduct() {
        get("/products/1")
            .then().statusCode(200)
            .body("id", equalTo(1))
            .body("title", notNullValue())
            .body("price", notNullValue())
            .body("description", notNullValue())
            .body("image", notNullValue())
            .body("category", notNullValue());
    }

    @Test
    public void testLimitProducts() {
        given().queryParam("limit", 5)
        .when().get("/products")
        .then().statusCode(200)
        .body("size()", equalTo(5));
    }

    @Test
    public void testSortProductsDesc() {
        given().queryParam("sort", "desc")
        .when().get("/products")
        .then().statusCode(200);
    }

    @Test
    public void testGetAllCategories() {
        get("/products/categories")
            .then().statusCode(200)
            .body("size()", greaterThan(0));
    }

    @Test
    public void testGetProductsByCategory() {
        get("/products/category/electronics")
            .then().statusCode(200)
            .body("[0].category", equalTo("electronics"));
    }

    @Test
    public void testAddProduct() {
        String payload = "{ \"title\": \"Test Product\", \"price\": 10.5, \"description\": \"Test Description\", \"image\": \"https://i.pravatar.cc\", \"category\": \"electronic\" }";
        given().contentType(ContentType.JSON).body(payload)
        .when().post("/products")
        .then().statusCode(200)
        .body("id", notNullValue())
        .body("title", equalTo("Test Product"))
        .body("price", equalTo(10.5f))
        .body("description", equalTo("Test Description"))
        .body("image", equalTo("https://i.pravatar.cc"))
        .body("category", equalTo("electronic"));
    }

    @Test
    public void testUpdateProduct() {
        String payload = "{ \"title\": \"Updated Product\", \"price\": 20.5, \"description\": \"Updated\", \"image\": \"https://i.pravatar.cc\", \"category\": \"books\" }";
        given().contentType(ContentType.JSON).body(payload)
        .when().put("/products/1")
        .then().statusCode(200)
        .body("title", equalTo("Updated Product"));
    }

    @Test
    public void testDeleteProduct() {
        delete("/products/1")
            .then().statusCode(anyOf(is(200), is(204)));
    }
}
