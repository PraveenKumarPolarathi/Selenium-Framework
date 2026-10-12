package pages;

import org.openqa.selenium.By;

/**
 * OrderCompletePage — Order completion page after successful checkout.
 *
 * Locator source: output/objects-collections/pageobjects.json → OrderCompletePage
 *
 * Elements:
 *   OrderComplete_TextOrderNumber   — XPath on ul.details-list li
 *   OrderComplete_TextSuccessTitle  — div.order-completed .title strong
 */
public class OrderCompletePage extends BasePage {

    // ── Locators ────────────────────────────────────────────────────
    /**
     * Order number list item.
     * NopCommerce renders order details in ul.details-list.
     * XPath locates the li containing 'Order Number' text.
     */
    private static final By TEXT_ORDER_NUMBER =
        By.xpath("//li[contains(normalize-space(),'Order number:')]");

    /**
     * Order success title.
     * NopCommerce renders 'Your order has been successfully processed!'
     * inside div.order-completed .title strong.
     */
    private static final By TEXT_SUCCESS_TITLE =
        By.cssSelector("div.order-completed .title strong");

    // ── Actions ─────────────────────────────────────────────────────

    /**
     * Asserts that the order number is displayed on the completion page.
     * @return true if the Order Number li item is visible
     */
    public boolean isOrderNumberVisible() {
        log.info("OrderCompletePage — asserting order number visible");
        return isVisible(TEXT_ORDER_NUMBER);
    }

    /**
     * Asserts that the success title message is visible.
     * @return true if the success strong element is visible
     */
    public boolean isSuccessTitleVisible() {
        log.info("OrderCompletePage — asserting success title visible");
        return isVisible(TEXT_SUCCESS_TITLE);
    }
}
