package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import pages.LandingPage;
import pages.RegisterConfirmationPage;
import pages.RegisterPage;
import utils.ExtentReportManager;
import utils.TestDataReader;
import com.github.javafaker.Faker;

/**
 * RegisterSteps — Cucumber step definitions for TC_001: Register a new user.
 *
 * Test data loaded from src/test/resources/test-data/credentials.json (JSON format
 * per maintainTestData: "JSON" in autopilot-ui/configs/new/config.json).
 * No hardcoded credentials per ai-policy.md.
 */
public class RegisterSteps {

    private static final Logger log = LogManager.getLogger(RegisterSteps.class);

    private final LandingPage              landingPage              = new LandingPage();
    private final RegisterPage             registerPage             = new RegisterPage();
    private final RegisterConfirmationPage registerConfirmationPage = new RegisterConfirmationPage();

    // ── Given ────────────────────────────────────────────────────────

    @Given("the user is on the demo web shop home page")
    public void theUserIsOnTheTricentisHomePage() {
        log.info("Step: the user is on the Tricentis home page");
        ExtentReportManager.getTest().info("Navigated to BASE_URL (handled by Hooks.setUp)");
        // Navigation is performed in Hooks.@Before — nothing extra needed here
    }

    // ── When ─────────────────────────────────────────────────────────

    @When("the user clicks the Register button")
    public void theUserClicksTheRegisterButton() {
        log.info("Step: the user clicks the Register button");
        ExtentReportManager.getTest().info("Clicking Register link");
        landingPage.clickRegisterLink();
    }

    @When("the user fills in the registration form with valid details")
    public void theUserFillsInTheRegistrationFormWithValidDetails() {
        log.info("Step: the user fills in the registration form");
        ExtentReportManager.getTest().info("Filling registration form with test data");

        // Load test data from JSON — never hardcoded per ai-policy.md
        String firstName = TestDataReader.get("register.firstName");
        String lastName  = TestDataReader.get("register.lastName");
        Faker faker = new Faker();
        String email     = faker.internet().emailAddress();;
        String password  = TestDataReader.get("register.password");

        registerPage.registerAs(firstName, lastName, email, password);
    }

    @When("the user fills in the registration form with custom {string} & {string}")
    public void theUserFillsInTheRegistrationFormWithCustomDetails(String firstName, String lastName) {
        log.info("Step: the user fills in the registration form with custom details");
        ExtentReportManager.getTest().info("Filling registration form with custom test data");
        Faker faker = new Faker();
        String email     = faker.internet().emailAddress();;
        String password  = TestDataReader.get("register.password");

        registerPage.registerAs(firstName, lastName, email, password);
    }

    // ── Then ─────────────────────────────────────────────────────────

    @Then("the registration success message should be displayed")
    public void theRegistrationSuccessMessageShouldBeDisplayed() {
        log.info("Step: assert registration success message");
        boolean visible = registerConfirmationPage.isSuccessMessageVisible();
        ExtentReportManager.getTest().info("Registration success message visible: " + visible);
        Assertions.assertTrue(visible,
            "Expected registration success message (div.result) to be visible");
    }

    @When("the user clicks Continue after registration")
    public void theUserClicksContinueAfterRegistration() {
        log.info("Step: clicking Continue after registration");
        ExtentReportManager.getTest().info("Clicking Continue on confirmation page");
        registerConfirmationPage.clickContinue();
    }

    @Then("the user should be redirected to the homepage")
    public void theUserShouldBeRedirectedToTheHomepage() {
        log.info("Step: assert user is on homepage");
        boolean loaded = landingPage.isHomePageLoaded();
        ExtentReportManager.getTest().info("Homepage logo visible: " + loaded);
        Assertions.assertTrue(loaded,
            "Expected homepage header logo (div.header-logo) to be visible after registration");
    }
}
