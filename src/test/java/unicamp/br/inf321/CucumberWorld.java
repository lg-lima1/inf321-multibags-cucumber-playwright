package unicamp.br.inf321;

import com.microsoft.playwright.*;

import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;

public class CucumberWorld {
    public static Playwright playwright;
    public static Browser browser;
    public static BrowserContext context;
    public static Page page;

    @BeforeAll
    public static void before_all() {
        CucumberWorld.playwright = Playwright.create();
        CucumberWorld.browser = CucumberWorld.playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false)
        );
        CucumberWorld.context = CucumberWorld.browser.newContext();
    }

    @Before
    public void setup_scenario() {
        CucumberWorld.context = CucumberWorld.browser.newContext();
        CucumberWorld.page = CucumberWorld.context.newPage();
    }

    @After
    public void teardown_scenario() {
        if (CucumberWorld.page != null) {
            CucumberWorld.page.close();
        }
    }

    @AfterAll
    public static void after_all() {
        if (CucumberWorld.browser != null) CucumberWorld.browser.close();
        if (CucumberWorld.playwright != null) CucumberWorld.playwright.close();
    }
}
