package com.github.vuskk5.tests;

import com.github.vuskk5.selectors.Locators;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LocatorsTest {
    @Test
    void byPartialClassName() {
        By selector = Locators.byPartialClassname("value");
        assertThat(selector).isInstanceOf(By.ByXPath.class);
        assertThat(selector).hasToString("By.partialClassName: value");
    }

    @Test
    void byDataTestId() {
        By selector = Locators.byDataTestId("value");
        assertThat(selector).hasToString("By.cssSelector: [data-testid=\"value\"]");
    }
}
