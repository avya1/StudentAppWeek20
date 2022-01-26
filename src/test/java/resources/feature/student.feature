Feature: Testing different request on the student application

  @SMOKE
  Scenario: Check if the student application can be accessed by users
    When User sends a GET request to list endpoint
    Then User must get back a valid status code 200

  Scenario: Create a new student & verify if the student is added
    When User can create new student with all the details
    Then User must verify created student is added

  Scenario: Check user can update student information and verify it
    When User update student detail by changing name
    Then User must able to see updated student detail in record

  Scenario:Check user can delete student record and verify record is deleted
    When User delete any student by id
    Then User search the same id to verify student is deleted






