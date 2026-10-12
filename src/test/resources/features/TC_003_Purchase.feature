@tc003 @regression
Feature: TC_003 — Purchase a Product (End-to-End)
  As a user
  I want to Register and browse to a product and complete a purchase
  So that an order is placed successfully

  Scenario: Login and purchase a 14.1-inch Laptop
    # ── Register ─────────────────────────────────────────────
    Given the user is on the demo web shop home page
    When the user clicks the Register button
    And the user fills in the registration form with valid details
    Then the registration success message should be displayed
    When the user clicks Continue after registration
    Then the user should be redirected to the homepage
    Then the Logout button should be visible in the navbar

    # ── Product Selection ──────────────────────────────────
    And the user selects add to cart button on the 14.1-inch Laptop
    And the user selects shopping cart button on the top of the page

    # ── Cart ──────────────────────────────────────────────
    Then the cart total amount should be displayed
    When the user agrees to Terms and Conditions and clicks Checkout

    # ── Checkout — Billing ────────────────────────────────
    And the user fills in the billing address and clicks Continue

    # ── Checkout — Shipping ───────────────────────────────
    And the user selects In-Store Pickup and clicks Continue

    # ── Checkout — Payment ────────────────────────────────
    And the user selects Cash On Delivery and clicks Continue
    Then the Pay by COD selection should be confirmed
    When the user clicks Continue on payment information

    # ── Checkout — Confirm Order ──────────────────────────
    Then the ordered product should be visible in the confirmation
    When the user confirms the order

    # ── Order Complete ────────────────────────────────────
    Then the order number and success message should be displayed
