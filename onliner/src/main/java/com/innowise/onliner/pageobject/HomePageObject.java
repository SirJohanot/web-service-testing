package com.innowise.onliner.pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePageObject {

    private static final By SEARCH_INPUT_BY = By.xpath("//input[@class='fast-search__input']");

    private static final String SEARCH_RESULT_XPATH = "//*[@id=\"search-page\"]//a[text()='%s']";
    private static final String VIEW_OFFERS_BUTTON_XPATH = "//*[@id=\"search-page\"]//a[text()='%s']/../../..//a[contains(@class, 'product__button')]";

    private final WebDriver driver;

    private final WebDriverWait webDriverWait;

    public HomePageObject(WebDriver driver) {
        this.driver = driver;
        this.webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Step("Enter {searchString} in the search field")
    public void search(String searchString) {
        WebElement searchInputElement = driver.findElement(SEARCH_INPUT_BY);
        searchInputElement.sendKeys(searchString);
    }

    @Step("Click on [View Offers] for {productName}")
    public ProductOffersPageObject clickViewOffers(String productName) {
        String completeViewOffersButtonXpathString = String.format(VIEW_OFFERS_BUTTON_XPATH, productName);
        By viewOffersButtonBy = By.xpath(completeViewOffersButtonXpathString);
        WebElement offersButtonElement = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(viewOffersButtonBy));
        offersButtonElement.click();
        return new ProductOffersPageObject(driver);
    }

    public WebElement getProductFromSearchResults(String productName) {
        driver.switchTo().frame(0);
        String completeProductXpathString = String.format(SEARCH_RESULT_XPATH, productName);
        By searchResultBy = By.xpath(completeProductXpathString);
        return webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(searchResultBy));
    }

}
