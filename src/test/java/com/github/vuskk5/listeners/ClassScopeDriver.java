package com.github.vuskk5.listeners;

import com.github.vuskk5.drivermanager.DriverManager;
import org.testng.IClassListener;
import org.testng.ITestClass;

public class ClassScopeDriver implements IClassListener {
    @Override
    public void onBeforeClass(ITestClass testClass) {
        DriverManager.startChrome();
    }

    @Override
    public void onAfterClass(ITestClass testClass) {
        DriverManager.quit();
    }
}
