package com.patiun.onliner.pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ShoppingCartPageObject {


    private static final String PRODUCT_ELEMENT_XPATH = "//div[contains(@class, 'cart-form__offers-list')]//a[contains(text(), '%s')]/../../../../..";
    private static final By DELETE_PRODUCT_BUTTON_BY = By.xpath("//a[contains(@class, 'cart-form__button_remove')]");
    private static final By PRODUCT_ELEMENTS_LIST_BY = By.xpath("//div[contains(@class, 'cart-form__offers-unit')]");
    private static final String PRODUCT_REMOVAL_NOTIFICATION_ELEMENT_XPATH = "//div[contains(@class, 'cart-form__offers-list')]//div[contains(text(), '%s')]/../../../..";
    private static final By REMOVAL_NOTIFICATION_CLOSE_BUTTON_BY = By.xpath("div[2]/div[1]/div[2]/div[2]/a[2]");

    private final WebDriver driver;
    private final WebDriverWait webDriverWait;

    public ShoppingCartPageObject(WebDriver driver) {
        this.driver = driver;
        this.webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public WebElement getProductWebElement(String productName) {
        String fullProductElementXpath = String.format(PRODUCT_ELEMENT_XPATH, productName);
        By productElementBy = By.xpath(fullProductElementXpath);
        return webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(productElementBy));
    }

    public List<WebElement> getProductElements() {
        return driver.findElements(PRODUCT_ELEMENTS_LIST_BY);
    }

    @Step("Hover the cursor over {productName}")
    public void hoverOverProductElement(String productName) {
        WebElement productElement = getProductWebElement(productName);
        Actions action = new Actions(driver);
        action.moveToElement(productElement).perform();
    }

    @Step("Click on the [Remove] button of {productName}")
    public void removeProductFromCart(String productName) {
        WebElement productElement = getProductWebElement(productName);
        WebElement removeButton = productElement.findElement(DELETE_PRODUCT_BUTTON_BY);
        webDriverWait.until(ExpectedConditions.elementToBeClickable(removeButton));
        removeButton.click();
    }

    @Step("Click on the [Close] button of the removal notification of {productName}")
    public void closeProductRemovedNotification(String productName) {
        String notificationElementFullXpath = String.format(PRODUCT_REMOVAL_NOTIFICATION_ELEMENT_XPATH, productName);
        By notificationElementBy = By.xpath(notificationElementFullXpath);
        WebElement notificationElement = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(notificationElementBy));
        WebElement closeButton = notificationElement.findElement(REMOVAL_NOTIFICATION_CLOSE_BUTTON_BY);
        closeButton.click();
    }

}
