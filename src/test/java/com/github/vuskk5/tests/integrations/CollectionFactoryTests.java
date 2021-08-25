package com.github.vuskk5.tests.integrations;

import com.codeborne.selenide.Selenide;
import com.github.vuskk5.components.TextComponent;
import com.github.vuskk5.support.Find;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CollectionFactoryTests extends IntegrationTest {
    @BeforeClass
    void openTestPage() {
        openFile("page_with_multiple_components.html");
    }

    private static class TestPage {
        @Find(tagName = "div")
        List<TextComponent> rootList;

        @Find(tagName = "input")
        List<TextComponent> innerList;
    }

    @Test
    void factoryShouldBuildWithRootElementLocator() {
        var page = Selenide.page(TestPage.class);
        assertThat(page.rootList).hasSize(3).doesNotContainNull();

        assertThat(page.rootList.get(0).input).hasToString(page.innerList.get(0).input.toString());
    }

    @Test
    void factoryShouldBuildWithInnerElementLocator() {
        var page = Selenide.page(TestPage.class);
        assertThat(page.innerList).hasSize(3).doesNotContainNull();
    }

    @Test
    void factoryShouldHaveSameValuesWithInnerAndRoot() {
        var page = Selenide.page(TestPage.class);
        assertThat(page.rootList.get(0).input).hasToString(page.innerList.get(0).input.toString());
        assertThat(page.rootList.get(0).label).hasToString(page.innerList.get(0).label.toString());
    }

    @Test
    void factoryShouldFindInnerElements() {
        var page = Selenide.page(TestPage.class);
        assertThat(page.rootList.get(0).label.text()).isEqualTo("Text 1");
        assertThat(page.rootList.get(1).label.text()).isEqualTo("Text 2");
        assertThat(page.rootList.get(2).label.text()).isEqualTo("Text 3");
    }
}
