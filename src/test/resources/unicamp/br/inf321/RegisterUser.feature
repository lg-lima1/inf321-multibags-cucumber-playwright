Feature: Register User Account

  Scenario: Register Account Test
    Given I navigate to "http://multibags.1dt.com.br/"
    When I click on "My Account" in the top header menu
    And I click on "Register"
    And I insert a random string with maximum length of 12 characters in the first name field
    And I insert a random string with maximum length of 12 characters in the last name field
    And I select a random country in the dropdown field
    And I insert a random string with maximum length of 12 characters in the state field
    And I insert a random email address in the Email address field
    And I insert "abcd1234" in the password field
    And I insert "abcd1234" in the repeat password field
    And I click on the "CREATE AN ACCOUNT" button
    Then the current URL should contain "http://multibags.1dt.com.br/shop/customer/dashboard.html"
    And I click on "Welcome " in the top header menu
    And the main menu should contain:
      | My Account                |
      | Logout                    |
