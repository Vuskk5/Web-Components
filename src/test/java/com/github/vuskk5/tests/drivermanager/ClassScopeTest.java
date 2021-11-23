package com.github.vuskk5.tests.drivermanager;

import com.github.vuskk5.driver.DriverManager;
import com.github.vuskk5.listeners.ClassScopeDriver;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(ClassScopeDriver.class)
public class ClassScopeTest {
    private WebDriver driver;

    @Test
    void driverInitialization() {
        // Will never pass, instead will throw a null-pointer in case of null driver
        var driver = DriverManager.driver();
        assert driver != null;

        this.driver = driver;
    }

    @Test(dependsOnMethods = "driverInitialization")
    void driverShouldBeDifferentInEachTest() {
        assert this.driver == DriverManager.driver();
    }
}
