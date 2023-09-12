# When SonarQube Detects Code Smells (include Hotspots), Send Slack DM to code author

It is an application that sends notifications to Slack when a code smell or hotspot occurs in SonarQube.

## Requirements

* This project requires Java 8 or later.
* spring boot version 3.x
* slack-api-client 1.29.2

# Usage

* You can configure the sonarqube api url and Slack details in the application.yml file.
* You only need to set either the Channel ID or the Channel Name.
   ```
    monitoring:
      sonarqube:
        consoleUrl:
        apiUrl: http://127.0.0.1:9100/api
        componentKeys:
      slack:
        token: xoxb-xxxxxxxxxxxxxxxxxxxxxxxxxxxx
        channel:
          id:
          name:
        admin.id:
    ```
* When a matching user does not exist in the Slack channel, it can be configured to send a direct message to the admin.
  ```
    admin.id: "@admin"
  ```

  * run application
      * build this project and run SonarqubeMonitoringApplication.class
      * [OR] java -jar sonarqube_monitoring*.jar
      * execute api
        ```
          curl -X POST 'http://localhost:8080/sonarqube/quality/alert'
        ```
  * [OR] you can use spring schedule. you just set the job time.
    ```
       jobs:
         cronSchedule: "0 0 9 * * 1-5"
    ```

## output snapshot
![slack_notification](https://tnfhrnsss.github.io/docs/sub-projects/img/sonarqube_codesmell_slack_notification.png)

### blog reference

For further reference, please consider the following sections:

* [blog](https://tnfhrnsss.github.io/docs/sub-projects/sonarqube_codesmell_slack_notification/)

