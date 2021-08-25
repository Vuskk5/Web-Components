package com.github.vuskk5.support.internal;

import com.codeborne.selenide.Driver;
import com.codeborne.selenide.impl.SelenidePageFactory;
import com.github.vuskk5.support.Find;
import com.github.vuskk5.support.Finds;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.List;

@ParametersAreNonnullByDefault
public class WebComponentFactory extends SelenidePageFactory {
    @Nonnull
    @Override
    protected By findSelector(Driver driver, Field field) {
        return new EnhancedAnnotations(field).buildBy();
    }

    @CheckReturnValue
    @Override
    protected boolean isDecoratableList(Field field, Type[] genericTypes, Class<?> type) {
        if (!List.class.isAssignableFrom(field.getType())) {
            return false;
        }

        Class<?> listType = getListGenericType(field, genericTypes);

        return listType != null && type.isAssignableFrom(listType) &&
               (field.getAnnotation(FindBy.class) != null || field.getAnnotation(FindBys.class) != null ||
                field.getAnnotation(Find.class) != null || field.getAnnotation(Finds.class) != null);
    }
}
