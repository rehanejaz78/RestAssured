import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static io.restassured.RestAssured.*;

public class Rest extends base{

    public String id = "1";  //for the time being.

    @Test
    public void headerAndOne (){
        String response= given().
                header("X-Api-Key","PMAK-632ad0a1218e6409fad7be48-240c6d9fa4a37268e1600ce30911063804")
                .header("Content-Type","application/json")
                .when().body("{\n" +
                        "    \"environment\": {\n" +
                        "        \"name\": \"Reejaz\",\n" +
                        "        \"values\": [\n" +
                        "            {\n" +
                        "                \"key\":        \"283\",\n" +
                        "                \"value\": \"Value\",\n" +
                        "                \"enabled\": true,\n" +
                        "                \"type\": \"personal\"\n" +
                        "            }\n" +
                        "        ]\n" +
                        "    }\n" +
                        "}").post(baseURI+basePath).then().log().body().extract().response().asString();

        JsonPath jsonPath = new JsonPath(response);
        id = jsonPath.get("environment.uid");
        System.out.println("id = " +id);

        try {

            FileWriter fileWriter = new FileWriter("src/test/java/ids.txt");
            fileWriter.write(id);
            fileWriter.close();

        }
        catch (IOException err){
            System.out.println(err);
        }


    }

    @Test
    public void Map(){  //by using map method
        Map<String, Object> requestHeader = new HashMap<>();
        requestHeader.put("X-Api-Key","PMAK-632ad0a1218e6409fad7be48-240c6d9fa4a37268e1600ce30911063804");
        requestHeader.put("Content-Type","application/json");

        HashMap<String,Object > hashMap = new HashMap<>();
        HashMap<String,Object> values = new HashMap<>();
        HashMap<String,Object > env = new HashMap<>();


        values.put("key","284");
        values.put("value","value");
        values.put("enabled",true);
        values.put("type","personal");

        Object[] myobj = new Object[1];
        myobj[0] = values;

        env.put("name","rehan");
        env.put("values",myobj);

        hashMap.put("environment",env);


        given().
                headers(requestHeader).log().all()
                .when()
                .body(hashMap)
                .post(baseURI+basePath).then().log().all();


    }

    
    @Test
    public void Filemethod() throws IOException {
    byte[] file = Files.readAllBytes(Paths.get("src/test/body.json"));

    String input = new String(file);
    
    given(). header("X-Api-Key","PMAK-632ad0a1218e6409fad7be48-240c6d9fa4a37268e1600ce30911063804")
                .header("Content-Type","application/json")
            .when().log().body()
            .body(input)
            .post(baseURI+basePath)
            .then().log().all().statusCode(200);
    
    }

    @Test
    public void GET_request(){

        given(). header("X-Api-Key","PMAK-632ad0a1218e6409fad7be48-240c6d9fa4a37268e1600ce30911063804")
                .header("Content-Type","application/json")
                .pathParam("id","21032804-7d8528cc-3679-4820-a68e-0ef97e739e83")
                .when().log().body()
                .get(baseURI+basePath+"/{id}")
                .then().log().all().statusCode(200);
    }


    @Test
    public void Update_request () throws IOException {
        byte[] inp = Files.readAllBytes(Paths.get("src/test/java/ids.txt"));
        String Id = new String(inp);


        given(). header("X-Api-Key","PMAK-632ad0a1218e6409fad7be48-240c6d9fa4a37268e1600ce30911063804")
                .header("Content-Type","application/json")
                .pathParam("id",Id)
                .when().log().body()
                .body("{\n" +
                        "  \"environment\": {\n" +
                        "    \"name\": \"Production\",\n" +
                        "    \"values\": [\n" +
                        "      {\n" +
                        "        \"key\": \"788\",\n" +
                        "        \"value\": \"myvalue\",\n" +
                        "        \"enabled\": true,\n" +
                        "        \"type\": \"private\"\n" +
                        "      }\n" +
                        "    ]\n" +
                        "  }\n" +
                        "}")
                .put("https://api.getpostman.com/environments/{id}")
                .then().log().body();
        System.out.println("hello");



    }

    @Test
    public void delete() throws IOException {
        byte[] inp = Files.readAllBytes(Paths.get("src/test/java/ids.txt"));
        String Id = new String(inp);

        given().baseUri("https://api.getpostman.com/").pathParam("id", Id)
                .header("X-Api-Key","PMAK-632ad0a1218e6409fad7be48-240c6d9fa4a37268e1600ce30911063804")
                .header("Content-Type","application/json")
                .log().all()
                .when()
                .delete("environments/{id}")
                .then().log().body();
    }




}
