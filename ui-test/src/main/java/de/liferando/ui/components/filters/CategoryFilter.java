package de.liferando.ui.components.filters;

import static com.codeborne.selenide.Selenide.$;

public enum CategoryFilter implements Filter{

    ITALIAN("a[data-value='21']");

    private final String locator;

    CategoryFilter(String locator) {
        this.locator = locator;
    }

    @Override
    public void applyFilter() {
        $(".filter-kitchen").$(locator).click();
    }
}
