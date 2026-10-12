package pages;

import org.openqa.selenium.By;

/**
 * CartPage — Shopping cart page at /cart.
 *
 * Locator source: output/objects-collections/pageobjects.json → CartPage
 *
 * Elements:
 *   Cart_TextProductSubtotal   — span.product-subtotal
 *   Cart_CheckboxTermsOfService — #termsofservice
 *   Cart_ButtonCheckout         — #checkout
 */
public class CartPage extends BasePage {

    // ── Locators ────────────────────────────────────────────────────
    /** Product subtotal span — NopCommerce renders per-line price here. */
    private static final By TEXT_PRODUCT_SUBTOTAL   = By.cssSelector("span.product-subtotal");

    /** Terms of service checkbox — NopCommerce standard ID on cart page. */
    private static final By CHECKBOX_TERMS          = By.cssSelector("#termsofservice");

    /** Checkout button — NopCommerce standard ID (button#checkout). */
    private static final By BUTTON_CHECKOUT         = By.cssSelector("#checkout");

    // ── Actions ─────────────────────────────────────────────────────

    /**
     * Asserts that the product subtotal price label is visible.
     * Confirms the product was added to the cart.
     * @return true if subtotal span is visible
     */
    public boolean isSubtotalVisible() {
        log.info("CartPage — asserting product subtotal visible");
        return isVisible(TEXT_PRODUCT_SUBTOTAL);
    }

    /**
     * Checks the Terms of Service checkbox.
     * Required before Checkout can be clicked.
     */
    public void acceptTermsOfService() {
        log.info("CartPage — accepting Terms of Service");
        check(CHECKBOX_TERMS);
    }

    /**
     * Clicks the Checkout button.
     * Navigates to the multi-step checkout page.
     */
    public void clickCheckout() {
        log.info("CartPage — clicking Checkout");
        click(BUTTON_CHECKOUT);
    }
}
