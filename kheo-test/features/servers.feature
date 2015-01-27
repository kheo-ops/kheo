Feature: Manage servers
    
    Scenario: As a user, I can add a server
        Given A server "localhost" with access enabled to "root" with "password" on port "22222"
        When I add a "localhost" server with access enabled to "root" with "password" on port "22222"
        Then I can retrieve the server "localhost"
        And SSH connectivity is "true"

    Scenario: As a user, I can remove an existing server
        Given An existing server "server_to_remove"
        When I remove "server_to_remove"
        And I retrieve the server "server_to_remove"
        Then "server_to_remove" is not found

    Scenario: As a user, I cannot remove a non existing server
        Given A non existing server "server_to_remove"
        When I remove "server_to_remove"
        Then "server_to_remove" is not found
