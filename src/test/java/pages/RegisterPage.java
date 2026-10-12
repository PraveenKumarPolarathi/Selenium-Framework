package pages;

import org.openqa.selenium.By;

/**
 * RegisterPage — User registration form at /register.
 *
 * All field IDs confirmed from live DOM capture of demowebshop.tricentis.com/register.
 * Locator source: output/objects-collections/pageobjects.json → RegisterPage
 *
 * Elements:
 *   Register_RadioButtonGenderMale   — #gender-male
 *   Register_RadioButtonGenderFemale — #gender-female
 *   Register_InputFirstName          — #FirstName
 *   Register_InputLastName           — #LastName
 *   Register_InputEmail              — #Email
 *   Register_InputPassword           — #Password
 *   Register_InputConfirmPassword    — #ConfirmPassword
 *   Register_ButtonRegister          — #register-button
 */
public class RegisterPage extends BasePage {

    // ── Locators ────────────────────────────────────────────────────
    private static final By RADIO_GENDER_MALE      = By.cssSelector("#gender-male");
    private static final By RADIO_GENDER_FEMALE    = By.cssSelector("#gender-female");
    private static final By INPUT_FIRST_NAME       = By.cssSelector("#FirstName");
    private static final By INPUT_LAST_NAME        = By.cssSelector("#LastName");
    private static final By INPUT_EMAIL            = By.cssSelector("#Email");
    private static final By INPUT_PASSWORD         = By.cssSelector("#Password");
    private static final By INPUT_CONFIRM_PASSWORD = By.cssSelector("#ConfirmPassword");
    private static final By BUTTON_REGISTER        = By.cssSelector("#register-button");

    // ── Actions ─────────────────────────────────────────────────────

    /** Selects the Male gender radio button. */
    public void selectGenderMale() {
        log.info("RegisterPage — selecting gender: Male");
        check(RADIO_GENDER_MALE);
    }

    /** Selects the Female gender radio button. */
    public void selectGenderFemale() {
        log.info("RegisterPage — selecting gender: Female");
        check(RADIO_GENDER_FEMALE);
    }

    /**
     * Enters the first name in the FirstName field.
     * @param firstName value from JSON test data — never hardcoded
     */
    public void enterFirstName(String firstName) {
        log.info("RegisterPage — entering first name");
        type(INPUT_FIRST_NAME, firstName);
    }

    /**
     * Enters the last name in the LastName field.
     * @param lastName value from JSON test data
     */
    public void enterLastName(String lastName) {
        log.info("RegisterPage — entering last name");
        type(INPUT_LAST_NAME, lastName);
    }

    /**
     * Enters the email address.
     * @param email value from JSON test data — never hardcoded per ai-policy.md
     */
    public void enterEmail(String email) {
        log.info("RegisterPage — entering email");
        type(INPUT_EMAIL, email);
    }

    /**
     * Enters the password.
     * @param password value from JSON test data — never hardcoded per ai-policy.md
     */
    public void enterPassword(String password) {
        log.info("RegisterPage — entering password");
        type(INPUT_PASSWORD, password);
    }

    /**
     * Enters the confirm password.
     * @param password value from JSON test data
     */
    public void enterConfirmPassword(String password) {
        log.info("RegisterPage — entering confirm password");
        type(INPUT_CONFIRM_PASSWORD, password);
    }

    /**
     * Submits the registration form by clicking the Register button.
     */
    public void clickRegister() {
        log.info("RegisterPage — clicking Register button");
        click(BUTTON_REGISTER);
    }

    /**
     * Complete registration with all user details.
     * Convenience method that calls individual field setters in order.
     *
     * @param firstName  from test data
     * @param lastName   from test data
     * @param email      from test data
     * @param password   from test data
     */
    public void registerAs(String firstName, String lastName, String email, String password) {
        log.info("RegisterPage — filling out full registration form");
        selectGenderMale();
        enterFirstName(firstName);
        enterLastName(lastName);
        enterEmail(email);
        enterPassword(password);
        enterConfirmPassword(password);
        clickRegister();
    }
}
