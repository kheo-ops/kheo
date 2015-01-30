Feature: In order to use Kheo API, a healthcheck should tell me the application status
    Healthcheck shows the status of all application dependencies   

    Scenario: When the application and its dependencies are up, healtcheck is OK
        Given I am on the healthcheck page
        Then status code is 200
