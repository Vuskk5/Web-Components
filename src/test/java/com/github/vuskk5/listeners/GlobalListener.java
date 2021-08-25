package com.github.vuskk5.listeners;

import com.codeborne.selenide.Configuration;
import org.testng.IExecutionListener;

public class GlobalListener implements IExecutionListener {
    @Override
    public void onExecutionStart() {
        Configuration.headless = true;
    }
}
