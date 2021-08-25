package com.github.vuskk5.pages;

import com.github.vuskk5.components.VideoComponent;
import com.github.vuskk5.support.Find;

import java.util.List;

public class YouTubePage {
    @Find(css = "#thumbnail")
    public List<VideoComponent> cards;
}
