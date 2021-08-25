package com.github.vuskk5.tests.integrations;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.github.vuskk5.WebPage;
import com.github.vuskk5.components.TextComponent;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ElementBuilderTests extends IntegrationTest {
    @BeforeClass
    void openTestPage() {
        Configuration.headless = true;
        openFile("page_with_single_component.html");
    }

    static class TestPage extends WebPage {
        public TextComponent compFromCssSelector = $(".head", TextComponent.class);
        public TextComponent compFromSeleniumSelector = $(By.className("head"), TextComponent.class);
        public TextComponent compFromXPathSelector = $(By.xpath("//*[@class = 'head']"), TextComponent.class);
        public TextComponent compFromRootLocator = $(".head", TextComponent.class);
        public TextComponent compFromInnerLocator = $("label", TextComponent.class);
    }

    @Test
    void buildComponentFromExistingInnerElement() {
        var page = Selenide.page(TestPage.class);
        var component = page.compFromInnerLocator;
        assertThat(component.label.getTagName()).isEqualTo("label");
        assertThat(component.label.text()).isEqualTo("Text");
        assertThat(component.input.getTagName()).isEqualTo("input");
        assertThat(component.input.val()).isEqualTo("");
    }

    @Test
    void buildComponentFromExistingRootElement() {
        var page = Selenide.page(TestPage.class);
        var component = page.compFromRootLocator;
        assertThat(component.label.getTagName()).isEqualTo("label");
        assertThat(component.label.text()).isEqualTo("Text");
        assertThat(component.input.getTagName()).isEqualTo("input");
        assertThat(component.input.val()).isEqualTo("");
    }

    @Test
    void buildComponentFromCssSelector() {
        var page = Selenide.page(TestPage.class);
        var component = page.compFromCssSelector;
        assertThat(component.label.getTagName()).isEqualTo("label");
        assertThat(component.label.text()).isEqualTo("Text");
        assertThat(component.input.getTagName()).isEqualTo("input");
        assertThat(component.input.val()).isEqualTo("");
    }

    @Test
    void buildComponentFromXPath() {
        var page = Selenide.page(TestPage.class);
        var component = page.compFromXPathSelector;
        assertThat(component.label.getTagName()).isEqualTo("label");
        assertThat(component.label.text()).isEqualTo("Text");
        assertThat(component.input.getTagName()).isEqualTo("input");
        assertThat(component.input.val()).isEqualTo("");
    }

    @Test
    void buildComponentFromSeleniumSelector() {
        var page = Selenide.page(TestPage.class);
        var component = page.compFromSeleniumSelector;
        assertThat(component.label.getTagName()).isEqualTo("label");
        assertThat(component.label.text()).isEqualTo("Text");
        assertThat(component.input.getTagName()).isEqualTo("input");
        assertThat(component.input.val()).isEqualTo("");
    }
}
