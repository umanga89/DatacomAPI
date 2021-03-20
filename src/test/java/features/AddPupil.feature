Feature: Add Pupil request functional tests

  @functional @all
  Scenario: User should be able to add pupil with only required parameters
    Given I populate add pupil request
    When I send add pupil request with mandatory values
      | firstName | lastName | gradeId |
      | Umanga    | Madawala | 1       |
    Then I should see response with status code as 201
    And I should see following values in response body
      | FIELD     | VALUE    |
      | firstName | Umanga   |
      | lastName  | Madawala |
      | gradeId   | 1        |
    And I should see "pupilId" field in response

  @functional @all
  Scenario: User should be able to add pupil with all supported parameters
    Given I populate add pupil request
    When I send add pupil request with all values
      | firstName | lastName | gradeId | infix     | classId | email         | isDisabled |
      | Umanga    | Madawala | 1       | NewMCUser | 1       | test@mail.com | false      |
    Then I should see response with status code as 201
    And I should see following values in response body
      | FIELD      | VALUE         |
      | firstName  | Umanga        |
      | lastName   | Madawala      |
      | gradeId    | 1             |
      | infix      | NewMCUser     |
      | classId    | 1             |
      | email      | test@mail.com |
      | isDisabled | false         |
    And I should see "pupilId" field in response

  @functional @all
  Scenario: User should not be able to add pupil if basic authentication header is not provided
    Given I populate add pupil request
    When I send add pupil request with mandatory values without basic authentication header
      | firstName | lastName | gradeId |
      | Umanga    | Madawala | 1       |
    Then I should see response with status code as 401
    And I should see following values in response body
      | FIELD   | VALUE                         |
      | status  | 401                           |
      | message | Authorization header required |

  @functional @positive @all
  Scenario: User should be able to add pupil with first name having max length of ---

  @functional @positive @all
  Scenario: User should be able to add pupil with first name having min length of ---

  @functional @negative @all
  Scenario: User should not be able to add pupil if first name is not given

  @functional @negative @all
  Scenario: User should not be able to add pupil if last name is not given

  @functional @negative @all
  Scenario: User should not be able to add pupil if grade Id is not given

  @functional @negative @all
  Scenario: User should not be able to add pupil if username is not valid

  @functional @negative @all
  Scenario: User should not be able to add pupil if password is not valid