package com.github.vuskk5.support;

import com.github.vuskk5.WebComponent;
import com.github.vuskk5.support.internal.EnhancedFindByBuilder;
import lombok.NonNull;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactoryFinder;
import org.openqa.selenium.support.pagefactory.ByChained;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import static com.codeborne.selenide.Selectors.byXpath;

/**
 * <p>An alternative to Selenium's {@link FindBy}.
 * <p>This class allows adding / customizing locating mechanisms, such as {@link #text()}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
@PageFactoryFinder(Find.FindByBuilder.class)
public @interface Find {
    /**
     * Finds elements by 'id' attribute
     */
    String id() default "";

    /**
     * Finds elements by 'name' attribute
     */
    String name() default "";

    /**
     * Finds elements bu one 'class' attribute value
     */
    String className() default "";

    /**
     * Finds elements matching the given CSS Selector
     */
    String css() default "";

    /**
     * Finds elements with the given tag name
     */
    String tagName() default "";

    /**
     * Finds links with matching text values
     */
    String linkText() default "";

    /**
     * Finds links containing the given text values
     */
    String partialLinkText() default "";

    /**
     * Finds elements whose 'attribute' matches the given 'value'
     */
    String attribute() default "";

    /**
     * Finds elements by 'value' attribute
     */
    String attributeValue() default "";

    /**
     * Finds elements matching the given XPath
     */
    String xpath() default "";

    /**
     * Finds elements containing the given text
     */
    String text() default "";

    /**
     * Finds elements matching the given text
     */
    String exactText() default "";

    /**
     * Finds elements matching the given title
     */
    String title() default "";

    /**
     * Finds elements containing the given class-name
     */
    String partialClassName() default "";

    /**
     * Finds elements matching the given title
     */
    String dataTestId() default "";

    class FindByBuilder extends EnhancedFindByBuilder {
        /**
         * <p>Create the {@link By} locator from the given annotation.
         * <p>This method does special locator-building for {@link WebComponent}s.
         *
         * @param annotation The annotation to parse
         * @param field      The annotation's respective field
         * @return an element locator
         */
        public By buildIt(Object annotation, Field field) {
            Find findBy = (Find) annotation;
            assertValidFind(findBy);

            // Build a normal locator
            By locator = buildByFromShortFindBy(findBy);

            // Get the field's type (Or the List's generic type)
            Class<?> type = field.getType();
            if (List.class.isAssignableFrom(type)) {
                type = getListGenericType(field);

                // If the list does not have a generic type
                if (type == null) {
                    return locator;
                }
            }

            if (WebComponent.class.isAssignableFrom(type)) {
                return new ByChained(locator, parseClassRootToLocator(type));
            }

            return locator;
        }

        public static By parseClassRootToLocator(@NonNull Class<?> annotatedType) {
            Root rootLocator = annotatedType.getAnnotation(Root.class);

            if (rootLocator == null) {
                throw new IllegalStateException(annotatedType + " must be annotated with " + Root.class.getSimpleName());
            }

            if (!"".equals(rootLocator.tagName()))
                return byXpath("ancestor-or-self::" + rootLocator.tagName());

            if (!"".equals(rootLocator.className()))
                return byXpath("ancestor-or-self::*[contains(concat(' ', normalize-space(@class), ' '), ' " + rootLocator.className() + " ')][1]");

            if (!"".equals(rootLocator.partialClassName()))
                return byXpath("ancestor-or-self::*[contains(@class, '" + rootLocator.partialClassName() + "')]");

            throw new IllegalArgumentException(Root.class.getSimpleName() + " must be passed exactly 1 parameter");
        }

        @CheckReturnValue
        @Nullable
        private Class<?> getListGenericType(Field field) {
            Type genericType = field.getGenericType();
            if (!(genericType instanceof ParameterizedType)) return null;

            return (Class<?>) ((ParameterizedType) genericType).getActualTypeArguments()[0];
        }
    }
}
