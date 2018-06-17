package slacktest.cucumber.upload;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;

import org.codehaus.jackson.JsonNode;
import slacktest.cucumber.SlackFileObjectConfig;
import slacktest.cucumber.delete.FileDeleteStepDefinition;
import slacktest.cucumber.list.FileListStepDefinition;
import slacktest.cucumber.util.Util;

public class FileUploadStepDefinition {
    String token = SlackFileObjectConfig.TOKEN;
    Response response;
    public FileUploadStepDefinition() {

        RestAssured.baseURI = SlackFileObjectConfig.OPEN_NOTIFY_API_URI;
    }
    public void uploadFile(HashMap<String, String> record) {
        response =
                given().
                        log().all().
                        contentType("multipart/form-data").
                        multiPart("file", new File(record.get("file"))).
                        multiPart("channels", record.get("channels")).
                        multiPart("token", token).
                        multiPart("file_name", record.get("file_name")).
                        multiPart("filetype", record.get("filetype")).
                        multiPart("initial_comment", record.get("initial_comment")).
                        multiPart("title", record.get("title")).
                        when().
                        post(SlackFileObjectConfig.UPLOAD).
                        then().
                        statusCode(SlackFileObjectConfig.HTTP_OK).
                        extract().response();
    }

    public void validateResponseIsJson() {
        ValidatableResponse vr = response.then().statusCode(200);
    }

    public void validateResponseContents(HashMap<String, String> record) {
        String respBody = response.getBody().asString();
        // Remove file_name as that for some reason not part of response.
        record.remove("file_name");
        // Remove channels too as it displays channel id.
        record.remove("channels");
        for(Map.Entry<String, String> e: record.entrySet()) {
            String expectedData = e.getValue();
            if(e.getKey().equals("file")) {
                expectedData = expectedData.substring(expectedData.lastIndexOf(File.separator)+1);
            }
            assertTrue(respBody.contains(e.getKey()));
            assertTrue(respBody.contains(expectedData));
        }
    }

    public void validateResponseForImage() {
        JsonNode jnode = Util.toJson(response.getBody().asString());
        JsonNode fileNode = jnode.path("file");
        for(String img_type : SlackFileObjectConfig.IMG_TYPES) {
            assertTrue(fileNode.has(img_type));
        }
    }

    public void validateFileIsUploadedByListing(boolean exists) {
        FileListStepDefinition fl = new FileListStepDefinition();
        JsonNode jnode = Util.toJson(response.getBody().asString());
        JsonNode fileNode = jnode.path("file");
        fl.listFile(fileNode.path("id").getValueAsText(),exists);
    }

    public void deleteFile(String expectedStatus) {
        FileDeleteStepDefinition fl = new FileDeleteStepDefinition();
        JsonNode fileNode = Util.toJson(response.getBody().asString()).path("file");
        fl.deleteFile(fileNode.path("id").getValueAsText(), expectedStatus);
    }

    // Unused Practice methods.

    public void uploadFile() {
        response =
                given().
                        log().all().
                        contentType("multipart/form-data").
                        multiPart("file", new File("/Users/vgmehta/Downloads/MAY_BATTING.pdf")).
                        multiPart("channels", "general").
                        multiPart("token", token).
                        when().
                        post(SlackFileObjectConfig.UPLOAD).
                        then().
                        statusCode(SlackFileObjectConfig.HTTP_OK).
                        extract().response();
    }

    public void uploadContent(String content) {
        response =
                given().
                        log().all().
                        contentType("multipart/form-data").
                        multiPart("content", content).
                        multiPart("channels", "general").
                        multiPart("token", token).
                        when().
                        post(SlackFileObjectConfig.UPLOAD).
                        then().
                        statusCode(SlackFileObjectConfig.HTTP_OK).
                        extract().response();

    }

    public void uploadWithoutContentOrFile() {
        response =
                given().
                        log().all().
                        contentType("multipart/form-data").
                        multiPart("channels", "general").
                        multiPart("token", token).
                        when().
                        post(SlackFileObjectConfig.UPLOAD).
                        then().
                        statusCode(SlackFileObjectConfig.HTTP_OK).
                        extract().response();
    }

}
