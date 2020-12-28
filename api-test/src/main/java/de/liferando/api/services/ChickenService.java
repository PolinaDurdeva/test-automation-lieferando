package de.liferando.api.services;

import de.liferando.api.responses.CoopResponse;
import io.qameta.allure.Step;
import io.restassured.RestAssured;

public class ChickenService extends ApiService {

    public ChickenService() {

    }

    @Step("Feed chicken")
    public CoopResponse feedChicken() {
        return RestAssured.given()
                .spec(specification)
                .post(Endpoint.FEED_CHICKEN)
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(CoopResponse.class);
    }

    @Step("Collect eggs")
    public CoopResponse collectEggs() {
        return RestAssured.given()
                .spec(specification)
                .post(Endpoint.COLLECT_EGGS)
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(CoopResponse.class);
    }

    @Step("Count collected eggs")
    public CoopResponse countCollectedEggs() {
        return RestAssured.given()
                .spec(specification)
                .post(Endpoint.COUNT_EGGS)
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(CoopResponse.class);
    }

}
