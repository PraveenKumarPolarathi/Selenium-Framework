package pages;

import org.openqa.selenium.By;

/**
 * RegisterConfirmationPage — Post-registration success page.
 *
 * NopCommerce renders the success message in div.result.
 * Locator source: output/objects-collections/pageobjects.json → RegisterConfirmationPage
 *
 * Elements:
 *   RegisterConfirmation_TextResult      — div.result
 *   RegisterConfirmation_ButtonContinue  — input.register-continue-button
 */
public class RegisterConfirmationPage extends BasePage {

    // ── Locators ────────────────────────────────────────────────────
    /** Confirmation message container rendered by NopCommerce after successful registration. */
    private static final By TEXT_RESULT       = By.cssSelector("div.result");

    /** Continue button that returns the user to the homepage after registration. */
    private static final By BUTTON_CONTINUE   = By.cssSelector("input.register-continue-button");

    // ── Actions ─────────────────────────────────────────────────────

    /**
     * Asserts that the registration success message is visible.
     * @return true if the confirmation message element is present and visible
     */
    public boolean isSuccessMessageVisible() {
        log.info("RegisterConfirmationPage — asserting success message visible");
        return isVisible(TEXT_RESULT);
    }

    /**
     * Clicks the Continue button to navigate to the homepage.
     */
    public void clickContinue() {
        log.info("RegisterConfirmationPage — clicking Continue");
        click(BUTTON_CONTINUE);
    }
}
