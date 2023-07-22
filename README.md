# When SonarQube Detects Code Smells, Send Slack DM to code author

It is an application that sends notifications to Slack when a code smell occurs in SonarQube.

## Requirements

* This project requires Java 8 or later.
* spring boot version 3.x
* slack-api-client 1.29.2

# Usage

* You can configure the sonarqube api url and Slack details in the application.yml file.
   ```
    monitoring:
      sonarqube:
        api.url: http://127.0.0.1:9100/api
      slack:
        token: xoxb-xxxxxxxxxxxxxxxxxxxxxxxxxxxx
        channel-name: developer
    ```
* run application
    * build this project and run SonarqubeMonitoringApplication.class
    * [OR] java -jar sonarqube_monitoring*.jar

* execute api
    ```
      curl -X POST 'http://localhost:8080/sonarqube/codesmell/alert'
    ```

## output snapshot

### Reference Documentation

For further reference, please consider the following sections:
