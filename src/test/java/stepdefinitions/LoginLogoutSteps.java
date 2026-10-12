package stepdefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import pages.HomePageLoggedIn;
import pages.LandingPage;
import pages.LoginPage;
import utils.ExtentReportManager;
import utils.TestDataReader;

/**
 * LoginLogoutSteps — Cucumber step definitions for TC_002: Login and Logout.
 *
 * Credentials loaded from src/test/resources/test-data/credentials.json.
 * No hardcoded credentials per ai-policy.md.
 */
public class LoginLogoutSteps {

    private static final Logger log = LogManager.getLogger(LoginLogoutSteps.class);

    private final LandingPage       landingPage       = new LandingPage();
    private final LoginPage         loginPage         = new LoginPage();
    private final HomePageLoggedIn  homePageLoggedIn  = new HomePageLoggedIn();

    // ── When ─────────────────────────────────────────────────────────

    @When("the user clicks the Login link on the Landing Page")
    public void theUserClicksTheLoginLinkOnTheLandingPage() {
        log.info("Step: the user clicks the Login link");
        ExtentReportManager.getTest().info("Clicking Login link in nav");
        landingPage.clickLoginLink();
    }

    @When("the user logs in with valid credentials")
    public void theUserLogsInWithValidCredentials() {
        log.info("Step: the user logs in with valid credentials");
        ExtentReportManager.getTest().info("Entering credentials from JSON test data");

        // Credentials from JSON test data — never hardcoded per ai-policy.md
        String email    = TestDataReader.get("login.email");
        String password = TestDataReader.get("login.password");

        loginPage.loginWith(email, password);
    }

    // ── Then ─────────────────────────────────────────────────────────

    @Then("the Logout button should be visible in the navbar")
    public void theLogoutButtonShouldBeVisibleInTheNavbar() {
        log.info("Step: assert Logout link visible");
        boolean visible = homePageLoggedIn.isLogoutLinkVisible();
        ExtentReportManager.getTest().info("Logout link visible: " + visible);
        Assertions.assertTrue(visible,
            "Expected Logout link (a.ico-logout) to be visible after login");
    }

    @When("the user clicks the Logout button")
    public void theUserClicksTheLogoutButton() {
        log.info("Step: the user clicks the Logout button");
        ExtentReportManager.getTest().info("Clicking Logout link");
        homePageLoggedIn.clickLogout();
    }

    @Then("the Login link should be visible in the navbar")
    public void theLoginLinkShouldBeVisibleInTheNavbar() {
        log.info("Step: assert Login link visible after logout");
        boolean visible = landingPage.isLoginLinkVisible();
        ExtentReportManager.getTest().info("Login link visible: " + visible);
        Assertions.assertTrue(visible,
            "Expected Login link (a.ico-login) to be visible after logout");
    }
}
