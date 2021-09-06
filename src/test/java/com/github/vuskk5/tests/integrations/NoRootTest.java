package com.github.vuskk5.tests.integrations;

import com.codeborne.selenide.Selenide;
import com.github.vuskk5.components.NoRootComponent;
import com.github.vuskk5.extensions.ContainerExtensions;
import com.github.vuskk5.support.Find;
import lombok.experimental.ExtensionMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selectors.byClassName;
import static com.github.vuskk5.WebPage.$;
import static org.assertj.core.api.Assertions.assertThat;

@ExtensionMethod(ContainerExtensions.class)
public class NoRootTest extends IntegrationTest {
    @BeforeClass
    void openTestPage() {
        openFile("page_with_single_component.html");
    }

    @Test
    void builderShouldBuildWithoutRoot() {
        var page = Selenide.page(MultipleComponentPage.class);
        assertThat(page.annotationComponent.label.getTagName()).isEqualTo("label");
        assertThat(page.annotationComponent.label.text()).isEqualTo("Text");
        assertThat(page.annotationComponent.input.getTagName()).isEqualTo("input");
        assertThat(page.annotationComponent.input.val()).isEqualTo("");
    }

    @Test
    void factoryShouldBuildWithoutRoot() {
        var page = Selenide.page(MultipleComponentPage.class);
        assertThat(page.builderComponent.label.getTagName()).isEqualTo("label");
        assertThat(page.builderComponent.label.text()).isEqualTo("Text");
        assertThat(page.builderComponent.input.getTagName()).isEqualTo("input");
        assertThat(page.builderComponent.input.val()).isEqualTo("");
    }

    @Test
    void factoryAndBuilderShouldProvideTheSame() {
        var page = Selenide.page(MultipleComponentPage.class);
        assertThat(page.annotationComponent.input.toString()).isEqualTo(page.builderComponent.input.toString());
        assertThat(page.annotationComponent.label.toString()).isEqualTo(page.builderComponent.label.toString());
        assertThat(page.annotationComponent.self().innerHtml()).isEqualTo(page.builderComponent.self().innerHtml());
    }

    private static class MultipleComponentPage {
        @Find(className = "head")
        NoRootComponent annotationComponent;

        NoRootComponent builderComponent = $(byClassName("head"), NoRootComponent.class);
    }
}
