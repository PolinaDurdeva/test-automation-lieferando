package de.liferando.ui.tests;

import static com.codeborne.selenide.WebDriverRunner.clearBrowserCache;
import static com.codeborne.selenide.WebDriverRunner.closeWebDriver;
import static com.codeborne.selenide.WebDriverRunner.source;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import de.liferando.ui.configurations.TestConfiguration;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class BaseTest {

    protected static final Logger LOGGER = LogManager.getLogger(BaseTest.class);
    private final TestConfiguration testConfiguration = ConfigFactory.create(TestConfiguration.class, System.getProperties());

    @BeforeSuite(alwaysRun = true)
    public void setup() {
        if (!testConfiguration.useLocalDriver()) {
            setupRemoteDriver();
        }
        Configuration.browser = testConfiguration.browser();
        Configuration.baseUrl = testConfiguration.baseUrl();
        Configuration.timeout = testConfiguration.timeout();
        Configuration.pageLoadTimeout = testConfiguration.pageLoadTimeout();
        Configuration.reportsFolder = testConfiguration.reportFolder();
        Configuration.fastSetValue = true;
        Configuration.startMaximized = true;
    }

    @AfterSuite
    public void teardown() {
        closeWebDriver();
    }

    @AfterMethod
    public void clearCache() {
        clearBrowserCache();
    }

    private void setupRemoteDriver() {
        LOGGER.info("Setting up remote driver {}", testConfiguration.browser());
        Configuration.remote = testConfiguration.remoteHost();
        Configuration.driverManagerEnabled = false;
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", testConfiguration.enableVNC());
        capabilities.setCapability("enableVideo", testConfiguration.enableVideo());
        Configuration.browserCapabilities = capabilities;
    }

    protected  <T> T at(Class<T> page) {
        return Selenide.page(page);
    }
}
