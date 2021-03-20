Feature: Delete Pupils request functional tests

  Background: Add pupil and save pupil id
    Given I populate add pupil request
    When I send add pupil request with mandatory values
      | firstName | lastName | gradeId |
      | Umanga    | Madawala | 1       |
    Then I should see response with status code as 201
    And I save "pupilId" from response

  @functional @all @delete
  Scenario: User should be able to delete a pupil with valid pupilId
    When I send delete pupil request with "valid" pupilId
    Then I should see response with status code as 204
    And I should see "pupil deleted" description in response

  @functional  @all
  Scenario: User should not be able to delete a pupil with non-existent pupilId
    When I send delete pupil request with "non-existent" pupilId
    Then I should see response with status code as 404
    And I should see "Not found" description in response

  @functional @all
  Scenario: User should not be able to delete a pupil with non-existent pupilId
    When I send delete pupil request with "empty" pupilId
    Then I should see response with status code as 405
    And I should see following values in response body
      | FIELD   | VALUE                     |
      | status  | 405                       |
      | message | DELETE method not allowed |

  @functional @all
  Scenario: User should not be able to delete a pupil if basic authentication header is not provided
    When I send delete pupil request without basic authentication header
    Then I should see response with status code as 401
    And I should see following values in response body
      | FIELD   | VALUE                         |
      | status  | 401                           |
      | message | Authorization header required |