package unicamp.br.inf321;

import com.github.javafaker.Faker;
import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import java.util.regex.Pattern;

public class RegisterUserStepDefinitions {

    private final Faker faker = new Faker();

    public RegisterUserStepDefinitions() {
    }

    private Page getPage() {
        return CucumberWorld.page;
    }

    @Given("I navigate to {string}")
    public void i_navigate_to(String url) {
        getPage().navigate(url);
    }

    @When("I insert a random string with maximum length of {int} characters in the state field")
    public void i_insert_a_random_string_with_maximum_length_of_characters_in_the_state_field(int maxLength) {
        String randomState = faker.lorem().characters(maxLength, true);
        getPage().fill("#hidden_zones", randomState);
    }

    @When("I click on {string} in the top header menu")
    public void i_click_on_in_the_top_header_menu(String linkText) {
        getPage().click("text=" + linkText);
    }

    @When("I click on {string}")
    public void i_click_on(String linkText) {
        getPage().click("text=" + linkText);
    }

    @When("I insert a random string with maximum length of {int} characters in the first name field")
    public void i_insert_random_first_name(int maxLength) {
        String randomFirstName = faker.lorem().characters(maxLength, true);
        getPage().fill("#firstName", randomFirstName);
    }

    @When("I insert a random string with maximum length of {int} characters in the last name field")
    public void i_insert_random_last_name(int maxLength) {
        String randomLastName = faker.lorem().characters(maxLength, true);
        getPage().fill("#lastName", randomLastName);
    }

    @When("I select a random country in the dropdown field")
    public void i_select_random_country() {
        var options = getPage().querySelectorAll("#registration_country option");
        int optionsCount = options.size();
        if (optionsCount == 0) {
            throw new RuntimeException("No options found in country dropdown");
        }
        int randomIndex = faker.number().numberBetween(1, optionsCount - 1);
        String value = options.get(randomIndex).getAttribute("value");
        getPage().selectOption("#registration_country", value);
    }

    @When("I insert a random email address in the Email address field")
    public void i_insert_random_email() {
        String pattern = "<random_string><random_int>@g.unicamp.br";
        String randomString = faker.lorem().characters(5, true);
        int randomInt = faker.number().numberBetween(1, 9999);
        String email = pattern.replace("<random_string>", randomString).replace("<random_int>", String.valueOf(randomInt));
        getPage().fill("#emailAddress", email);
    }

    @When("I insert {string} in the password field")
    public void i_insert_password(String password) {
        getPage().fill("#password", password);
    }

    @When("I insert {string} in the repeat password field")
    public void i_insert_repeat_password(String password) {
        getPage().fill("#passwordAgain", password);
    }

    @When("I click on the {string} button")
    public void i_click_on_button(String buttonText) {
        getPage().click("button:has-text('" + buttonText + "')");
    }

    @Then("the current URL should contain {string}")
    public void the_current_url_should_contain(String expectedUrl) {
        Pattern pattern = Pattern.compile(expectedUrl, Pattern.CASE_INSENSITIVE);
        PlaywrightAssertions.assertThat(getPage()).hasURL(pattern);
    }
}
