package de.liferando.ui.pages;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

import org.openqa.selenium.Cookie;

public interface Page {

    static void dismissCookie() {
        getWebDriver().manage().addCookie(new Cookie("cookieConsent", "full"));
    }

}
