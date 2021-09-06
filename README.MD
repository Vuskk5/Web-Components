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
Components are the core feature of the framework, they allow you to create and map your HTML components to your Java code.  
The best way to explain them would be with an example, consider the following HTML:
```html
<div class="textfield-wrapper">
    <label>Some Header</label>
    <input type="text"/>
    <input type="button" value="verify">
</div>
```
Clearly we have a single 'textfield' element which is composed of multiple other elements.  
The element's root is the `<div>` tag. The `<label>`, `<input>` and `<button>`, are the child elements.

For regular Selenium code, a simple solution to automate this in page-object pattern, might look like this:

```java
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

class Page {
    @FindBy(css = ".textfield-wrapper > label")
    WebElement textFieldLabel;
    
    @FindBy(css = ".textfield-wrapper > [type='text']")
    WebElement textFieldInput;

    @FindBy(css = ".textfield-wrapper > [type='button']")
    WebElement textFieldButton;
}
```
So for a single field on a page, we had to declare 3 `WebElement`s.  

Now take a look at the Web Component approach:

```java
import com.github.vuskk5.WebComponent;
import com.github.vuskk5.support.Find;
import org.openqa.selenium.WebElement;

class TextField extends WebComponent {
    @Find(tagName = "label")
    WebElement label;

    @Find(css = "[type='text']")
    WebElement input;

    @Find(attribute = "type", attributeValue = "button")
    WebElement button;
}

class Page {
    @Find(className = "textfield-wrapper")
    TextField textfield;
}
```
After creating the component and mapping its child elements, the `Page` class has only a single field.  
It has the same functionality just like the previous example, but now, instead of accessing elements directly from the 
page, we need to call the component, and then it's child element. like so:
```java
var page = new Page();
var field = page.textfield;

field.label.getText();   // "Some Header"
field.input.sendKeys("Hello World!");
field.button.click();
```
Of course that wrapper methods can also be added to the components:

```java
import com.github.vuskk5.WebComponent;

class TextField extends WebComponent {
    ...

    public TextField set(String text) {
        if (this.input.getAttribute("value") != null) {
            this.input.clear();
        }
        this.input.sendKeys(text);
    }
}
```
And the last great thing is the support of lists of components:
```java
class Page {
    @Find(className = "textfield-wrapper")
    List<TextField> textfields;
}
```
## @Root
The `@Root` annotation allows the definition of the Component's root element.  
Every 'Component' must have a root, its it's Search Context. All elements which are defined in a `WebComponent` are searched under the root element.   
The annotation only defines if and how the root will be located, meaning that when the `@Root` annotation is present, you do not have to provide a locator of the root itself, you can provide an inner locator which will then automatically find the root.  
All of this may be a bit confusing, take a look at the following:
```html
<body>
    <div class="textfield-wrapper">
        <label>First Input</label>
        <input type="text"/>
        <input type="button" value="verify">
    </div>
    <div class="textfield-wrapper">
        <label>Second Input</label>
        <input type="text"/>
        <input type="button" value="verify">
    </div>
</body>
```
When we try to locate the second field using `css(.textfield-wrapper)` we will still get the first element, but when the `@Root` is added we can locate the `<label>` by its inner text:
```java
import com.github.vuskk5.support.Root;

@Root(className = "textfield-wrapper")
class TextField extends WebComponent {
    @Find(tagName = "label")
    WebElement label;

    @Find(css = "[type='text']")
    WebElement input;
}

class Page {
    @Find(text = "Second Input")
    TextField textfield;
}
```
This will locate the element matching the given text, in this case it would be the `<label>` tag, and then it will find an ancestor of this tag, with the class "textfield-wrapper", thus finding the component's root.
# WebPage
## Dollar Methods
