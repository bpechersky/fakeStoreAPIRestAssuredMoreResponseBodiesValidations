package com.fakestore.base;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public class TestBase {
    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://fakestoreapi.com";
    }
}
