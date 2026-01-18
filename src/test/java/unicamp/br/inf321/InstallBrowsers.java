package unicamp.br.inf321;

import com.microsoft.playwright.Playwright;

public class InstallBrowsers {
    public static void main(String[] args) {
        Playwright.create().chromium().launch();
    }
}
