
package slacktest.cucumber.list;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import slacktest.cucumber.SlackFileObjectConfig;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class FileListStepDefinition {
    String token = SlackFileObjectConfig.TOKEN;
    Response response;
    public FileListStepDefinition() {
        RestAssured.baseURI = SlackFileObjectConfig.OPEN_NOTIFY_API_URI;
    }

    public void listFile(String fileId, boolean exists) {
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("token", token);
        parameters.put("lastName", "Doe");
        response = given().
                log().
                all().
                queryParam("token", token).
                queryParam("types", "images").
                when().
                get(SlackFileObjectConfig.LIST).
                then().
                statusCode(SlackFileObjectConfig.HTTP_OK).
                extract().response();
        System.out.println("FileID Expected: " + fileId);
        System.out.println("list output:: " + response.getBody().asString());
        assertEquals(response.getBody().asString().contains(fileId), exists);
    }

}
