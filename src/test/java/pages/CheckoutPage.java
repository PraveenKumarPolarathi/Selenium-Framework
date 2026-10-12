package pages;

import org.openqa.selenium.By;

/**
 * CheckoutPage — Multi-step checkout: Billing → Shipping → Payment Method → Payment Info → Confirm.
 *
 * Field IDs follow NopCommerce standard naming conventions.
 * Locator source: output/objects-collections/pageobjects.json → CheckoutPage
 *
 * Billing elements:
 *   Checkout_DropdownBillingCountry   — #BillingNewAddress_CountryId
 *   Checkout_InputBillingAddress1     — #BillingNewAddress_Address1
 *   Checkout_InputBillingCity         — #BillingNewAddress_City
 *   Checkout_InputBillingZip          — #BillingNewAddress_ZipPostalCode
 *   Checkout_InputBillingPhone        — #BillingNewAddress_PhoneNumber
 *   Checkout_ButtonBillingContinue    — input.new-address-next-step-button
 *
 * Shipping elements:
 *   Checkout_RadioButtonShippingInStore — XPath by label text
 *   Checkout_ButtonShippingContinue     — input.shipping-method-next-step-button
 *
 * Payment Method elements:
 *   Checkout_RadioButtonPaymentCOD        — XPath by label text
 *   Checkout_ButtonPaymentMethodContinue  — input.payment-method-next-step-button
 *
 * Payment Info elements:
 *   Checkout_TextPaymentInfo              — div.payment-info
 *   Checkout_ButtonPaymentInfoContinue    — input.payment-info-next-step-button
 *
 * Confirm Order elements:
 *   Checkout_TextConfirmProduct           — XPath on product td
 *   Checkout_ButtonConfirmOrder           — input.confirm-order-next-step-button
 */
public class CheckoutPage extends BasePage {

    // ── Billing Locators ─────────────────────────────────────────────
    private static final By DROPDOWN_BILLING_COUNTRY =
        By.cssSelector("#BillingNewAddress_CountryId");

    private static final By INPUT_BILLING_ADDRESS1 =
        By.cssSelector("#BillingNewAddress_Address1");

    private static final By INPUT_BILLING_CITY =
        By.cssSelector("#BillingNewAddress_City");

    private static final By INPUT_BILLING_ZIP =
        By.cssSelector("#BillingNewAddress_ZipPostalCode");

    private static final By INPUT_BILLING_PHONE =
        By.cssSelector("#BillingNewAddress_PhoneNumber");

    private static final By BUTTON_BILLING_CONTINUE =
        By.cssSelector("#billing-buttons-container input.new-address-next-step-button");

    // ── Shipping Locators ────────────────────────────────────────────
    /**
     * In-Store Pickup radio button.
     * Shipping option IDs are dynamic — XPath locates the radio preceding
     * a label whose text contains 'In-Store Pickup' (parent-child relationship).
     */
    private static final By RADIO_SHIPPING_INSTORE =
            By.cssSelector("#PickUpInStore");

    private static final By BUTTON_SHIPPING_CONTINUE =
            By.cssSelector("#shipping-buttons-container > input.button-1");

    // ── Payment Method Locators ──────────────────────────────────────
    /**
     * Cash On Delivery radio button.
     * Payment method IDs are dynamic — XPath by label text sibling relationship.
     */
    private static final By RADIO_PAYMENT_COD =
        By.xpath("//label[contains(text(),'Cash On Delivery')]/preceding-sibling::input[@type='radio']");

    private static final By BUTTON_PAYMENT_METHOD_CONTINUE =
        By.cssSelector("input.payment-method-next-step-button");

    // ── Payment Info Locators ────────────────────────────────────────
    /** NopCommerce renders COD confirmation text inside div.payment-info. */
    private static final By TEXT_PAYMENT_INFO =
        By.cssSelector("div.payment-info");

    private static final By BUTTON_PAYMENT_INFO_CONTINUE =
        By.cssSelector("input.payment-info-next-step-button");

    // ── Confirm Order Locators ───────────────────────────────────────
    /**
     * Product name in the order confirmation table.
     * XPath scoped to the product td cell with exact product name anchor text.
     */
    private static final By TEXT_CONFIRM_PRODUCT =
        By.xpath("//td[contains(@class,'product')]//a[normalize-space(text())='14.1-inch Laptop']");

    private static final By BUTTON_CONFIRM_ORDER =
        By.cssSelector("input.confirm-order-next-step-button");

    // ── Billing Step Actions ─────────────────────────────────────────

    /**
     * Fills the billing address form and clicks Continue.
     * All values sourced from JSON test data — never hardcoded per ai-policy.md.
     *
     * @param country  e.g. "India"
     * @param address1 street address from testData
     * @param city     city from testData
     * @param zip      postal code from testData
     * @param phone    phone from testData
     */
    public void fillBillingAddress(String country, String address1,
                                   String city, String zip, String phone) {
        log.info("CheckoutPage — filling billing address (country={})", country);
        selectByVisibleText(DROPDOWN_BILLING_COUNTRY, country);
        type(INPUT_BILLING_ADDRESS1, address1);
        type(INPUT_BILLING_CITY, city);
        type(INPUT_BILLING_ZIP, zip);
        type(INPUT_BILLING_PHONE, phone);
    }

    /** Clicks Continue on the Billing step. */
    public void clickBillingContinue() {
        log.info("CheckoutPage — clicking Billing Continue");
        click(BUTTON_BILLING_CONTINUE);
    }

    // ── Shipping Step Actions ────────────────────────────────────────

    /** Selects the In-Store Pickup shipping option. */
    public void selectInStorePickup() {
        log.info("CheckoutPage — selecting In-Store Pickup shipping");
        check(RADIO_SHIPPING_INSTORE);
    }

    /** Clicks Continue on the Shipping step. */
    public void clickShippingContinue() {
        log.info("CheckoutPage — clicking Shipping Continue");
        click(BUTTON_SHIPPING_CONTINUE);
    }

    // ── Payment Method Step Actions ──────────────────────────────────

    /** Selects the Cash On Delivery payment method. */
    public void selectCashOnDelivery() {
        log.info("CheckoutPage — selecting Cash On Delivery");
        check(RADIO_PAYMENT_COD);
    }

    /** Clicks Continue on the Payment Method step. */
    public void clickPaymentMethodContinue() {
        log.info("CheckoutPage — clicking Payment Method Continue");
        click(BUTTON_PAYMENT_METHOD_CONTINUE);
    }

    // ── Payment Info Step Actions ────────────────────────────────────

    /**
     * Asserts that the payment info panel is visible (COD confirmation text).
     * @return true if div.payment-info is visible
     */
    public boolean isPaymentInfoVisible() {
        log.info("CheckoutPage — asserting payment info visible");
        return isVisible(TEXT_PAYMENT_INFO);
    }

    /** Clicks Continue on the Payment Info step. */
    public void clickPaymentInfoContinue() {
        log.info("CheckoutPage — clicking Payment Info Continue");
        click(BUTTON_PAYMENT_INFO_CONTINUE);
    }

    // ── Confirm Order Step Actions ───────────────────────────────────

    /**
     * Asserts that the 14.1-inch Laptop line item is visible in the order review table.
     * @return true if the product anchor is visible
     */
    public boolean isProductVisibleInOrderReview() {
        log.info("CheckoutPage — asserting product visible in order review");
        return isVisible(TEXT_CONFIRM_PRODUCT);
    }

    /** Clicks Confirm Order to submit the purchase. */
    public void clickConfirmOrder() {
        log.info("CheckoutPage — clicking Confirm Order");
        click(BUTTON_CONFIRM_ORDER);
    }
}
