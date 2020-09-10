Feature: As a user, I want to test the functionality of Reset request

  Background: Reset bookings before scenario is executed
    Given I populate data for reset request
    When I send reset request
    Then I should see response with status code as 200
    And I should see empty response body

  @functional @all
    Scenario: When reset request is sent, all taxis are sent back to original location cancelling current bookings
      Given I populate data for Book request
      When I send Book request for 1st car with following details
        | SourceX | SourceY | DestinationX | DestinationY |
        | 0       | 0       | 1            | 1            |
      Then I should see response with status code as 200
      And I should see response body as following details
        | car_id | state             | total_time |
        | 1      | FETCHING_CUSTOMER | 2          |

      When I send Book request for 2nd car with following details
        | SourceX | SourceY | DestinationX | DestinationY |
        | 1       | 1       | 2            | 2            |
      Then I should see response with status code as 200
      And I should see response body as following details
        | car_id | state             | total_time |
        | 2      | FETCHING_CUSTOMER | 4          |

      When I send Book request for 3rd car with following details
        | SourceX | SourceY | DestinationX | DestinationY |
        | 0       | 1       | 6            | 8            |
      Then I should see response with status code as 200
      And I should see response body as following details
        | car_id | state             | total_time |
        | 3      | FETCHING_CUSTOMER | 14         |

      Given I populate data for reset request
      When I send reset request
      Then I should see response with status code as 200
      And I should see empty response body

      Given I populate data for Book request
      When I send Book request with following details
        | SourceX | SourceY | DestinationX | DestinationY |
        | 0       | 0       | 1            | 1            |
      Then I should see response with status code as 200
      And I should see response body as following details
        | car_id | state             | total_time |
        | 1      | FETCHING_CUSTOMER | 2          |