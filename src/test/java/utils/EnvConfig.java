package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * EnvConfig — single source of truth for environment-driven configuration.
 * Avoids duplicating fallback defaults across Hooks / step definitions.
 */
public final class EnvConfig {

    private static final Logger log = LogManager.getLogger(EnvConfig.class);
    private static final String DEFAULT_BASE_URL = "https://demowebshop.tricentis.com/";

    private EnvConfig() {
        // utility class — no instances
    }

    /**
     * Resolves BASE_URL from the system property, always returned with a trailing slash.
     * Falls back to the public demo shop URL and logs a warning so a missing/typo'd
     * -DBASE_URL in CI is visible rather than silently masked.
     */
    public static String baseUrl() {
        String baseUrl = System.getProperty("BASE_URL");
        if (baseUrl == null || baseUrl.isBlank()) {
            log.warn("BASE_URL system property not set — falling back to default: {}", DEFAULT_BASE_URL);
            baseUrl = DEFAULT_BASE_URL;
        }
        return baseUrl.endsWith("/") ? baseUrl : baseUrl + "/";
    }
}
