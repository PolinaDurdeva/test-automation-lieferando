package de.liferando.ui.pages;

import static com.codeborne.selenide.Selenide.$$;

import com.codeborne.selenide.ElementsCollection;

public class SearchResultPage implements Page {

    private final ElementsCollection restaurantList = $$("#irestaurantlist .restaurant:not(.restaurant-hide)");
    private final ElementsCollection deliveryCostIcons = $$(".restaurant[data-id]:not(.restaurant-hide) .delivery-cost");
    private final ElementsCollection kitchenCategoryIcons = $$(".restaurant[data-id]:not(.restaurant-hide) .kitchens");

    public ElementsCollection getDeliveryCostIcons() {
        return deliveryCostIcons;
    }

    public ElementsCollection getRestaurantCategories() {
        return kitchenCategoryIcons;
    }

}
