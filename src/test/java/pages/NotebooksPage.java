package pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * NotebooksPage — Product listing page at /notebooks.
 *
 * Locator source: output/objects-collections/pageobjects.json → NotebooksPage
 *
 * Elements:
 *   Notebooks_ButtonAddToCart14Inch — Add to cart quick-action button, scoped to the
 *                                     14.1-inch Laptop product tile (data-productid='31')
 */
public class NotebooksPage extends BasePage {

    // ── Locators ────────────────────────────────────────────────────
    /**
     * Add to cart quick-action button for the 14.1-inch Laptop.
     * Scoped to div.product-item[data-productid='31'] so it targets this product
     * specifically, regardless of its position in the listing grid.
     */
    private static final By BUTTON_ADD_TO_CART_14_INCH =
            By.cssSelector("div.product-item[data-productid='31'] input.product-box-add-to-cart-button");

    private static final By CONFIRMATION_MESSAGE = By.cssSelector("#bar-notification .content");
    private static final By CLOSE_CONFIRMATION_MESSAGE = By.cssSelector("#bar-notification .close");
    private static final By BAR_NOTIFICATION = By.id("bar-notification");

    // ── Actions ─────────────────────────────────────────────────────

    /**
     * Clicks the Add to cart quick-action button for the 14.1-inch Laptop directly
     * from the listing page (AJAX add-to-cart, no navigation to the product detail page).
     * Waits for the resulting bar-notification popup, dismisses it, then waits for it
     * to fully disappear so it can't intercept clicks in the next step.
     */
    public void clickLaptopProduct() {
        log.info("NotebooksPage — clicking Add to cart for 14.1-inch Laptop");
        scrollToElement(BUTTON_ADD_TO_CART_14_INCH);
        click(BUTTON_ADD_TO_CART_14_INCH);

        boolean visible = isVisible(CONFIRMATION_MESSAGE);
        Assertions.assertTrue(visible, "Confirmation Message displayed");
        click(CLOSE_CONFIRMATION_MESSAGE);

        // bar-notification fades out via CSS transition — wait for it to fully clear
        // so it can't intercept a click on the header cart icon in the next step.
        wait.until(ExpectedConditions.invisibilityOfElementLocated(BAR_NOTIFICATION));
    }
}
