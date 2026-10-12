package pages;

import driver.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * BasePage — shared WebDriverWait helpers for all Page Objects.
 *
 * Default explicit-wait timeout: 15 seconds.
 * No Thread.sleep() — uses WebDriverWait per ai-policy.md.
 */
public abstract class BasePage {

    protected static final Logger log = LogManager.getLogger(BasePage.class);
    /** Overridable via -DEXPLICIT_WAIT_SECONDS=<n> for slower environments. */
    protected static final int DEFAULT_WAIT_SECONDS =
        Integer.parseInt(System.getProperty("EXPLICIT_WAIT_SECONDS", "15"));

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage() {
        this.driver = DriverFactory.getDriver();
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT_SECONDS));
    }

    /** Wait for element to be visible, then return it. */
    protected WebElement waitVisible(By locator) {
        log.debug("Waiting for element to be visible: {}", locator);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Wait for element to be clickable, then click it.
     * Falls back to a JS click if a transient overlay (e.g. a fading notification
     * banner) intercepts the click at the target coordinates.
     */
    protected void click(By locator) {
        log.debug("Clicking element: {}", locator);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        try {
            element.click();
        } catch (ElementClickInterceptedException e) {
            log.warn("Click intercepted on {} — retrying via JS click", locator);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }

    public void scrollToElement(By element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(element));
    }

    /** Clear field and type value. */
    protected void type(By locator, String value) {
        log.debug("Typing into element: {} | value length: {}", locator, value.length());
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(value);
    }

    /** Select dropdown option by visible text. */
    protected void selectByVisibleText(By locator, String text) {
        log.debug("Selecting '{}' from dropdown: {}", text, locator);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        new Select(element).selectByVisibleText(text);
    }

    /** Returns true if the element is visible within the timeout. */
    protected boolean isVisible(By locator) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            log.debug("Element is visible: {}", locator);
            return true;
        } catch (Exception e) {
            log.warn("Element NOT visible within {}s: {}", DEFAULT_WAIT_SECONDS, locator);
            return false;
        }
    }

    /** Checks (ticks) a checkbox or radio button if not already selected. */
    protected void check(By locator) {
        log.debug("Checking element: {}", locator);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        if (!element.isSelected()) {
            element.click();
        }
    }
}
