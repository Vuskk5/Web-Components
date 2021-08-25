package com.github.vuskk5.extensions;

import com.codeborne.selenide.ElementsContainer;
import com.codeborne.selenide.SelenideElement;
import lombok.SneakyThrows;

import java.lang.reflect.Field;

public class ContainerExtensions {
    @SneakyThrows
    public static <T extends ElementsContainer> void self(T container, SelenideElement value) {
        Field self = ElementsContainer.class.getDeclaredField("self");
        boolean isAccessible = self.canAccess(container);

        self.setAccessible(true);
        self.set(container, value);
        self.setAccessible(isAccessible);
    }

    @SneakyThrows
    public static <T extends ElementsContainer> SelenideElement self(T container) {
        Field self = ElementsContainer.class.getDeclaredField("self");
        boolean isAccessible = self.canAccess(container);

        self.setAccessible(true);
        SelenideElement element = (SelenideElement) self.get(container);
        self.setAccessible(isAccessible);

        return element;
    }
}
