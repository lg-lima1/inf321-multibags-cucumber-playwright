Feature: New Product Review Test

  Scenario: Register a new product review successfully
    Given I navigate to "http://multibags.1dt.com.br/"
    When I click on "My Account" in the top header menu
    And I click on "Sign in"
    And I enter email "oisidoro@unicamp.br"
    And I enter password "aA#123456789"
    And I click on the sign in button
    Then I should see the welcome message "Welcome Toni"
    And the current URL should be "http://multibags.1dt.com.br/shop/customer/dashboard.html"
    And the main menu should contain:
      | My Account                  |
      | Billing & shipping information |
      | Change password             |
      | Logout                      |
    And I navigate to "http://multibags.1dt.com.br/shop/product/vintage-courier-bag.html"
    And I click on the "Customer Review" tab
    And I click on the "Write a Review" button
    And I handle existing product review if present
    And I insert a random text of 140 characters in the description field
    And I insert a random score between 0 and 5 in the score input
    And I click on the submit button
    Then I should see the text "You have successfully created a product review"
    And I should see the text "You have evaluated this product"
    And the input score should be the same as score input
    And I should see the review text I entered
