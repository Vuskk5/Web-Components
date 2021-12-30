package com.github.vuskk5.drivermanager;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.events.WebDriverEventListener;

/**
 * Driver manager for vanilla Selenium
 */
public class DriverManager {
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver attachListener(WebDriverEventListener eventListener) {
        driver.set(new EventFiringWebDriver(driver()));
        ((EventFiringWebDriver) driver()).register(eventListener);
        return driver();
    }

    public static WebDriver start() {
        return startChrome();
    }

    public static WebDriver start(DriverType driverType) {
        return switch (driverType) {
            case CHROME -> startChrome();
            case INTERNET_EXPLORER -> startIExplorer();
            case EDGE, FIREFOX, OPERA, SAFARI -> throw new UnsupportedOperationException("Driver of type " + driverType + " is not supported.");
        };
    }

    public static ChromeDriver startChrome() {
        ChromeOptions options = new ChromeOptions();
        return startChrome(options);
    }

    public static ChromeDriver startChrome(ChromeOptions options) {
        WebDriverManager.chromedriver().setup();
        ChromeDriver chromeDriver = new ChromeDriver(options);
        driver.set(chromeDriver);
        return chromeDriver;
    }

    public static InternetExplorerDriver startIExplorer() {
        WebDriverManager.iedriver().setup();
        InternetExplorerDriver ieDriver = new InternetExplorerDriver();
        driver.set(ieDriver);
        return ieDriver;
    }

    public static WebDriver driver() {
        if (driver.get() == null) {
            throw new NullPointerException("You must initialize the driver before using it.");
        }

        return DriverManager.driver.get();
    }

    public static void quit() {
        driver.get().quit();
        driver.remove();
    }
}
