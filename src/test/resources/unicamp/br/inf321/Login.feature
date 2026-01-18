Feature: User Login

  Scenario: Successful login with valid credentials
    Given the user is on the homepage
    When the user navigates to the sign in page
    And the user enters email "oisidoro@unicamp.br"
    And the user enters password "aA#123456789"
    And the user clicks the sign in button
    Then the user should see the welcome message "Welcome Toni"
    And the current URL should contain "http://multibags.1dt.com.br/shop/customer/dashboard.html"
    And the main menu should contain:
      | My Account                 |
      | Billing & shipping information |
      | Change password            |
      | Logout                    |
