package com.github.vuskk5.tests.drivermanager;

import com.github.vuskk5.driver.DriverManager;
import com.github.vuskk5.listeners.MethodScopeDriver;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(MethodScopeDriver.class)
public class MethodScopeTest {
    private WebDriver driver;

    @Test
    void driverInitialization() {
        driver = DriverManager.driver();
    }

    @Test(dependsOnMethods = "driverInitialization")
    void driverShouldBeDifferentInEachTest() {
        assert driver != null;
        assert driver != DriverManager.driver();
    }
}
