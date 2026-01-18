package unicamp.br.inf321;

import com.github.javafaker.Faker;
import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import io.cucumber.java.en.*;
import org.json.JSONArray;
import org.junit.jupiter.api.Assertions;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class NewProductReviewStepDefinitions {

    private final Faker faker = new Faker();

    private String productId;
    private String token;
    private List<String> reviewIds;
    private String textInput;
    private String scoreInput;
    private String username;

    public NewProductReviewStepDefinitions() {
    }

    private Page getPage() {
        return CucumberWorld.page;
    }

    @When("I handle existing product review if present")
    public void handleExistingProductReviewIfPresent() throws Exception {
        if (getPage().locator("text=You have evaluated this product").isVisible()) {
            String url = getPage().url();
            URI uri = URI.create(url);

            String querys = uri.getQuery();
            String[] segments = querys.split("=");
            productId = segments[segments.length - 1];

            token = authenticateUser("oisidoro@unicamp.br", "aA#123456789");

            reviewIds = getReviewIds(productId, token);

            for (String reviewId : reviewIds) {
                deleteReview(productId, reviewId, token);
            }

            getPage().reload();
        }
    }

    @When("I insert a random text of {int} characters in the description field")
    public void insertRandomTextInDescription(int maxLength) {
        textInput = faker.lorem().characters(maxLength, true);
        getPage().fill("#description", textInput);
    }

    @When("I insert a random score between {int} and {int} in the score input")
    public void insertRandomScore(int min, int max) {
        scoreInput = String.valueOf(faker.number().randomDouble(1, min, max));
        Locator input = getPage().locator("#rating");
        input.evaluate("elem => elem.value = '" + scoreInput + "'");
    }

    @When("I click on the submit button")
    public void clickSubmitButton() {
        getPage().click("button:has-text('Submit')");
    }

    @Then("I should see the text {string}")
    public void shouldSeeText(String expectedText) {
        PlaywrightAssertions.assertThat(getPage().locator("text=" + expectedText)).isVisible();
    }

    @Then("the input score should be the same as score input")
    public void verifyInputScore() {
        PlaywrightAssertions.assertThat(getPage().locator("#customerRating > input[type=hidden]")).hasValue(scoreInput);
    }

    @Then("I should see the review text I entered")
    public void verifyReviewText() {
        PlaywrightAssertions.assertThat(getPage().locator("text=" + textInput)).isVisible();
    }

    @When("I enter email {string}")
    public void i_enter_email(String email) {
        getPage().fill("#signin_userName", email);
    }

    @When("I enter password {string}")
    public void i_enter_password(String password) {
        getPage().fill("#signin_password", password);
    }

    @When("I click on the sign in button")
    public void i_click_on_the_sign_in_button() {
        getPage().click("button:has-text('Sign in')");
    }

    @Then("I should see the welcome message {string}")
    public void i_should_see_the_welcome_message(String username) {
        PlaywrightAssertions.assertThat(getPage().locator("text=Welcome " + username.replace("Welcome ", ""))).isVisible();
    }

    @Then("the current URL should be {string}")
    public void the_current_url_should_be(String expectedUrl) {
        Assertions.assertEquals(expectedUrl, getPage().url());
    }

    @When("I click on {string} menu entry")
    public void i_click_on_menu_entry(String menu) {
        getPage().click("text=" + menu);
    }

    @When("I click on {string} product link")
    public void i_click_on_product_link(String product) {
        getPage().click("text=" + product);
    }

    @When("I click on the {string} tab")
    public void i_click_on_the_tab(String tab) {
        getPage().click("text=" + tab);
    }

    // Helper methods for API calls

    private String authenticateUser(String email, String password) throws Exception {
        this.username = email;
        
        HttpClient client = HttpClient.newHttpClient();
        String json = String.format("{\"username\":\"%s\",\"password\":\"%s\"}", email, password);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://multibags.1dt.com.br/api/v1/customer/login"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String body = response.body();
        org.json.JSONObject jsonObject = new org.json.JSONObject(body);
        if (!jsonObject.has("token")) {
            System.out.println("Login response body: " + body);
            throw new RuntimeException("Token not found in login response");
        }
        return jsonObject.getString("token");
    }

    private List<String> getReviewIds(String productId, String token) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://multibags.1dt.com.br/api/v1/products/" + productId + "/reviews"))
                .header("Authorization", "Bearer " + token)
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String body = response.body();

        return getReviewIds(body);
    }

    private List<String> getReviewIds(String body) {
        List<String> reviewIds = new ArrayList<>();
        JSONArray json = new JSONArray(body);
        for ( Object item : json) {
            JSONObject json_item = (JSONObject) item;
            JSONObject json_customer = (JSONObject) json_item.get("customer");
            String username = json_customer.getString("emailAddress");
            if (Objects.equals(username, this.username)) {
                String reviewId = String.valueOf(json_item.getInt("id"));
                reviewIds.add(reviewId);
            }
        }
        return reviewIds;
    }

    private void deleteReview(String productId, String reviewId, String token) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://multibags.1dt.com.br/api/v1/auth/products/" + productId + "/reviews/" + reviewId))
                .header("Authorization", "Bearer " + token)
                .DELETE()
                .build();
        client.send(request, HttpResponse.BodyHandlers.discarding());
    }
}
