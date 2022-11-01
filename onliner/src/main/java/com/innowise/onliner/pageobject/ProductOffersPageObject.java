package com.innowise.onliner.pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ProductOffersPageObject {

    private final WebDriver driver;

    private final WebDriverWait webDriverWait;

    private static final By CURRENT_REGION_ELEMENT_BY = By.xpath("//*[@id=\"container\"]/div/div/div/div/div[2]/div[1]/main/div/div/div[2]/div[1]/div/div[3]/div/div[1]");
    private static final By REGION_CONFIRMATION_BUTTON_BY = By.xpath("//*[@id=\"container\"]/div/div/div/div/div[2]/div[1]/main/div/div/div[2]/div[1]/div/div[3]/div/div[1]/div[2]/div[1]/div/div/div/div/div[2]/span[1]");
    private static final By OFFERS_LIST_BY = By.xpath("//div[@class='offers-list__group']");
    private static final By OFFERS_BY = By.xpath("//div[@class='offers-list__flex']");
    private static final By OFFER_PRICE_BY = By.xpath("div[1]/div[1]/div");
    private static final By ADD_TO_CART_BUTTON_BY = By.xpath("div[2]/div[1]/a[2]");
    private static final By PRODUCT_ADDED_TO_CART_MESSAGE_BY = By.xpath("//*[@id=\"container\"]/div/div/div/div/div[2]/div[1]/main/div/div/div[2]/div[1]/div/div[3]/div/div[4]/div[1]/div/div[2]/div[2]/div[2]/div/div[1]");
    private static final By CONTINUE_SHOPPING_BUTTON_BY = By.xpath("//*[@id=\"container\"]/div/div/div/div/div[2]/div[1]/main/div/div/div[2]/div[1]/div/div[3]/div/div[4]/div[1]/div/div[2]/div[2]/div[2]/div/div[3]/a[1]");
    private static final By GO_TO_CART_BUTTON_BY = By.xpath("//*[@id=\"container\"]/div/div/div/div/div[2]/div[1]/main/div/div/div[2]/div[1]/div/div[3]/div/div[4]/div[1]/div/div[2]/div[2]/div[2]/div/div[3]/a[2]");

    private static final String PRICE_TRIM_REGEX = "[^\\d,]";

    public ProductOffersPageObject(WebDriver driver) {
        this.driver = driver;
        this.webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @Step("Choose the product with the cheapest price and click on [Add to Cart]")
    public void addTheCheapestOfferToCart() {
        WebElement currentRegionElement = driver.findElement(CURRENT_REGION_ELEMENT_BY);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", currentRegionElement);
        WebElement regionConfirmationButton = webDriverWait.until(ExpectedConditions.elementToBeClickable(REGION_CONFIRMATION_BUTTON_BY));
        regionConfirmationButton.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(OFFERS_LIST_BY));

        List<WebElement> offerElements = driver.findElements(OFFERS_BY);
        WebElement cheapestOffer = offerElements.get(0);
        for (WebElement offer : offerElements) {
            Float offerPrice = getOfferPrice(offer);
            Float currentMinimumPrice = getOfferPrice(cheapestOffer);
            if (offerPrice < currentMinimumPrice) {
                cheapestOffer = offer;
            }
        }
        WebElement addToCartButton = cheapestOffer.findElement(ADD_TO_CART_BUTTON_BY);
        addToCartButton.click();
    }

    public boolean productAddedToCartMessageIsVisible() {
        WebElement productAddedMessage = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(PRODUCT_ADDED_TO_CART_MESSAGE_BY));
        return productAddedMessage.isDisplayed();
    }

    public boolean continueShoppingButtonIsVisible() {
        WebElement continueShoppingButton = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(CONTINUE_SHOPPING_BUTTON_BY));
        return continueShoppingButton.isDisplayed();
    }

    public boolean goToCartButtonIsVisible() {
        WebElement goToCartButton = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(GO_TO_CART_BUTTON_BY));
        return goToCartButton.isDisplayed();
    }

    private Float getOfferPrice(WebElement offerElement) {
        String offerPriceString = offerElement.findElement(OFFER_PRICE_BY).getText();
        offerPriceString = offerPriceString.replaceAll(PRICE_TRIM_REGEX, "");
        offerPriceString = offerPriceString.replaceAll(",", ".");
        return Float.valueOf(offerPriceString);
    }

}
