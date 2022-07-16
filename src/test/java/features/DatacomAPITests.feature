Feature: Datacom API Tests

  @functional
  Scenario: TC1: Verify GET Users request
    Given I populate get users request
    When I send get users request
    Then I should see response with status code as 200
    And I should see 10 users in response and each user should have "id" parameter

  @functional
  Scenario: TC2: Verify GET User request by Id
    Given I populate get users request
    When I send get user request by Id 8
    Then I should see response with status code as 200
    And I should see "name" of the user is returned as "Nicholas Runolfsdottir V"

  @functional
  Scenario: TC3: Verify POST Users request
    Given I populate create user request
    When I send create user request with following details
      | parameter           | value             |
      | name                | umanga            |
      | username            | umanga            |
      | email               | test@umanga.com   |
      | address_street      | 123               |
      | address_suite       | 01                |
      | address_city        | test_city         |
      | address_zipcode     | 111111            |
      | geo_lat             | -14.3990          |
      | geo_lng             | -120.7677         |
      | phone               | 586.493.6943 x140 |
      | website             | www.test.com      |
      | company_name        | test_company      |
      | company_catchPhrase | test              |
      | company_bs          | test              |
    Then I should see response with status code as 201
    And I should see created user is returned in response with correct data
      | parameter           | value             |
      | name                | umanga            |
      | username            | umanga            |
      | email               | test@umanga.com   |
      | address_street      | 123               |
      | address_suite       | 01                |
      | address_city        | test_city         |
      | address_zipcode     | 111111            |
      | geo_lat             | -14.3990          |
      | geo_lng             | -120.7677         |
      | phone               | 586.493.6943 x140 |
      | website             | www.test.com      |
      | company_name        | test_company      |
      | company_catchPhrase | test              |
      | company_bs          | test              |