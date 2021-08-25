package com.github.vuskk5.selectors;

import com.codeborne.selenide.Selectors;
import org.openqa.selenium.By;

public class Locators {
    public static By byPartialClassname(String className) {
        return new PartialClassname(className);
    }

    /**
     * Locate an element by a part of its class name.
     */
    public static class PartialClassname extends By.ByXPath {
        private final String className;

        public PartialClassname(String className) {
            super(".//*[contains(@class, '" + className + "')]");

            this.className = className;
        }

        @Override
        public String toString() {
            return "By.partialClassName: " + className;
        }
    }

    /**
     * Locate an element by it's "data-testid" attribute.
     * @param dataTestId - The attribute's value
     */
    public static By byDataTestId(String dataTestId) {
        return Selectors.byAttribute("data-testid", dataTestId);
    }
}
