package com.github.vuskk5.components;

import com.codeborne.selenide.SelenideElement;
import com.github.vuskk5.WebComponent;
import com.github.vuskk5.support.Find;
import com.github.vuskk5.support.Root;

import java.util.List;

@Root(className = "hierarchical")
public class HierarchyComponent extends WebComponent {
    @Find(className = "inner")
    public List<InnerComponent> components;

    @Root(className = "inner")
    public static class InnerComponent extends WebComponent {
        @Find(tagName = "label")
        public SelenideElement label;

        @Find(tagName = "input")
        public SelenideElement input;
    }
}
