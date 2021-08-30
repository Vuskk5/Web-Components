# Web Component Framework
This project provides a solution for composing 'Web Components' when testing systems developed using component-based frameworks (React, Vue, Angular...)  

Also provided are some useful extensions and shortcuts for locating elements. 

# Contents

# Extended @FindBy
The regular `@FindBy` annotation is great, it allows us to place the 'locator' near our element and have it proxied lazily.  
Still, a few problems might rise, one of them is that it only supports the default Selenium `By` locators.  
This means that for every complex locator we'll have to fall to CssSelector or XPath, having a big impact on code readability and maintainability. 
## @Find
`@Find` is an 'extended' version of Selenium's `@FindBy`.  
It allows you to use and add more locator strategies to the annotation and provides some useful ones out of the box.  
Added locators:
* *attribute, attributeValue* - Locate elements by an attribute name, and it's value
* *text* - Locate elements whose text contains the given text
* *exactText* - Locate elements whose text matches the given text
* *partialClassName* - Locate elements whose class contains the given class
* *title* - Locate elements by their 'title' attribute
* *dataTestId* - Locate elements by their 'data-testid' attribute

Note that this annotation is fully customizable, if you wish to add a custom locator follow the [advanced usage guide]().
## @Finds
`@Finds` acts the same way as Selenium's `@FindBys`.  
Allows searching an element by a chained locator.

```java
import com.github.vuskk5.support.Find;
import com.github.vuskk5.support.Finds;

// This will locate the element with a class 'input-field' 
// inside an element with id 'wrapper'
@Finds({ @Find(id = "wrapper"),
         @Find(className = "input-field") })
private WebElement element;
```
## @FindAll
`@FindAll` acts the same way as Selenium's `@FindAll`.  
Allows searching an element or a list, by multiple locators.

```java
import com.github.vuskk5.support.Find;
import com.github.vuskk5.support.FindAll;

// This will locate any element who has a class 'radio' or 'button'
@FindAll({ @Find(className = "button"),
           @Find(className = "radio") })
private WebElement element;
```

# WebComponent
## @Root

# WebPage
## Dollar Methods
