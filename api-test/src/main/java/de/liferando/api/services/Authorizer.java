package de.liferando.api.services;

import io.restassured.specification.RequestSpecification;

public interface Authorizer {

    RequestSpecification authorize();

}
