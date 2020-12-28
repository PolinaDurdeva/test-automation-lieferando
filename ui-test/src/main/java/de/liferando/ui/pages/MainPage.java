package de.liferando.ui.pages;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainPage implements Page {

    private static final Logger LOGGER = LogManager.getLogger(MainPage.class);
    private final SelenideElement searchInput = $("input#imysearchstring");
    private final ElementsCollection suggestions = $$("#iautoCompleteDropDownContent a");

    @Step("Open home page")
    public static MainPage open(){
        LOGGER.info("Open home page");
        Selenide.open("/");
        Page.dismissCookie();
        return new MainPage();
    }
    @Step("Search by address {address}")
    public SearchResultPage searchFor(String address) {
        LOGGER.info("Search by address {}", address);
        searchInput.setValue(address);
        suggestions.findBy(Condition.matchesText(address)).click();
        return new SearchResultPage();
    }

}
