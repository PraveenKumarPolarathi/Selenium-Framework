package driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.time.Duration;

/**
 * DriverFactory — Thread-safe WebDriver lifecycle manager.
 *
 * Browser is resolved from the BROWSER system property (default: chrome).
 * Headless mode is resolved from the HEADLESS system property (default: false).
 * Follows AI policy: no hardcoded URLs or credentials; all config via env/system props.
 *
 * DSL config ref: autopilot-ui/configs/new/config.json
 */
public class DriverFactory {

    private static final Logger log = LogManager.getLogger(DriverFactory.class);

    /** Thread-local storage ensures parallel test safety. */
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    private DriverFactory() {
        // Utility class — prevent instantiation
    }

    /**
     * Initialises and stores a new WebDriver for the current thread.
     * Reads BROWSER and HEADLESS from system properties set by Maven Surefire.
     */
    public static void createDriver() {
        String browser  = System.getProperty("BROWSER", "chrome").toLowerCase();
        boolean headless = Boolean.parseBoolean(System.getProperty("HEADLESS", "false"));

        log.info("Creating WebDriver — browser={}, headless={}", browser, headless);

        WebDriver driver;
        switch (browser) {
            case "firefox" -> {
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOpts = new FirefoxOptions();
                if (headless) firefoxOpts.addArguments("--headless");
                driver = new FirefoxDriver(firefoxOpts);
                log.debug("FirefoxDriver created");
            }
            case "edge" -> {
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOpts = new EdgeOptions();
                if (headless) edgeOpts.addArguments("--headless");
                driver = new EdgeDriver(edgeOpts);
                log.debug("EdgeDriver created");
            }
            default -> {
                // Default: Chrome
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOpts = new ChromeOptions();
                if (headless) {
                    chromeOpts.addArguments("--headless=new");
                    chromeOpts.addArguments("--disable-gpu");
                }
                chromeOpts.addArguments("--window-size=1920,1080");
                chromeOpts.addArguments("--no-sandbox");
                chromeOpts.addArguments("--disable-dev-shm-usage");
                driver = new ChromeDriver(chromeOpts);
                log.debug("ChromeDriver created");
            }
        }

        driver.manage().window().maximize();
        // Fail fast on a hung page/script instead of stalling the suite indefinitely
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));
        driverThreadLocal.set(driver);
        log.info("WebDriver initialised and stored in ThreadLocal");
    }

    /**
     * Returns the WebDriver for the current thread.
     * Throws IllegalStateException if createDriver() was not called first.
     */
    public static WebDriver getDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver == null) {
            throw new IllegalStateException(
                "WebDriver not initialised for current thread. Call createDriver() first.");
        }
        return driver;
    }

    /**
     * Quits and removes the WebDriver for the current thread.
     * Safe to call even if the driver is already null.
     */
    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            log.info("Quitting WebDriver for current thread");
            try {
                driver.quit();
            } catch (Exception e) {
                log.warn("Exception while quitting driver: {}", e.getMessage());
            } finally {
                driverThreadLocal.remove();
            }
        }
    }
}
