package com.github.vuskk5.tests.youtube;

import com.codeborne.selenide.Selenide;
import com.github.vuskk5.components.VideoComponent;
import com.github.vuskk5.pages.YouTubePage;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import static com.github.vuskk5.WebPage.$$;


public class YouTubeTest {
    @Test
    void testYoutubeVideoCompsUsingPageFactory() {
        Selenide.open("https://www.youtube.com/");

        YouTubePage page = Selenide.page(YouTubePage.class);
        var cards = page.cards;

        for (VideoComponent card : cards) {
            System.out.println(card.getLink());
        }
    }

    @Test
    void testYoutubeVideoCompsUsingMethods() {
        Selenide.open("https://www.youtube.com/");

        var cards = $$(By.cssSelector("#thumbnail"), VideoComponent.class);

        for (VideoComponent card : cards) {
            System.out.println(card.getLink());
        }
    }

}
