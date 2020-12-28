package de.liferando.api.tests;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import de.liferando.api.responses.CoopResponse;
import de.liferando.api.services.ChickenService;
import io.qameta.allure.Description;
import java.util.Objects;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestChickenService extends BaseTest {

    ChickenService chickenService;

    @BeforeClass
    public void init() {
        // better to init with "clear user" before each test to make api call independent and stateless
        chickenService  = new ChickenService();
    }

    @Test
    @Description("User is able to feed chickens")
    public void userFeedChickenFirstTime() {
        CoopResponse response = chickenService.feedChicken();
        assertThat(response.getAction(), is("chickens-feed"));
        assertThat(response.getSuccess(), is(Boolean.TRUE));
        assertThat(response.getMessage(), is(testDataSpecification.happyChickenMessage()));
    }

    @Test
    @Description("Warning when chickens are fed too much")
    public void userFeedChickenTwoTimes() {
        chickenService.feedChicken();
        CoopResponse response = chickenService.feedChicken();
        assertThat(response.getAction(), is("chickens-feed"));
        assertThat(response.getSuccess(), is(Boolean.TRUE));
        assertThat(response.getMessage(), is(testDataSpecification.chickenIsFullMessage()));
    }

    @Test
    @Description("User is able to collect eggs")
    public void testCollectEggs() {
        CoopResponse response = chickenService.collectEggs();
        assertThat(response.getAction(), is("eggs-collect"));
        assertThat(response.getSuccess(), is(Boolean.TRUE));
        assertThat(response.getData(), greaterThan(0));
        assertThat(response.getMessage(), is(testDataSpecification.collectedEggsMessage(response.getData())));
    }

    @Test
    @Description("Verify daily collected eggs counter")
    public void testCollectEggsAndCount() throws InterruptedException {
        Integer initialEggsNumber = chickenService.countCollectedEggs().getData();
        Integer collectedEggs = chickenService.collectEggs().getData();
        int requestLimit = 5;
        // might be avoided if we have possibility to reset the state
        while (Objects.isNull(collectedEggs) && requestLimit > 0) {
            CoopResponse coopResponse = chickenService.collectEggs();
            collectedEggs = coopResponse.getData();
            requestLimit--;
            Thread.sleep(5000);
        }
        int expectedSumOfEggs = initialEggsNumber + collectedEggs;
        CoopResponse response = chickenService.countCollectedEggs();
        assertThat(response.getAction(), is("eggs-count"));
        assertThat(response.getSuccess(), is(Boolean.TRUE));
        assertThat(response.getData(), is(expectedSumOfEggs));
        assertThat(response.getMessage(), is(testDataSpecification.dailyCollectedEggsMessage(expectedSumOfEggs)));
    }

    @Test
    @Description("User is not able to collect eggs too many times")
    public void testFailToCollectEggs() {
        chickenService.collectEggs();
        CoopResponse response = chickenService.collectEggs();
        assertThat(response.getAction(), is("eggs-collect"));
        assertThat(response.getSuccess(), is(Boolean.TRUE));
        assertThat(response.getMessage(), is(testDataSpecification.failToCollectEggsMessage()));
        assertThat(response.getData(), is(nullValue()));
    }

}
