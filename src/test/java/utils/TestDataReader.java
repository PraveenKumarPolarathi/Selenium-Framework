package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.util.Locale;

/**
 * TestDataReader — loads test data from src/test/resources/test-data/credentials.json.
 *
 * An environment variable override takes precedence over the JSON value, e.g.
 * LOGIN_PASSWORD overrides login.password. This lets CI inject real secrets without
 * touching the checked-in JSON, which only holds disposable demo-site test data.
 *
 * Usage:
 *   TestDataReader.get("login.email")      → returns value at JSON path login → email
 *   TestDataReader.get("checkout.country") → returns value at JSON path checkout → country
 */
public class TestDataReader {

    private static final Logger log  = LogManager.getLogger(TestDataReader.class);
    private static JsonNode     root = null;

    static {
        try (InputStream is = TestDataReader.class
                .getClassLoader()
                .getResourceAsStream("test-data/credentials.json")) {
            if (is == null) {
                throw new IllegalStateException(
                    "credentials.json not found on classpath at test-data/credentials.json");
            }
            root = new ObjectMapper().readTree(is);
            log.info("TestDataReader: credentials.json loaded successfully");
        } catch (Exception e) {
            log.error("TestDataReader: failed to load credentials.json", e);
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * Retrieves a string value from credentials.json using a dot-separated path.
     *
     * @param dotPath e.g. "login.email", "checkout.address1"
     * @return the string value, or throws if the path does not exist
     */
    public static String get(String dotPath) {
        String envKey = dotPath.toUpperCase(Locale.ROOT).replace('.', '_');
        String envVal = System.getenv(envKey);
        if (envVal != null && !envVal.isBlank()) {
            log.debug("TestDataReader: using environment override {} for {}", envKey, dotPath);
            return envVal;
        }

        String[] parts = dotPath.split("\\.");
        JsonNode node  = root;
        for (String part : parts) {
            if (node == null || !node.has(part)) {
                throw new IllegalArgumentException(
                    "Test data path not found in credentials.json: " + dotPath);
            }
            node = node.get(part);
        }
        if (node == null || node.isNull()) {
            throw new IllegalArgumentException(
                "Test data value is null for path: " + dotPath);
        }
        return node.asText();
    }

    private TestDataReader() {
        // utility class — no instances
    }
}
