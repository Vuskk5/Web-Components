package com.github.vuskk5.support;

import com.github.vuskk5.support.internal.EnhancedFindByBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactoryFinder;
import org.openqa.selenium.support.pagefactory.ByChained;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

/**
 * @see FindBys
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
@PageFactoryFinder(Finds.FindByBuilder.class)
public @interface Finds {
    Find[] value();

    class FindByBuilder extends EnhancedFindByBuilder {
        public By buildIt(Object annotation, Field field) {
            Finds findBys = (Finds) annotation;
            assertValidFinds(findBys);

            Find[] findByArray = findBys.value();
            By[] byArray = new By[findByArray.length];
            for (int i = 0; i < findByArray.length; i++) {
                byArray[i] = buildByFromFindBy(findByArray[i]);
            }

            return new ByChained(byArray);
        }
    }
}
