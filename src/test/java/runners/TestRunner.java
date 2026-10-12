package runners;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.*;

/**
 * TestRunner — JUnit Platform Suite entry point for Cucumber.
 *
 * Features  : src/test/resources/features
 * Glue      : stepdefinitions, hooks
 * Plugin    : Extent HTML report + JSON for CI integration
 * Tags      : Override at runtime with -Dcucumber.filter.tags="@tc001"
 *
 * Run all:           mvn test
 * Run by tag:        mvn test -Dcucumber.filter.tags="@regression"
 * Run headed:        mvn test -DHEADLESS=false
 * Run in CI:         mvn test -DBASE_URL=$BASE_URL -DHEADLESS=true
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME,   value = "stepdefinitions,hooks")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME,
    value = "pretty,"
          + "html:reports/cucumber-report.html,"
          + "json:reports/cucumber-report.json")
@ConfigurationParameter(key = PARALLEL_EXECUTION_ENABLED_PROPERTY_NAME, value = "false")
public class TestRunner {
    // JUnit Platform Suite runner — no body needed
}
