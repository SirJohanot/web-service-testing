package com.innowise.relax.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class AbstractPageObject {

    private static final int MAXIMUM_TIMEOUT = 15;

    protected WebDriver driver;
    protected WebDriverWait webDriverWait;

    public AbstractPageObject(WebDriver driver) {
        this.driver = driver;
        this.webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(MAXIMUM_TIMEOUT));
    }

    protected void clickButton(By buttonBy) {
        WebElement button = getElementWhenClickable(buttonBy);
        button.click();
    }

    protected WebElement getElementWhenClickable(By elementBy) {
        return webDriverWait.until(ExpectedConditions.elementToBeClickable(elementBy));
    }

    protected WebElement getElementWhenVisible(By elementBy) {
        return webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(elementBy));
    }

}
