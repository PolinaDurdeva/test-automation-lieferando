package de.liferando.ui.tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import de.liferando.ui.components.filters.CategoryFilter;
import de.liferando.ui.components.filters.DeliveryCostFilter;
import de.liferando.ui.components.FilterComponent;
import de.liferando.ui.conditions.PriceCondition;
import de.liferando.ui.pages.MainPage;
import io.qameta.allure.Description;
import org.testng.annotations.Test;

public class SearchRestaurantTest extends BaseTest {

    private final String address = "Ohlauer Straße 43, Berlin";

    @Test
    @Description("User can filter restaurants by delivery cost")
    public void userCanFilterByDeliveryCost() {
        MainPage.open().searchFor(address);
        ElementsCollection costItems = at(FilterComponent.class)
                .filterSearch(DeliveryCostFilter.EURO_OR_LESS)
                .getDeliveryCostIcons().snapshot();
        costItems.forEach(el -> el.shouldHave(PriceCondition.priceLessThan(1.00)));
    }

    @Test
    @Description("User can filter restaurants by kitchen")
    public void userCanFilterByCategory() {
        MainPage.open().searchFor(address);
        ElementsCollection kitchen = at(FilterComponent.class)
                .filterSearch(CategoryFilter.ITALIAN)
                .getRestaurantCategories().snapshot();
        kitchen.forEach(el -> el.shouldHave(Condition.text("Italian")));
    }
}
