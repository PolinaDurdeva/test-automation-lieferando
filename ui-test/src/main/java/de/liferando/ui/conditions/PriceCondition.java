package de.liferando.ui.conditions;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Driver;
import java.util.Optional;
import org.openqa.selenium.WebElement;

public class PriceCondition {

    public static Condition priceLessThan(double priceLimit) {
        return new Condition(String.format("price is less than %s", priceLimit)) {
            @Override
            public boolean apply(Driver driver, WebElement element) {
                String elementText = element.getText();
                return parsePrice(elementText).map(p -> p <= priceLimit).orElse(true);
            }
        };
    }

    private static Optional<Double> parsePrice(String text) {
        String price = text.replaceAll("[^0-9.,]", "").replace(",", ".");
        return Optional.of(price).filter(s -> !s.isEmpty()).map(Double::parseDouble);
    }
}
