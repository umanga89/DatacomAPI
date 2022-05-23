Feature: Customer Management functional tests

  @functional @e2e
  Scenario: Register a new customer with valid name and account balance
    Given I populate add customer request
    When I send add customer request with valid name and account balance
      | name                 | account_balance |
      | testUser_<timestamp> | 10000           |
    Then I should see response with status code as 200
    And I should see customer uuid in response body
    And I can see customer saved in database
    And I can see customer account is saved in database with account balance "10000.00"
    And I can see kafka consumer receives a message for the created customer account with customer uuid and account balance "10000"

  @functional @e2e
  Scenario: Register a new customer with valid name and account balance with decimal value
    Given I populate add customer request
    When I send add customer request with valid name and account balance
      | name                 | account_balance |
      | testUser_<timestamp> | 10000.11        |
    Then I should see response with status code as 200
    And I should see customer uuid in response body
    And I can see customer saved in database
    And I can see customer account is saved in database with account balance "10000.11"
    And I can see kafka consumer receives a message for the created customer account with customer uuid and account balance "10000.11"

  @functional @e2e
    #assuming it is allowed
  Scenario: Register a new customer with valid name and negative account balance
    Given I populate add customer request
    When I send add customer request with valid name and account balance
      | name                 | account_balance |
      | testUser_<timestamp> | -10000.11       |
    Then I should see response with status code as 200
    And I should see customer uuid in response body
    And I can see customer saved in database
    And I can see customer account is saved in database with account balance "-10000.11"
    And I can see kafka consumer receives a message for the created customer account with customer uuid and account balance "-10000.11"

#  @functional @e2e
#    #This should ideally fail. But customer is created with empty name
#  Scenario: Register a new customer with invalid (empty) name and valid account balance
#    Given I populate add customer request
#    When I send add customer request with valid name and account balance
#      | name  | account_balance |
#      | empty | 10000.00        |
#    Then I should see response with status code as 400

  @functional @e2e
  Scenario: Register a new customer with valid name and invalid (string) account balance
    Given I populate add customer request
    When I send add customer request with valid name and account balance
      | name                | account_balance |
      | testUser_<timestamp | string          |
    Then I should see response with status code as 400
    And I should see following values in response body
      | FIELD     | VALUE            |
      | timestamp | <timestampvalue> |
      | status    | 400              |
      | error     | Bad Request      |
      | message   | ""               |
      | path      | /addCustomer     |