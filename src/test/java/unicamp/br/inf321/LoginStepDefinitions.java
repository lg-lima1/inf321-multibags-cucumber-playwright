package unicamp.br.inf321;

import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginStepDefinitions {
    public LoginStepDefinitions() {
    }

    private Page getPage() {
        return CucumberWorld.page;
    }

    @Given("the user is on the homepage")
    public void the_user_is_on_the_homepage() {
        getPage().navigate("http://multibags.1dt.com.br/");
    }

    @When("the user navigates to the sign in page")
    public void the_user_navigates_to_the_sign_in_page() {
        getPage().click("text=My Account");
        getPage().click("text=Sign in");
    }

    @When("the user enters email {string}")
    public void the_user_enters_email(String email) {
        getPage().fill("#signin_userName", email);
    }

    @When("the user enters password {string}")
    public void the_user_enters_password(String password) {
        getPage().fill("#signin_password", password);
    }

    @When("the user clicks the sign in button")
    public void the_user_clicks_the_sign_in_button() {
        getPage().click("button:has-text('Sign in')");
    }

    @Then("the user should see the welcome message {string}")
    public void the_user_should_see_the_welcome_message(String username) {
        PlaywrightAssertions.assertThat(getPage().locator("text=Welcome " + username.replace("Welcome ", ""))).isVisible();
    }

    @Then("the main menu should contain:")
    public void the_main_menu_should_contain(io.cucumber.datatable.DataTable dataTable) {
        for (String menu : dataTable.asList()) {
            PlaywrightAssertions.assertThat(getPage().locator("text=\"" + menu + "\"").filter(new Locator.FilterOptions().setHasText(menu).setVisible(true)).first()).isVisible();
        }
    }
}
