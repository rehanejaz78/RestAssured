import io.restassured.RestAssured;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Get {


    @Test
    public static void GET_Test(){

        RestAssured.baseURI= "https://reqres.in/api";

        int code =given().log().all()
                .when().get("users?page=2").then().log().all().statusCode(200).extract().response().statusCode();

        System.out.println(" Status code of this GET api is : "+code);
        System.out.println("kjchs");

    }
}

/*
* RestAssured.baseURI = "https://reqres.in/api";
        given().queryParam("token", "QpwL5tke4Pnpja7X4")

                .log().all()
                .body("{\n" +
                        "    \"email\": \"eve.holt@reqres.in\",\n" +
                        "    \"password\": \"cityslicka\"\n" +
                        "}")
                .when().post("/login")
                .then().log().all()
                .statusCode(200)
                .extract().response().asString();
* */
