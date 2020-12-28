package de.liferando.ui.components;

import de.liferando.ui.components.filters.Filter;
import de.liferando.ui.pages.SearchResultPage;
import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FilterComponent {

    private static final Logger LOGGER = LogManager.getLogger(FilterComponent.class);

    @Step("Filter search by {filter}")
    public SearchResultPage filterSearch(Filter filter) {
        LOGGER.info("Filter restaurants by {}", filter);
        filter.applyFilter();
        return new SearchResultPage();
    }
}
