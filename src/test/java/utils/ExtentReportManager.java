package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * ExtentReportManager — thread-safe Extent Reports v5 manager.
 *
 * Usage:
 *   ExtentReportManager.createTest("TC_001 - Register")
 *   ExtentReportManager.getTest().info("some step log")
 *   ExtentReportManager.flush()   ← called once in @AfterAll
 */
public class ExtentReportManager {

    private static final Logger log = LogManager.getLogger(ExtentReportManager.class);

    private static final ExtentReports          extent;
    private static final ThreadLocal<ExtentTest> testThread = new ThreadLocal<>();

    static {
        ExtentSparkReporter spark = new ExtentSparkReporter("reports/extent-report.html");
        spark.config().setTheme(Theme.DARK);
        spark.config().setDocumentTitle("AutoPlayPilot Test Report");
        spark.config().setReportName("Tricentis Demo Web Shop — Selenium Cucumber");

        extent = new ExtentReports();
        extent.attachReporter(spark);
        extent.setSystemInfo("Application", "Tricentis Demo Web Shop");
        extent.setSystemInfo("Framework",   "Selenium 4 + Cucumber 7 + JUnit 5");
        log.info("ExtentReportManager initialised — report path: reports/extent-report.html");
    }

    /**
     * Creates and registers an ExtentTest for the current thread.
     *
     * @param testName display name shown in the report
     */
    public static synchronized ExtentTest createTest(String testName) {
        ExtentTest test = extent.createTest(testName);
        testThread.set(test);
        return test;
    }

    /**
     * Returns the ExtentTest bound to the current thread.
     * Throws if {@link #createTest(String)} was not called first on this thread.
     */
    public static ExtentTest getTest() {
        ExtentTest test = testThread.get();
        if (test == null) {
            throw new IllegalStateException(
                "No ExtentTest found for the current thread. " +
                "Ensure ExtentReportManager.createTest() is called in @Before.");
        }
        return test;
    }

    /**
     * Flushes all report data to disk. Call once in an @AfterAll hook.
     */
    public static synchronized void flush() {
        if (extent != null) {
            extent.flush();
            log.info("ExtentReportManager flushed — report written to reports/extent-report.html");
        }
    }

    private ExtentReportManager() {
        // utility class — no instances
    }
}
