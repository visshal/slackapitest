package slacktest.cucumber.feature.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;
import slacktest.cucumber.upload.FileUploadStepDefinition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SlackFileObjectTestSteps {

    FileUploadStepDefinition service;


    @Given("User has access token to use file.upload API")
    public void user_has_access_token_to_use_file_upload_API() {
        // Write code here that turns the phrase above into concrete actions
        service = new FileUploadStepDefinition();
    }

    @Then("User can see successful response")
    public void user_can_see_successful_response() {
        service.validateResponseIsJson();
    }

    @Then("User can see the uploaded file in file.list")
    public void user_can_see_the_uploaded_file_in_file_list() throws Throwable {
        System.out.println("Sleeping for maximum 30 seconds to so that DB can be sync for files.list api.");
        Thread.sleep(30000);
        service.validateFileIsUploadedByListing(true);
    }

    @Then("User can see all expected thumbnails urls in response")
    public void user_can_see_all_expected_thumbnails_urls_in_response() {
        service.validateResponseForImage();
    }

    @When("User uploads file using following parameters")
    public void user_uploads_file_using_following_parameters(DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : data) {
            HashMap<String, String> record = new HashMap<String, String>();
            for (Map.Entry<String, String> entry : row.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                record.put(key, value);
            }
            service.uploadFile(record);
            service.validateResponseContents(record);
        }
    }

    @Then("User can delete uploaded file using file.delete")
    public void user_can_delete_uploaded_file_using_file_delete() {
        service.deleteFile("true");
    }

    @Then("Now User can not see file in file.list")
    public void now_User_can_not_see_file_in_file_list() {
        service.validateFileIsUploadedByListing(false);
    }

    @Then("User can not delete file that doesn't exist")
    public void user_can_not_delete_file_that_doesn_t_exist() {
        service.deleteFile("false");
    }

}

