package de.liferando.api.listeners;

import io.restassured.RestAssured;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener, ISuiteListener {

    private static final Logger LOGGER = LogManager.getLogger(TestListener.class);
    private static final String ALLURE_ENV_PATH = "target/allure-results/environment.properties";

    @Override
    public void onStart(ISuite suite) {
        LOGGER.info("Start Suit ==>" + suite.getName());
    }

    @Override
    public void onFinish(ISuite suite) {
        LOGGER.info("Finish Suit ==> " + suite.getName());
        attachEnvironmentConfiguration();
    }

    @Override
    public void onStart(ITestContext context) {
        LOGGER.info("Start Test ==> " + context.getSuite().getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        LOGGER.info("Finish Test ==> " + context.getName());
        var passedTestNames = context.getPassedTests().getAllResults().stream().map(ITestResult::getName).collect(Collectors.toSet());
        var failedTestNames = context.getFailedTests().getAllResults().stream().map(ITestResult::getName).collect(Collectors.toSet());
        var skippedTestNames = context.getSkippedTests().getAllResults().stream().map(ITestResult::getName).collect(Collectors.toSet());
        LOGGER.info("Passes tests: " + passedTestNames);
        LOGGER.info("Failed tests: " + failedTestNames);
        LOGGER.info("Skipped tests: " + skippedTestNames);
    }

    @Override
    public void onTestStart(ITestResult result) {
        ThreadContext.push(result.getName());
        LOGGER.info("New Test Started: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LOGGER.info("Test Successfully Finished: " + result.getName());
        ThreadContext.clearStack();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        LOGGER.info("Test Failed: " + result.getName());
        LOGGER.error(result.getThrowable());
        ThreadContext.clearStack();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        LOGGER.info("Test Skipped: " + result.getName());
        ThreadContext.clearStack();
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
    }

    private void attachEnvironmentConfiguration() {
        Properties prop = new Properties();
        prop.setProperty("base_url", RestAssured.baseURI);
        try (FileWriter writer = new FileWriter(ALLURE_ENV_PATH)) {
            prop.store(writer, "store properties in allure directory");
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("Failed to write properties for allure report");
        }
    }

}
