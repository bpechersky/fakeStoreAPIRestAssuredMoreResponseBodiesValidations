package com.fakestore.users;

import com.fakestore.base.TestBase;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class UsersApiTests extends TestBase {

    @Test
    public void testGetAllUsers() {
        get("/users")
            .then().statusCode(200)
            .body("[0].id", notNullValue())
            .body("[0].email", notNullValue())
            .body("[0].username", notNullValue())
            .body("[0].name.firstname", notNullValue())
            .body("[0].name.lastname", notNullValue())
            .body("[0].address.city", notNullValue())
            .body("[0].phone", notNullValue());
    }

    @Test
    public void testGetSingleUser() {
        get("/users/1")
            .then().statusCode(200)
            .body("id", equalTo(1))
            .body("email", notNullValue())
            .body("username", notNullValue())
            .body("name.firstname", notNullValue())
            .body("name.lastname", notNullValue())
            .body("address.city", notNullValue())
            .body("phone", notNullValue());
    }

    @Test
    public void testAddUser() {
        String payload = "{ \"email\": \"john@example.com\", \"username\": \"johndoe\", \"password\": \"secure123\", \"name\": { \"firstname\": \"John\", \"lastname\": \"Doe\" }, \"address\": { \"city\": \"Berlin\", \"street\": \"123 Main St\", \"number\": 123, \"zipcode\": \"10001\", \"geolocation\": { \"lat\": \"40.7128\", \"long\": \"-74.0060\" } }, \"phone\": \"123-456-7890\" }";

        given().contentType(ContentType.JSON).body(payload)
                .when().post("/users")
                .then()
                .log().all()
                .statusCode(200)
                .body("id", notNullValue()); // Only reliable field returned
    }


    @Test
    public void testUpdateUser() {
        String payload = "{ \"username\": \"updateduser\", \"phone\": \"987-654-3210\" }";
        given().contentType(ContentType.JSON).body(payload)
        .when().put("/users/1")
        .then().statusCode(200)
        .body("username", equalTo("updateduser"));
    }

    @Test
    public void testDeleteUser() {
        delete("/users/1")
            .then().statusCode(anyOf(is(200), is(204)));
    }
}
