Feature: Order Management functional tests

  @functional @e2e
  Scenario: Register a new customer with valid name and account balance
    #account balance does not tally after deducting the amount of the order placed
    Given I populate add customer request
    When I send add customer request with valid name and account balance
      | name                 | account_balance |
      | testUser_<timestamp> | 10000           |
    Then I should see response with status code as 200
    And I should see customer uuid in response body
    And I can see customer account is saved in database with account balance "10000.00"
    And I send new order request with valid isin and quantity
      | isin                 | quantity |
      | DE000122_<timestamp> | 25       |
    And I should see response with status code as 200
    And I should see "Order was successfully published" in response body
    And I can see kafka consumer receives a message for the created order with isin "DE000122_<timestamp>" , quantity 25 and status "COMPLETED"
    And I can see kafka consumer receives a message for the created order with isin "DE000122_<timestamp>" , quantity 25 and action "BUY"
    And I can see order data is saved in database
      | isin                 | quantity | status    |
      | DE000122_<timestamp> | 25       | COMPLETED |
    And I can see account balance of customer account updated correctly in database

  Scenario: Place an order greater than account balance
    #Assuming it is supported in the system
    Given I populate add customer request
    When I send add customer request with valid name and account balance
      | name                 | account_balance |
      | testUser_<timestamp> | 1               |
    Then I should see response with status code as 200
    And I should see customer uuid in response body
    And I can see customer account is saved in database with account balance "1.00"
    And I send new order request with valid isin and quantity
      | isin                 | quantity |
      | DE000122_<timestamp> | 25       |
    And I should see response with status code as 200
    And I should see "Order was successfully published" in response body
    And I can see kafka consumer receives a message for the created order with isin "DE000122_<timestamp>" , quantity 25 and status "COMPLETED"
    And I can see kafka consumer receives a message for the created order with isin "DE000122_<timestamp>" , quantity 25 and action "BUY"
    And I can see order data is saved in database
      | isin                 | quantity | status    |
      | DE000122_<timestamp> | 25       | COMPLETED |
    And I can see account balance of customer account updated correctly in database

  @Todo
  Scenario:Place an order with invalid (string) quantity

  @Todo
  Scenario:Place an order with invalid (empty) quantity

  @Todo
  Scenario:Place an order with invalid (negative) quantity
    #Assuming negative quantity is considered as sell. But it is marked as BUY in order.request topic messages

  @Todo
  Scenario:Place an order with invalid (non-existing) customer

  @Todo
  Scenario:Place an order with invalid (empty id) customer