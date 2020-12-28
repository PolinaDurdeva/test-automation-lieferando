package de.liferando.ui.configurations;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.LoadPolicy;
import org.aeonbits.owner.Config.LoadType;
import org.aeonbits.owner.Config.Sources;


@LoadPolicy(LoadType.MERGE)
@Sources({"classpath:uitest.properties"})
public interface TestConfiguration extends Config {

    @DefaultValue("true")
    @Key("test.localDriver")
    Boolean useLocalDriver();

    @DefaultValue("false")
    @Key("selenoid.VNC")
    Boolean enableVNC();

    @DefaultValue("false")
    @Key("selenoid.video")
    Boolean enableVideo();

    @DefaultValue("chrome")
    @Key("selenide.browser")
    String browser();

    @Key("app.url")
    String baseUrl();

    @Key("selenide.remote")
    String remoteHost();

    @DefaultValue("4000")
    @Key("selenide.pageLoadTimeout")
    int pageLoadTimeout();

    @DefaultValue("2000")
    @Key("selenide.timeout")
    int timeout();

    @DefaultValue("target/reports")
    @Key("selenide.reportFolder")
    String reportFolder();
}

