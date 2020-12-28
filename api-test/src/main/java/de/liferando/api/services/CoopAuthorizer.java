package de.liferando.api.services;

import de.liferando.api.configurations.TestConfiguration;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.cookie.CookieFilter;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CoopAuthorizer implements Authorizer {

    private static final Logger LOGGER = LogManager.getLogger(CoopAuthorizer.class);

    private final TestConfiguration configuration = ConfigFactory.create(TestConfiguration.class, System.getProperties());
    private final CookieFilter cookieFilter = new CookieFilter();

    public CoopAuthorizer() {

    }

    @Override
    public RequestSpecification authorize() {
        LOGGER.info("Authorize for coop API...");
        RequestSpecification spec = new RequestSpecBuilder()
                .log(LogDetail.URI)
                .addFilter(new AllureRestAssured())
                .addFilter(cookieFilter)
                .build();
        setCookies(spec);
        login(spec);
        initAuthorizationData(spec);
        LOGGER.info("Request specification is ready");
        return spec;
    }

    private void initAuthorizationData(RequestSpecification specification) {
        Response authResponse = authorize(EnumSet.copyOf(Arrays.stream(Scope.values()).collect(Collectors.toSet())));
        try {
            String locationHeader = authResponse.getHeader("Location");
            String code = locationHeader.split("code=")[1].replace("&", "");
            Response tokenResponse = getToken(code, specification);
            String token = tokenResponse.jsonPath().getString("access_token");
            specification.header("Authorization", "Bearer " + token)
                    .basePath("/api/" + configuration.userId());
        } catch (Exception e) {
            LOGGER.error("Error during authorization code extraction!");
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private void setCookies(RequestSpecification specification) {
        LOGGER.info("Perform request to set initial cookies");
        RestAssured.given()
                .spec(specification)
                .filter(cookieFilter)
                .expect().statusCode(200)
                .when().get(Endpoint.LOGIN);
    }

    private void login(RequestSpecification specification) {
        LOGGER.info("Perform request to login with credentials");
        RestAssured.given()
                .spec(specification)
                .filter(cookieFilter)
                .multiPart("_username", configuration.userEmail())
                .multiPart("_password", configuration.userPassword())
                .expect().statusCode(302)
                .when().post(Endpoint.CHECK_LOGIN);
    }

    private Response authorize(Set<Scope> scopes) {
        List<String> permissions = scopes.stream().map(Scope::getProfile).collect(Collectors.toList());
        String scope = String.join(" ", permissions);
        LOGGER.info("Perform request to autorize with permissions {}", scope);
        return RestAssured.given()
                .filter(cookieFilter)
                .redirects().follow(false)
                .queryParam("client_id", configuration.clientId())
                .queryParam("response_type", "code")
                .queryParam("redirect_uri", configuration.redirectUrl())
                .queryParam("scope", scope)
                .queryParam("authorize", 1)
                .expect().statusCode(302)
                .when()
                .get(Endpoint.AUTHORIZATION);
    }

    private Response getToken(String code, RequestSpecification specification) {
        LOGGER.info("Perform request to get token");
        return RestAssured.given().spec(specification)
                .multiPart("client_id", configuration.clientId())
                .multiPart("client_secret", configuration.secretKey())
                .multiPart("grant_type", "authorization_code")
                .multiPart("redirect_uri", configuration.redirectUrl())
                .multiPart("code", code)
                .expect().statusCode(200)
                .when().post(Endpoint.ACCESS_TOKEN);
    }

    private enum Scope {
        BARN("barn-unlock"),
        TOILETSEAT("toiletseat-down"),
        CHICKEN_FEED("chickens-feed"),
        EGGS_COLLECT("eggs-collect"),
        EGGS_COUNT("eggs-count"),
        PROFILE("profile");

        private final String profile;

        Scope(String profile) {
            this.profile = profile;
        }

        public String getProfile() {
            return profile;
        }
    }
}
