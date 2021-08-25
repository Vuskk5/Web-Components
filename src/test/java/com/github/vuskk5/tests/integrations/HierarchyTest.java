package com.github.vuskk5.tests.integrations;

import com.codeborne.selenide.Selenide;
import com.github.vuskk5.WebPage;
import com.github.vuskk5.components.HierarchyComponent;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static com.codeborne.selenide.Condition.text;
import static org.assertj.core.api.Assertions.assertThat;

public class HierarchyTest extends IntegrationTest {
    @BeforeClass
    void openTestPage() {
        openFile("page_with_hierarchical_components.html");
    }

    private static class TestPage {
        List<HierarchyComponent> components = WebPage.$$(By.className("hierarchical"), HierarchyComponent.class);
    }

    @Test
    void hierarchicalShouldBeProxiedCorrectly() {
        var page = Selenide.page(TestPage.class);
        assertThat(page.components).hasSize(2);
        assertThat(page.components.get(0).components).hasSize(2);

        page.components.get(0).components.get(0).label.shouldHave(text("Text 1"));
        page.components.get(1).components.get(1).label.shouldHave(text("Text 4"));
    }
}
