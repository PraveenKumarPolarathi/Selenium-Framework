package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * ProductDetailPage — header cart icon, used after a product has been added to the
 * cart to navigate to /cart.
 *
 * Locator source: output/objects-collections/pageobjects.json → ProductDetailPage
 */
public class ProductDetailPage extends BasePage {

    // ── Locators ────────────────────────────────────────────────────
    /** Header cart icon — navigates to /cart. Present on every page. */
    private static final By LINK_HEADER_CART_ICON = By.cssSelector("#topcartlink > a.ico-cart");

    private static final By BAR_NOTIFICATION = By.id("bar-notification");

    // ── Actions ─────────────────────────────────────────────────────

    /**
     * Clicks the header cart icon to navigate to the cart page.
     * Waits for any lingering bar-notification to fully clear first — it fades out
     * via a CSS transition and can intercept a click at the icon's coordinates.
     */
    public void openCartFromHeaderIcon() {
        log.info("ProductDetailPage — opening cart via header cart icon");
        wait.until(ExpectedConditions.invisibilityOfElementLocated(BAR_NOTIFICATION));
        click(LINK_HEADER_CART_ICON);
    }
}
