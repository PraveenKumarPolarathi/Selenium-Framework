package pages;

import org.openqa.selenium.By;

/**
 * LandingPage — Tricentis Demo Web Shop home page (unauthenticated state).
 *
 * Page: ${env.BASE_URL}
 * Locators sourced from: output/objects-collections/pageobjects.json
 *
 * Elements:
 *   Landing_LinkRegister — a.ico-register  (nav header)
 *   Landing_LinkLogin    — a.ico-login     (nav header)
 *   Landing_LogoHeader   — div.header-logo (confirms homepage)
 */
public class LandingPage extends BasePage {

    // ── Locators ────────────────────────────────────────────────────
    /** Register link in the top nav bar. CSS class ico-register confirmed from live DOM. */
    private static final By LINK_REGISTER  = By.cssSelector("a.ico-register");

    /** Log in link in the top nav bar. CSS class ico-login confirmed from live DOM. */
    private static final By LINK_LOGIN     = By.cssSelector("a.ico-login");

    /** Header logo container — used to assert the user is on the homepage. */
    private static final By LOGO_HEADER    = By.cssSelector("div.header-logo");

    // ── Actions ─────────────────────────────────────────────────────

    /**
     * Clicks the Register navigation link.
     * Navigates to the registration form page.
     */
    public void clickRegisterLink() {
        log.info("LandingPage — clicking Register link");
        click(LINK_REGISTER);
    }

    /**
     * Clicks the Log in navigation link.
     * Navigates to the login form page.
     */
    public void clickLoginLink() {
        log.info("LandingPage — clicking Login link");
        click(LINK_LOGIN);
    }

    /**
     * Asserts that the header logo is visible — confirms user is on the homepage.
     *
     * @return true if logo is visible
     */
    public boolean isHomePageLoaded() {
        log.info("LandingPage — asserting header logo visible");
        return isVisible(LOGO_HEADER);
    }

    /**
     * Asserts that the Login link is visible in the nav — confirms user is logged out.
     *
     * @return true if Login link is visible
     */
    public boolean isLoginLinkVisible() {
        log.info("LandingPage — asserting Login link visible (logged-out state)");
        return isVisible(LINK_LOGIN);
    }
}
