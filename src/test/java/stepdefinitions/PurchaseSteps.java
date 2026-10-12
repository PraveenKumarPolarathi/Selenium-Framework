package stepdefinitions;

import driver.DriverFactory;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import pages.CartPage;
import pages.CheckoutPage;
import pages.NotebooksPage;
import pages.OrderCompletePage;
import pages.ProductDetailPage;
import utils.EnvConfig;
import utils.ExtentReportManager;
import utils.TestDataReader;

/**
 * PurchaseSteps — Cucumber step definitions for TC_003: Login and Purchase a product.
 *
 * Test data (address, phone, zip) loaded from credentials.json.
 * No hardcoded values per ai-policy.md.
 */
public class PurchaseSteps {

    private static final Logger log = LogManager.getLogger(PurchaseSteps.class);

    private final NotebooksPage    notebooksPage    = new NotebooksPage();
    private final ProductDetailPage productDetailPage = new ProductDetailPage();
    private final CartPage          cartPage          = new CartPage();
    private final CheckoutPage      checkoutPage      = new CheckoutPage();
    private final OrderCompletePage orderCompletePage = new OrderCompletePage();

    // ── When ─────────────────────────────────────────────────────────

    @When("the user navigates to the Notebooks category page")
    public void theUserNavigatesToNotebooksPage() {
        log.info("Step: navigate to /notebooks");
        ExtentReportManager.getTest().info("Navigating to /notebooks category page");
        DriverFactory.getDriver().get(EnvConfig.baseUrl() + "notebooks");
    }

    @When("the user selects add to cart button on the 14.1-inch Laptop")
    public void theUserSelectsTheLaptopProduct() {
        log.info("Step: the user selects 14.1-inch Laptop");
        ExtentReportManager.getTest().info("Clicking 14.1-inch Laptop product link");
        notebooksPage.clickLaptopProduct();
    }

    @When("the user selects shopping cart button on the top of the page")
    public void theUserAddsTheProductToTheCart() {
        log.info("Step: the user adds product to cart");
        ExtentReportManager.getTest().info("Clicking Add to Cart button");
        productDetailPage.openCartFromHeaderIcon();
    }

    // ── Then ─────────────────────────────────────────────────────────

    @Then("the cart total amount should be displayed")
    public void theCartTotalAmountShouldBeDisplayed() {
        log.info("Step: assert cart subtotal visible");
        boolean visible = cartPage.isSubtotalVisible();
        ExtentReportManager.getTest().info("Product subtotal visible: " + visible);
        Assertions.assertTrue(visible,
            "Expected product subtotal (span.product-subtotal) to be visible in cart");
    }

    // ── When ─────────────────────────────────────────────────────────

    @When("the user agrees to Terms and Conditions and clicks Checkout")
    public void theUserAgreesToTermsAndClicksCheckout() {
        log.info("Step: accept Terms & Conditions and click Checkout");
        ExtentReportManager.getTest().info("Accepting Terms of Service and clicking Checkout");
        cartPage.acceptTermsOfService();
        cartPage.clickCheckout();
    }

    @When("the user fills in the billing address and clicks Continue")
    public void theUserFillsInTheBillingAddressAndClicksContinue() {
        log.info("Step: filling billing address");
        ExtentReportManager.getTest().info("Filling billing address from JSON test data");

        // All address fields from JSON test data — never hardcoded per ai-policy.md
        String country  = TestDataReader.get("checkout.country");
        String address1 = TestDataReader.get("checkout.address1");
        String city     = TestDataReader.get("checkout.city");
        String zip      = TestDataReader.get("checkout.zipCode");
        String phone    = TestDataReader.get("checkout.phone");

        checkoutPage.fillBillingAddress(country, address1, city, zip, phone);
        checkoutPage.clickBillingContinue();
    }

    @When("the user selects In-Store Pickup and clicks Continue")
    public void theUserSelectsInStorePickupAndClicksContinue() {
        log.info("Step: selecting In-Store Pickup shipping");
        ExtentReportManager.getTest().info("Selecting In-Store Pickup and clicking Continue");
        checkoutPage.selectInStorePickup();
        checkoutPage.clickShippingContinue();
    }

    @When("the user selects Cash On Delivery and clicks Continue")
    public void theUserSelectsCashOnDeliveryAndClicksContinue() {
        log.info("Step: selecting Cash On Delivery");
        ExtentReportManager.getTest().info("Selecting Cash On Delivery and clicking Continue");
        checkoutPage.selectCashOnDelivery();
        checkoutPage.clickPaymentMethodContinue();
    }

    @Then("the Pay by COD selection should be confirmed")
    public void thePayByCODSelectionShouldBeConfirmed() {
        log.info("Step: assert payment info (COD) visible");
        boolean visible = checkoutPage.isPaymentInfoVisible();
        ExtentReportManager.getTest().info("Payment info (div.payment-info) visible: " + visible);
        Assertions.assertTrue(visible,
            "Expected payment info panel (div.payment-info) to be visible");
    }

    @When("the user clicks Continue on payment information")
    public void theUserClicksContinueOnPaymentInformation() {
        log.info("Step: clicking Payment Info Continue");
        ExtentReportManager.getTest().info("Clicking Continue on Payment Info step");
        checkoutPage.clickPaymentInfoContinue();
    }

    @Then("the ordered product should be visible in the confirmation")
    public void theOrderedProductShouldBeVisibleInTheConfirmation() {
        log.info("Step: assert product visible in order confirm");
        boolean visible = checkoutPage.isProductVisibleInOrderReview();
        ExtentReportManager.getTest().info("Product in order review visible: " + visible);
        Assertions.assertTrue(visible,
            "Expected 14.1-inch Laptop to be visible in the order confirmation table");
    }

    @When("the user confirms the order")
    public void theUserConfirmsTheOrder() {
        log.info("Step: clicking Confirm Order");
        ExtentReportManager.getTest().info("Clicking Confirm Order button");
        checkoutPage.clickConfirmOrder();
    }

    @Then("the order number and success message should be displayed")
    public void theOrderNumberAndSuccessMessageShouldBeDisplayed() {
        log.info("Step: assert order number and success message visible");
        boolean orderNumVisible = orderCompletePage.isOrderNumberVisible();
        boolean successVisible  = orderCompletePage.isSuccessTitleVisible();

        ExtentReportManager.getTest()
            .info("Order number visible: " + orderNumVisible +
                  " | Success title visible: " + successVisible);

        Assertions.assertTrue(orderNumVisible,
            "Expected Order Number to be visible on order completion page");
        Assertions.assertTrue(successVisible,
            "Expected success title to be visible on order completion page");
    }
}
