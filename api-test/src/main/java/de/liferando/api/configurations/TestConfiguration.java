package de.liferando.api.configurations;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.LoadPolicy;
import org.aeonbits.owner.Config.LoadType;
import org.aeonbits.owner.Config.Sources;

@LoadPolicy(LoadType.MERGE)
@Sources({"system:properties",
        "classpath:apitest.properties"})
public interface TestConfiguration extends Config {

    @Key("app.secret")
    String secretKey();

    @Key("app.client.id")
    String clientId();

    @Key("app.redirect.url")
    String redirectUrl();

    @Key("app.url")
    String apiUrl();

    @Key("app.user.id")
    String userId();

    @Key("user.email")
    String userEmail();

    @Key("user.password")
    String userPassword();

}
