Feature: Slack API file object test

  Scenario: Upload content to slack channel via file.upload API
    Given User has access token to use file.upload API
    When User uploads file using following parameters
      |file_name |file |filetype    |initial_comment   |title | channels |
      |test_img.png       |./test_img.png     |png |Best_comment|test_title    | general |
    Then User can see successful response
    Then User can see all expected thumbnails urls in response
    Then User can see the uploaded file in file.list
    Then User can delete uploaded file using file.delete
    And Now User can not see file in file.list
    Then User can not delete file that doesn't exist

