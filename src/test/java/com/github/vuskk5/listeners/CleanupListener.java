package com.github.vuskk5.listeners;

import com.github.vuskk5.FrameworkConfig;
import com.github.vuskk5.cleanup.CleanupService;
import com.github.vuskk5.cleanup.CleanupThread;
import lombok.SneakyThrows;
import org.aeonbits.owner.ConfigFactory;
import org.testng.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.lang.annotation.Annotation;

public class CleanupListener implements IInvokedMethodListener, ITestListener, IClassListener, IExecutionListener {
    /**
     * When execution finishes, wait for the {@link CleanupThread} to finish before stopping the run.
     */
    @Override
    @SneakyThrows(InterruptedException.class)
    public void onExecutionFinish() {
        FrameworkConfig config = ConfigFactory.create(FrameworkConfig.class);
        CleanupThread thread = CleanupService.cleanupThread();

        thread.hadTestsFinished(true);

        thread.join(config.cleanupThreadMaxLife());
    }

    /**
     * Before each method is executed, check its annotations and set current {@link CleanupService.Level}
     */
    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        for (Annotation annotation : method.getTestMethod().getConstructorOrMethod().getMethod().getAnnotations()) {
            if (BeforeTest.class.isAssignableFrom(annotation.getClass())) {
                CleanupService.setLevel(CleanupService.Level.TEST);
                return;
            }
            if (BeforeClass.class.isAssignableFrom(annotation.getClass())) {
                CleanupService.setLevel(CleanupService.Level.CLASS);
                return;
            }
            if (BeforeMethod.class.isAssignableFrom(annotation.getClass()) ||
                Test.class.isAssignableFrom(annotation.getClass())) {
                CleanupService.setLevel(CleanupService.Level.METHOD);
                return;
            }
        }
    }

    /**
     * After each method is executed check if it was a {@link Test} and call cleanup at the method level.
     *
     * @implNote There is no "onAfterMethod" so we must use {@link IInvokedMethodListener#afterInvocation(IInvokedMethod, ITestResult)},
     * an alternative would be to use both {@link ITestListener#onTestSuccess(ITestResult)} and {@link ITestListener#onTestFailure(ITestResult)}
     */
    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod()) {
            CleanupService.cleanUp(CleanupService.Level.METHOD);
        }
    }

    /**
     * After each class is finished, call cleanup at class level
     */
    @Override
    public void onAfterClass(ITestClass testClass) {
        CleanupService.cleanUp(CleanupService.Level.CLASS);
    }

    /**
     * After each test is finished, call cleanup at test level
     */
    @Override
    public void onFinish(ITestContext context) {
        CleanupService.cleanUp(CleanupService.Level.TEST);
    }
}
