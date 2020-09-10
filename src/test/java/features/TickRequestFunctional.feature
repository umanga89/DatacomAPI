Feature: As a user, I want to test the functionality of Reset request

  Background: Reset bookings before scenario is executed
    Given I populate data for reset request
    When I send reset request
    Then I should see response with status code as 200
    And I should see empty response body

    @functional @all
  Scenario: Verify 1 tick request represents 1 time unit
    Given I populate data for Book request
    When I send Book request with following details
      | SourceX | SourceY | DestinationX | DestinationY |
      | 0       | 0       | 1            | 1            |
    Then I should see response with status code as 200
    And I should see response body as following details
      | car_id | state             | total_time |
      | 1      | FETCHING_CUSTOMER | 2          |

    Given I send tick request 3 times

    Given I populate data for Book request
    When I send Book request with following details
      | SourceX | SourceY | DestinationX | DestinationY |
      | 1       | 1       | 1            | 2            |
    Then I should see response with status code as 200
    And I should see response body as following details
      | car_id | state             | total_time |
      | 1      | FETCHING_CUSTOMER | 1          |