Feature: Get Pupils request functional tests

  @functional @all
  Scenario: User should be able to get all pupils created
    When I send Get Pupils request
    Then I should see response with status code as 200
    And I should see all records have "pupilId" field