package com.github.vuskk5.components;

import com.codeborne.selenide.SelenideElement;
import com.github.vuskk5.WebComponent;
import com.github.vuskk5.support.Find;
import com.github.vuskk5.support.Root;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Root(className = "head")
public class TextComponent extends WebComponent {
    @Find(className = "label")
    public SelenideElement label;

    @Find(tagName = "input")
    public SelenideElement input;
}
