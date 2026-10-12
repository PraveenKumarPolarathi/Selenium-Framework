@tc001 @regression
Feature: TC_001 — User Registration
  As a new visitor
  I want to register an account on the Demo Web Shop
  So that I can make purchases

  Background:
    Given the user is on the demo web shop home page
    When the user clicks the Register button

  Scenario: Register a new user account
    And the user fills in the registration form with valid details
    Then the registration success message should be displayed
    When the user clicks Continue after registration
    Then the user should be redirected to the homepage

@tc004 @regression
 Scenario Outline: Register a new user account for a custom  names
    And the user fills in the registration form with custom "<firstname>" & "<lastname>"
    Then the registration success message should be displayed
    When the user clicks Continue after registration
    Then the user should be redirected to the homepage

   Examples:
     | firstname | lastname   |
     | QATest    | Automation |
     | Demo      | User       |

