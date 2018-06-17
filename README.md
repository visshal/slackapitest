
##Objective
Automate set of tests for Slack's "file" object API for following methods.
 1. upload
 2. list
 3. delete

##Requirements:
*Build Tools:* Gradle 4.1
*Language:* Java 1.8
*Frameworks:* Cucumber, RestAssured

##Development Platform
macOS Siera 10.12.6

##How to run
 ```
 ./gradlew test
 ```
 
 ###Sample output snippet
 ```
 slacktest.cucumber.CucumberRunner STANDARD_OUT

    1 Scenarios (1 passed)
    8 Steps (8 passed)
    0m38.325s


BUILD SUCCESSFUL
```
 
 ##Note:
 Right before listing of a file there has been 30 seconds of sleep as uploaded file doesn't show up immediately and some times it takes as long as 15 seconds. 
