package pages;

import org.openqa.selenium.By;

/**
 * HomePageLoggedIn — Authenticated state of the Tricentis homepage.
 *
 * NopCommerce renders a.ico-logout in the header when the user is authenticated.
 * Locator source: output/objects-collections/pageobjects.json → HomePageLoggedIn
 *
 * Elements:
 *   Home_LinkLogout — a.ico-logout
 */
public class HomePageLoggedIn extends BasePage {

    // ── Locators ────────────────────────────────────────────────────
    /** Log out link visible in the header when the user session is active. */
    private static final By LINK_LOGOUT = By.cssSelector("a.ico-logout");

    // ── Actions ─────────────────────────────────────────────────────

    /**
     * Asserts that the Logout link is visible — confirms successful login.
     * @return true if the Logout link is present and visible
     */
    public boolean isLogoutLinkVisible() {
        log.info("HomePageLoggedIn — asserting Logout link visible");
        return isVisible(LINK_LOGOUT);
    }

    /**
     * Clicks the Log out link to end the user session.
     */
    public void clickLogout() {
        log.info("HomePageLoggedIn — clicking Logout link");
        click(LINK_LOGOUT);
    }
}
