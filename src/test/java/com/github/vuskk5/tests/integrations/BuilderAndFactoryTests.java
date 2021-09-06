package com.github.vuskk5.tests.integrations;

import com.codeborne.selenide.Selenide;
import com.github.vuskk5.components.TextComponent;
import com.github.vuskk5.support.Find;
import org.assertj.core.api.Assertions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selectors.byText;
import static com.github.vuskk5.WebPage.$;

public class BuilderAndFactoryTests extends IntegrationTest {
    @BeforeClass
    void openTestPage() {
        openFile("page_with_multiple_components.html");
    }

    @Test
    void testBuilderAndFactoryProvideSameResults() {
        var page = Selenide.page(MultipleComponentPage.class);
        Assertions.assertThat(page.annotationComponent.input.toString()).isEqualTo(page.builderComponent.input.toString());
        Assertions.assertThat(page.annotationComponent.label.toString()).isEqualTo(page.builderComponent.label.toString());
    }

    private static class MultipleComponentPage {
        @Find(text = "Text 1")
        TextComponent annotationComponent;

        TextComponent builderComponent = $(byText("Text 1"), TextComponent.class);
    }
}
