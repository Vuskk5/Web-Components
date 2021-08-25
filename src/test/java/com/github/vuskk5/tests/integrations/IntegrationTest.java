package com.github.vuskk5.tests.integrations;

import com.codeborne.selenide.Selenide;
import lombok.NonNull;
import org.openqa.selenium.NotFoundException;

import java.net.URL;

public class IntegrationTest {
    protected void openFile(@NonNull String fileName) {
        URL filePath = this.getClass().getClassLoader().getResource(fileName);

        if (filePath == null)
            throw new NotFoundException("Resource \"" + fileName + "\" was not found.");

        Selenide.open(filePath);
    }
}
