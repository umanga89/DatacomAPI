Feature: Get Pupil request functional tests

  Background: Add pupil and save pupil id
    Given I populate add pupil request
    When I send add pupil request with mandatory values
      | firstName | lastName | gradeId |
      | Umanga    | Madawala | 1       |
    Then I should see response with status code as 201
    And I save "pupilId" from response

  @functional @add @all
  Scenario: User should be able to get a pupil with valid pupilId
    When I send get pupil request with "valid" pupilId
    Then I should see response with status code as 200
    And I should see following values in response body
      | FIELD     | VALUE    |
      | firstName | Umanga   |
      | lastName  | Madawala |
      | gradeId   | 1        |
    And I should see "pupilId" field in response

  @functional @add @all
  Scenario: User should not be able to get a pupil with non-existent pupilId
    When I send get pupil request with "non-existent" pupilId
    Then I should see response with status code as 404
    And I should see following values in response body
      | FIELD   | VALUE                              |
      | status  | 404                                |
    And I should see "message" field with value "Pupil with ID: \"pupilId\" not found"

  @functional @add @all
  Scenario: User should be able to get all pupils if empty pupilId is sent
    When I send get pupil request with "empty" pupilId
    Then I should see response with status code as 200
    And I should see all records have "pupilId" field