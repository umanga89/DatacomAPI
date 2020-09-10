Feature: As a user, I want to test the negative scenarios of Book request

  Background: Reset bookings before scenario is executed
    Given I populate data for reset request
    When I send reset request
    Then I should see response with status code as 200
    And I should see empty response body

  @negative @all
  Scenario: Verify proper error message is displayed when source X value is larger than expected value
    Given I populate data for Book request
    When I send Book request with a given values
      | SourceX    | SourceY | DestinationX | DestinationY |
      | 2147483648 | 0       | 1            | 1            |
    Then I should see response with status code as 400
    #ToDo - validation for error message

  @negative @all
  Scenario: Verify proper error message is displayed when source Y value is larger than expected value
    Given I populate data for Book request
    When I send Book request with a given values
      | SourceX | SourceY    | DestinationX | DestinationY |
      | 0       | 2147483648 | 1            | 1            |
    Then I should see response with status code as 400
    #ToDo - validation for error message

  @negative @all
  Scenario: Verify proper error message is displayed when destination X value is larger than expected value
    Given I populate data for Book request
    When I send Book request with a given values
      | SourceX | SourceY | DestinationX | DestinationY |
      | 0       | 1       | 2147483648   | 1            |
    Then I should see response with status code as 400
    #ToDo - validation for error message

  @negative @all
  Scenario: Verify proper error message is displayed when destination Y value is larger than expected value
    Given I populate data for Book request
    When I send Book request with a given values
      | SourceX | SourceY | DestinationX | DestinationY |
      | 0       | 1       | 1            | 2147483648   |
    Then I should see response with status code as 400
    #ToDo - validation for error message

  @negative @all
  Scenario: Verify proper error message is displayed when source X value is less than expected value
    Given I populate data for Book request
    When I send Book request with a given values
      | SourceX     | SourceY | DestinationX | DestinationY |
      | -2147483649 | 0       | 1            | 1            |
    Then I should see response with status code as 400
    #ToDo - validation for error message

  @negative @all
  Scenario: Verify proper error message is displayed when source Y value is less than expected value
    Given I populate data for Book request
    When I send Book request with a given values
      | SourceX | SourceY     | DestinationX | DestinationY |
      | 0       | -2147483649 | 1            | 1            |
    Then I should see response with status code as 400
    #ToDo - validation for error message

  @negative @all
  Scenario: Verify proper error message is displayed when destination X value is less than expected value
    Given I populate data for Book request
    When I send Book request with a given values
      | SourceX | SourceY | DestinationX | DestinationY |
      | 0       | 1       | -2147483649  | 1            |
    Then I should see response with status code as 400
    #ToDo - validation for error message

  @negative @all
  Scenario: Verify proper error message is displayed when destination Y value is less than expected value
    Given I populate data for Book request
    When I send Book request with a given values
      | SourceX | SourceY | DestinationX | DestinationY |
      | 0       | 1       | 1            | -2147483649  |
    Then I should see response with status code as 400
    #ToDo - validation for error message

  @negative @all
  Scenario: Verify proper error message is displayed when source X parameter is not available in request
    Given I populate data for Book request
    When I send Book request without source X parameter
      | SourceY | DestinationX | DestinationY |
      | 1       | 1            | 2            |
    Then I should see response with status code as 400
    #ToDo - validation for error message

  @negative @all
  Scenario: Verify proper error message is displayed when source Y parameter is not available in request
    Given I populate data for Book request
    When I send Book request without source Y parameter
      | SourceX | DestinationX | DestinationY |
      | 1       | 1            | 2            |
    Then I should see response with status code as 400
    #ToDo - validation for error message

  @negative @all
  Scenario: Verify proper error message is displayed when destination X parameter is not available in request
    Given I populate data for Book request
    When I send Book request without destination X parameter
      | SourceX | SourceY | DestinationY |
      | 1       | 1       | 2            |
    Then I should see response with status code as 400
    #ToDo - validation for error message

  @negative @all
  Scenario: Verify proper error message is displayed when destination Y parameter is not available in request
    Given I populate data for Book request
    When I send Book request without destination Y parameter
      | SourceX | SourceY | DestinationX |
      | 1       | 1       | 2            |
    Then I should see response with status code as 400
    #ToDo - validation for error message

  @negative @all
  Scenario: Verify proper error message is displayed when source tag is not available in request
    Given I populate data for Book request
    When I send Book request without source tag in request
      | DestinationX | DestinationY |
      | 1       | 1       |
    Then I should see response with status code as 400
    #ToDo - validation for error message

  @negative @all
  Scenario: Verify proper error message is displayed when destination tag is not available in request
    Given I populate data for Book request
    When I send Book request without destination tag in request
      | SourceX | SourceY |
      | 1       | 1       |
    Then I should see response with status code as 400
    #ToDo - validation for error message

