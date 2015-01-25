Feature: Manage servers
    
    Scenario: As a user, I can add a server
        Given A server "localhost" with access enabled to "mika" with "password"
        When I add a "localhost" server with access enabled to "mika" with "password"
        Then I can retrieve the server "localhost"
        And SSH connectivity is "true"

