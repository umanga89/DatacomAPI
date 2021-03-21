Feature: Get Pupils request functional tests

  @functional @all
  Scenario: User should be able to get all pupils created
    #Precondition
    Given I populate add pupil request
    When I send add pupil request with mandatory values
      | firstName | lastName | gradeId |
      | Umanga    | Madawala | 1       |
    Then I should see response with status code as 201

    #Testing get pupils request
    When I send Get Pupils request
    Then I should see response with status code as 200
    And I should see all records have "pupilId" field