package pages;

import org.openqa.selenium.By;

/**
 * LoginPage — Login form at /login.
 *
 * Field classes confirmed from live DOM capture of demowebshop.tricentis.com/login.
 * CSS class selectors used (input.email, input.password) to disambiguate from
 * RegisterPage which uses the same #Email/#Password IDs on a different page.
 * Locator source: output/objects-collections/pageobjects.json → LoginPage
 *
 * Elements:
 *   Login_InputEmail    — input.email
 *   Login_InputPassword — input.password
 *   Login_ButtonLogin   — input.login-button
 */
public class LoginPage extends BasePage {

    // ── Locators ────────────────────────────────────────────────────
    /** Email input — CSS class 'email' confirmed live DOM (input#Email.email[autofocus]). */
    private static final By INPUT_EMAIL    = By.cssSelector("input.email");

    /** Password input — CSS class 'password' confirmed live DOM (input#Password.password). */
    private static final By INPUT_PASSWORD = By.cssSelector("input.password");

    /** Login submit button — confirmed live DOM (input.button-1.login-button[type=submit]). */
    private static final By BUTTON_LOGIN   = By.cssSelector("input.login-button");

    // ── Actions ─────────────────────────────────────────────────────

    /**
     * Enters the username (email address).
     * @param email value from JSON test data — never hardcoded per ai-policy.md
     */
    public void enterEmail(String email) {
        log.info("LoginPage — entering email");
        type(INPUT_EMAIL, email);
    }

    /**
     * Enters the password.
     * @param password value from JSON test data — never hardcoded per ai-policy.md
     */
    public void enterPassword(String password) {
        log.info("LoginPage — entering password");
        type(INPUT_PASSWORD, password);
    }

    /**
     * Clicks the Log In submit button.
     */
    public void clickLogin() {
        log.info("LoginPage — clicking Login button");
        click(BUTTON_LOGIN);
    }

    /**
     * Convenience method: fill credentials and submit the form.
     * @param email    from JSON test data
     * @param password from JSON test data
     */
    public void loginWith(String email, String password) {
        log.info("LoginPage — performing login");
        enterEmail(email);
        enterPassword(password);
        clickLogin();
    }
}
