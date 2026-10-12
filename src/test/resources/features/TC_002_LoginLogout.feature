@tc002 @regression @smoke
Feature: TC_002 — Login and Logout
  As a registered user
  I want to log in and log out of the Demo Web Shop
  So that my session is managed correctly

  Scenario: Login and Logout with valid credentials
    Given the user is on the demo web shop home page
    When the user clicks the Login link on the Landing Page
    And the user logs in with valid credentials
    Then the Logout button should be visible in the navbar
    When the user clicks the Logout button
    Then the Login link should be visible in the navbar
