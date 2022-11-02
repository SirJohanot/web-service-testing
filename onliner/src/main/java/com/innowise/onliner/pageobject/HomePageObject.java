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

    private static final By SEARCH_RESULTS_IFRAME_BY = By.xpath("//iframe[@class='modal-iframe']");
    private static final String SEARCH_RESULT_ELEMENT_XPATH = "//ul[@class='search__results']//a[text()='%s']/../..";
    private static final By VIEW_OFFERS_OF_PRODUCT_ELEMENT_BUTTON_BY = By.xpath("//a[contains(@class, 'product__button')]");

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
        WebElement searchResultElement = getSearchResultElement(productName);
        WebElement offersButtonElement = searchResultElement.findElement(VIEW_OFFERS_OF_PRODUCT_ELEMENT_BUTTON_BY);
        offersButtonElement.click();
        return new ProductOffersPageObject(driver);
    }

    public WebElement switchToSearchResultsFrameAndGetProductSearchResult(String productName) {
        WebElement searchResultsIframe = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(SEARCH_RESULTS_IFRAME_BY));
        driver.switchTo().frame(searchResultsIframe);
        return getSearchResultElement(productName);
    }

    private WebElement getSearchResultElement(String productName) {
        String completeProductXpathString = String.format(SEARCH_RESULT_ELEMENT_XPATH, productName);
        By searchResultBy = By.xpath(completeProductXpathString);
        return webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(searchResultBy));
    }

}
