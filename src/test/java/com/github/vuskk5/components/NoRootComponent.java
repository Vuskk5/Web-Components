package com.github.vuskk5.components;

import com.codeborne.selenide.SelenideElement;
import com.github.vuskk5.WebComponent;
import com.github.vuskk5.support.Find;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NoRootComponent extends WebComponent {
    @Find(className = "label")
    public SelenideElement label;

    @Find(tagName = "input")
    public SelenideElement input;
}
