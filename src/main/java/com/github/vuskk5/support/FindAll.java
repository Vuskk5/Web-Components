package com.github.vuskk5.support;

import com.github.vuskk5.support.internal.EnhancedFindByBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactoryFinder;
import org.openqa.selenium.support.pagefactory.ByAll;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

/**
 * Used to mark a field on a Page Object to indicate that lookup should use a series of @Find tags
 * It will then search for all elements that match any of the Find criteria. Note that elements
 * are not guaranteed to be in document order.
 *
 * It can be used on a types as well, but will not be processed by default.
 *
 * Eg:
 *
 * <pre class="code">
 * &#64;FindAll({ &#64;Find(id = "foo"),
 *            &#64;Find(className = "bar") })
 * </pre>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
@PageFactoryFinder(FindAll.FindByBuilder.class)
public @interface FindAll {
    Find[] value();

    class FindByBuilder extends EnhancedFindByBuilder {
        public By buildIt(Object annotation, Field field) {
            FindAll findBys = (FindAll) annotation;
            assertValidFindAll(findBys);

            Find[] findByArray = findBys.value();
            By[] byArray = new By[findByArray.length];
            for (int i = 0; i < findByArray.length; i++) {
                byArray[i] = buildByFromFindBy(findByArray[i]);
            }

            return new ByAll(byArray);
        }
    }
}
