package com.github.vuskk5.tests.integrations;

import com.codeborne.selenide.Selenide;
import com.github.vuskk5.components.TextComponent;
import com.github.vuskk5.support.Find;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ElementFactoryTests extends IntegrationTest {
    @BeforeClass
    void openTestPage() {
        openFile("page_with_multiple_components.html");
    }

    private static class TestPage {
        @Find(xpath = "//div[1]")
        TextComponent rootComponent;

        @Find(text = "Text 1")
        TextComponent innerComponent;
    }

    @Test
    void factoryShouldBuildWithRootElementLocator() {
        var page = Selenide.page(TestPage.class);
        assertThat(page.rootComponent.input.getTagName()).isEqualTo("input");
        assertThat(page.rootComponent.label.getTagName()).isEqualTo("label");
        assertThat(page.rootComponent.label.text()).isEqualTo("Text 1");
    }

    @Test
    void factoryShouldBuildWithInnerElementLocator() {
        var page = Selenide.page(TestPage.class);
        assertThat(page.innerComponent.input.getTagName()).isEqualTo("input");
        assertThat(page.innerComponent.label.getTagName()).isEqualTo("label");
        assertThat(page.innerComponent.label.text()).isEqualTo("Text 1");
    }
}
