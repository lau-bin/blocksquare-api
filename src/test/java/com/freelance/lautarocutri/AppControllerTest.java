package com.freelance.lautarocutri;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class AppControllerTest {

    @Test
    void testHelloEndpoint() {
//        given()
//          .when().get("/hello")
//          .then()
//             .statusCode(200)
//             .body("message", is("hello"));
    }

}