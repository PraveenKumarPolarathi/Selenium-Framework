package hooks;

import driver.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import utils.EnvConfig;
import utils.ExtentReportManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Hooks — Cucumber lifecycle hooks.
 *
 * @Before  : Initialises WebDriver, navigates to BASE_URL, starts Extent test node.
 * @After   : Captures screenshot on failure, quits driver, flushes Extent report.
 * @AfterStep: Logs each step result to Log4j2.
 *
 * BASE_URL is resolved from the BASE_URL system property per ai-policy.md.
 * No hardcoded credentials or URLs.
 */
public class Hooks {

    private static final Logger log = LogManager.getLogger(Hooks.class);
    private static final String SCREENSHOT_DIR = "screenshots";

    @Before(order = 0)
    public void setUp(Scenario scenario) {
        log.info("========== Starting scenario: {} ==========", scenario.getName());

        // Initialise browser from BROWSER / HEADLESS system properties
        DriverFactory.createDriver();

        // Navigate to BASE_URL — sourced from environment via system property
        String baseUrl = EnvConfig.baseUrl();
        log.info("Navigating to BASE_URL: {}", baseUrl);
        DriverFactory.getDriver().get(baseUrl);

        // Initialise Extent test node for current scenario
        ExtentReportManager.createTest(scenario.getName());
        log.debug("Extent test node created for: {}", scenario.getName());
    }

    @After(order = 0)
    public void tearDown(Scenario scenario) {
        WebDriver driver = DriverFactory.getDriver();

        if (scenario.isFailed()) {
            log.error("Scenario FAILED: {}", scenario.getName());
            // Capture and attach screenshot to Cucumber + Extent report, and persist to disk
            try {
                byte[] screenshot = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "Failure Screenshot");
                ExtentReportManager.getTest().fail("Screenshot on failure",
                    com.aventstack.extentreports.MediaEntityBuilder
                        .createScreenCaptureFromBase64String(
                            java.util.Base64.getEncoder().encodeToString(screenshot))
                        .build());
                log.debug("Screenshot captured and attached for failed scenario");
                saveScreenshotToDisk(scenario, screenshot);
            } catch (Exception e) {
                log.warn("Could not capture screenshot: {}", e.getMessage());
            }
            ExtentReportManager.getTest().fail("Scenario failed: " + scenario.getName());
        } else {
            log.info("Scenario PASSED: {}", scenario.getName());
            ExtentReportManager.getTest().pass("Scenario passed: " + scenario.getName());
        }

        // Always quit driver
        DriverFactory.quitDriver();

        // Flush Extent report after each scenario
        ExtentReportManager.flush();
        log.info("========== Ended scenario: {} — Status: {} ==========",
            scenario.getName(), scenario.getStatus());
    }

    @AfterStep
    public void afterStep(Scenario scenario) {
        // Log every step outcome to Log4j2
        log.debug("Step completed — Scenario: {} | Status: {}",
            scenario.getName(), scenario.getStatus());
    }

    /**
     * Writes the failure screenshot to the screenshots/ directory so it is picked up
     * by the CI "Upload Screenshots" artifact step, in addition to the Cucumber/Extent
     * attachments (which only live inside the HTML reports).
     */
    private void saveScreenshotToDisk(Scenario scenario, byte[] screenshot) {
        try {
            Path dir = Path.of(SCREENSHOT_DIR);
            Files.createDirectories(dir);
            String safeName = scenario.getName().replaceAll("[^a-zA-Z0-9-_]", "_");
            Path file = dir.resolve(safeName + "_" + System.currentTimeMillis() + ".png");
            Files.write(file, screenshot);
            log.info("Failure screenshot saved to {}", file);
        } catch (IOException e) {
            log.warn("Could not save screenshot to disk: {}", e.getMessage());
        }
    }
}
