package com.github.vuskk5.components;

import com.codeborne.selenide.SelenideElement;
import com.github.vuskk5.WebComponent;
import com.github.vuskk5.support.Find;
import com.github.vuskk5.support.Root;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Root(tagName = "ytd-rich-item-renderer")
public class VideoComponent extends WebComponent {
    @Find(css = "a#thumbnail")
    private SelenideElement thumbnail;

    public String getLink() {
        return thumbnail.getAttribute("href");
    }
}
