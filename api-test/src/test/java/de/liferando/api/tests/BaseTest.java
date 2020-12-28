package de.liferando.api.tests;

import de.liferando.api.configurations.TestConfiguration;
import de.liferando.api.testdata.SimpleTestDataSpecification;
import de.liferando.api.testdata.TestDataSpecification;
import io.restassured.RestAssured;
import org.aeonbits.owner.ConfigFactory;
import org.testng.annotations.BeforeSuite;

public class BaseTest {

    protected final TestDataSpecification testDataSpecification;

    public BaseTest() {
        this.testDataSpecification = new SimpleTestDataSpecification();
    }

    @BeforeSuite
    public void setup() {
        TestConfiguration cfg = ConfigFactory.create(TestConfiguration.class, System.getProperties());
        RestAssured.baseURI = cfg.apiUrl();
    }

}
