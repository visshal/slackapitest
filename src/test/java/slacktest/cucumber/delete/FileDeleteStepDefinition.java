package slacktest.cucumber.delete;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import slacktest.cucumber.SlackFileObjectConfig;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;

public class FileDeleteStepDefinition {
    String token = "xoxp-381371318146-382262893494-382267257526-41f0649fcb21ad28e17d73403fc35d3c";
    Response response;
    public FileDeleteStepDefinition() {
        RestAssured.baseURI = SlackFileObjectConfig.OPEN_NOTIFY_API_URI;
    }

    public void deleteFile(String fileId, String expectedMessage) {
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("token", token);
        parameters.put("lastName", "Doe");
        response = given().
                log().
                all().
                queryParam("token", token).
                queryParam("file", fileId).
                when().
                post(SlackFileObjectConfig.DELETE).
                then().
                statusCode(SlackFileObjectConfig.HTTP_OK).
                extract().response();
        assertTrue(response.getBody().asString().contains(expectedMessage));
    }
}
