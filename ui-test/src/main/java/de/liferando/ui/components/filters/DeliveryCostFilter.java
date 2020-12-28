package de.liferando.ui.components.filters;

import static com.codeborne.selenide.Selenide.$;

public enum DeliveryCostFilter implements Filter {

    NO_PREFERENCE("a[data-value=reset]"),
    FREE("a[data-value='[0,0]']"),
    EURO_OR_LESS("a[data-value='[0,1]']"),
    TWO_50EURO_OR_LESS("a[data-value='[0,2]']");

    private final String locator;

    DeliveryCostFilter(String locator) {
        this.locator = locator;
    }

    @Override
    public void applyFilter() {
        $(".deliverycosts-filter").$(locator).click();
    }
}
