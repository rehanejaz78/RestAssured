import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Get {



    @Test(priority = 1)
    public static void GET_Test(){

        RestAssured.baseURI= "https://reqres.in/api";

        int code =given().log().all()
                .when().get("users?page=2").then().log().all().statusCode(200).extract().response().statusCode();

        System.out.println(" Status code of this GET api is : "+code);


    }

    @Test(priority = 2)
    public void POST_test(){
        RestAssured.baseURI= "https://reqres.in/api";

        String res = given().body("{\n" +
                "    \"name\": \"rehan ejaz\",\n" +
                "    \"job\": \"leader\"\n" +
                "}").post("/users").then().log().all().statusCode(201).log().all().extract().response().asString() ;



        JsonPath jsonPath = new JsonPath(res);

        String id = jsonPath.get("id");
        System.out.println(id);
        System.out.println("Record posted successfully");

        PUT_Test(id);

    }


    public void PUT_Test(String id) {

        RestAssured.baseURI= "https://reqres.in/api";
        given().log().all()
                .body("{\n" +
                        "    \"name\": \"rehan\",\n" +
                        "    \"job\": \"automation\"\n" +
                        "}")
                .when().put("/users/" + id + "")
                .then().log().all()
                .assertThat().statusCode(200)
                .extract().response().asString();
        System.out.println("Record Updated Successfully !!");

        Delete(id);

    }

    public void Delete(String id) {

        RestAssured.baseURI= "https://reqres.in/api";


        given().log().all()
                .when().delete("/users/" + id + "")
                .then().log().all()
                .assertThat().statusCode(204)
                .extract().response().asString();
        System.out.println("Record deleted successfully !");

    }


}

