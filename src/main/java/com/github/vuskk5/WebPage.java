package com.github.vuskk5;

import com.codeborne.selenide.Driver;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.impl.ElementFinder;
import com.codeborne.selenide.impl.WebElementSource;
import com.github.vuskk5.support.Find;
import com.github.vuskk5.support.internal.WebComponentFactory;
import lombok.SneakyThrows;
import org.openqa.selenium.By;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.IntStream.range;

@ParametersAreNonnullByDefault
public class WebPage {
    @CheckReturnValue
    @Nonnull
    public static <T extends WebComponent> T $x(String xpathExpression, Class<T> containerType) {
        return $(Selectors.byXpath(xpathExpression), containerType);
    }

    @CheckReturnValue
    @Nonnull
    public static <T extends WebComponent> T $(String cssSelector, Class<T> containerType) {
        return $(Selectors.byCssSelector(cssSelector), containerType);
    }

    @CheckReturnValue
    @Nonnull
    public static <T extends WebComponent> T $(By innerOrRootLocator, Class<T> containerType) {
        return wrapElement(innerOrRootLocator, 0, containerType);
    }

    @CheckReturnValue
    @Nonnull
    public static <T extends WebComponent> List<T> $$x(String xpathExpression, Class<T> containerType) {
        return $$(Selectors.byXpath(xpathExpression), containerType);
    }

    @CheckReturnValue
    @Nonnull
    public static <T extends WebComponent> List<T> $$(String cssSelector, Class<T> containerType) {
        return $$(Selectors.byCssSelector(cssSelector), containerType);
    }

    @CheckReturnValue
    @Nonnull
    public static <T extends WebComponent> List<T> $$(By innerOrRootLocator, Class<T> containerType) {
        int size = Selenide.$$(Find.FindByBuilder.parseClassRootToLocator(innerOrRootLocator, containerType))
                           .size();

        return range(0, size).mapToObj(index -> wrapElement(innerOrRootLocator, index, containerType))
                             .collect(Collectors.toList());
    }

    /**
     * Creates a new WebComponent, the root ("self") component will be located by <code>criteria</code>
     * @param criteria This is locator will be used to locate the inner-or-self element
     * @param index Index for the <code>findElements</code> result
     */
    @CheckReturnValue
    @Nonnull
    @SneakyThrows
    public static <T extends WebComponent> T wrapElement(By criteria, int index, Class<T> componentType) {
        T component = componentType.getDeclaredConstructor().newInstance();

        Constructor<?> constructor = ElementFinder.class.getDeclaredConstructor(Driver.class, WebElementSource.class, By.class, Integer.TYPE);
        var isAccessible = constructor.canAccess(null);
        constructor.setAccessible(true);

        WebElementSource context = (WebElementSource) constructor.newInstance(WebDriverRunner.driver(),
                                                                              null,
                                                                              Find.FindByBuilder.parseClassRootToLocator(criteria, componentType),
                                                                              index);
        constructor.setAccessible(isAccessible);

        Type[] types = componentType.getGenericInterfaces();

        new WebComponentFactory().initElements(WebDriverRunner.driver(),
                                               context,
                                               component,
                                               types);

        return component;
    }
}
