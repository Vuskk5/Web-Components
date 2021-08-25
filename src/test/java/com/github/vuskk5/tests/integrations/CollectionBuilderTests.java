package com.github.vuskk5.tests.integrations;

import com.codeborne.selenide.Selenide;
import com.github.vuskk5.WebPage;
import com.github.vuskk5.components.TextComponent;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CollectionBuilderTests extends IntegrationTest {
    @BeforeClass
    void openTestPage() {
        openFile("page_with_multiple_components.html");
    }

    static class TestPage extends WebPage {
        // All those definitions locate the same elements
        List<TextComponent> listByCssSelector = $$("div", TextComponent.class);
        List<TextComponent> listBySeleniumSelector = $$(By.tagName("div"), TextComponent.class);
        List<TextComponent> listByXPath = $$(By.xpath("//div"), TextComponent.class);
        List<TextComponent> listFromInners = $$("label", TextComponent.class);
    }

    @DataProvider(name = "Component Provider")
    Object[][] getComponents() {
        TestPage page = Selenide.page(TestPage.class);
        return new Object[][] {
                new Object[] {page.listByCssSelector},
                new Object[] {page.listBySeleniumSelector},
                new Object[] {page.listByXPath}
        };
    }

    @Test(dataProvider = "Component Provider")
    void assertListIsBuiltCorrectly(List<TextComponent> components) {
        assertThat(components).hasSize(3);

        var textField = components.get(0);

        assertThat(textField.label.getTagName()).isEqualTo("label");
        assertThat(textField.label.text()).isEqualTo("Text 1");
        assertThat(textField.input.getTagName()).isEqualTo("input");
        assertThat(textField.input.val()).isEqualTo("");
    }

    @Test
    void builderShouldBuildComponentWithInnerElements() {
        var page = Selenide.page(TestPage.class);
        assertThat(page.listFromInners.get(0).label.text()).isEqualTo("Text 1");
        assertThat(page.listFromInners.get(1).label.text()).isEqualTo("Text 2");
        assertThat(page.listFromInners.get(2).label.text()).isEqualTo("Text 3");
    }

    @Test
    void assertSameLists() {
        var page = Selenide.page(TestPage.class);
        assertThat(page.listByCssSelector).hasSize(3);
        assertThat(page.listBySeleniumSelector).hasSize(3);
        assertThat(page.listByXPath).hasSize(3);
        assertThat(page.listFromInners).hasSize(3);
    }
}
