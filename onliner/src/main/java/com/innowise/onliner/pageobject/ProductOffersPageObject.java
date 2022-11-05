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

    private static final By REGION_ELEMENT_BY = By.xpath("//div[@class='offers-list__target']");
    private static final By REGION_CONFIRMATION_BUTTON_BY = By.xpath("//span[contains(@class, 'offers-form__button')]");
    private static final By OFFERS_LIST_BY = By.xpath("//div[@class='offers-list__group']");
    private static final By OFFERS_BY = By.xpath("//div[@class='offers-list__flex']");
    private static final By PRICE_OF_OFFER_ELEMENT_BY = By.xpath("div[1]/div[1]/div");
    private static final By ADD_TO_CART_BUTTON_OF_OFFER_ELEMENT_BY = By.xpath("div[2]/div[1]/a[2]");
    private static final By PRODUCT_ADDED_TO_CART_MESSAGE_BY = By.xpath("//div[@class='product-recommended__sidebar-overflow']/div");
    private static final By CONTINUE_SHOPPING_BUTTON_BY = By.xpath("//div[@class='product-recommended__sidebar-overflow']/div[3]/a[1]");
    private static final By GO_TO_CART_BUTTON_BY = By.xpath("//div[@class='product-recommended__sidebar-overflow']/div[3]/a[2]");

    private static final String PRICE_TRIM_REGEX = "[^\\d,]";

    private final WebDriver driver;
    private final WebDriverWait webDriverWait;

    public ProductOffersPageObject(WebDriver driver) {
        this.driver = driver;
        this.webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Step("Confirm the region choice")
    public void confirmRegion() {
        WebElement regionElement = driver.findElement(REGION_ELEMENT_BY);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", regionElement);
        WebElement confirmRegionButton = webDriverWait.until(ExpectedConditions.elementToBeClickable(REGION_CONFIRMATION_BUTTON_BY));
        confirmRegionButton.click();
    }

    @Step("Choose the product with the cheapest price and click on [Add to Cart]")
    public void addTheCheapestOfferToCart() {
        WebElement offersListElement = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(OFFERS_LIST_BY));

        List<WebElement> offerElements = offersListElement.findElements(OFFERS_BY);
        WebElement cheapestOffer = getCheapestOfferElement(offerElements);
        WebElement addToCartButton = cheapestOffer.findElement(ADD_TO_CART_BUTTON_OF_OFFER_ELEMENT_BY);
        addToCartButton.click();
    }

    private WebElement getCheapestOfferElement(List<WebElement> offerElements) {
        WebElement cheapestOffer = offerElements.get(0);
        for (WebElement offer : offerElements) {
            Float offerPrice = getOfferPrice(offer);
            Float currentMinimumPrice = getOfferPrice(cheapestOffer);
            if (offerPrice > currentMinimumPrice) {
                cheapestOffer = offer;
            }
        }
        return cheapestOffer;
    }

    @Step("Click on [Go to Cart] button")
    public ShoppingCartPageObject clickGoToCart() {
        WebElement goToCartButton = getGoToCartButtonElement();
        goToCartButton.click();
        return new ShoppingCartPageObject(driver);
    }

    public WebElement getProductAddedToCartMessageElement() {
        return webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(PRODUCT_ADDED_TO_CART_MESSAGE_BY));
    }

    public WebElement getContinueShoppingButtonElement() {
        return webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(CONTINUE_SHOPPING_BUTTON_BY));
    }

    public WebElement getGoToCartButtonElement() {
        return webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(GO_TO_CART_BUTTON_BY));
    }

    private Float getOfferPrice(WebElement offerElement) {
        String offerPriceString = offerElement.findElement(PRICE_OF_OFFER_ELEMENT_BY).getText();
        offerPriceString = offerPriceString.replaceAll(PRICE_TRIM_REGEX, "");
        offerPriceString = offerPriceString.replaceAll(",", ".");
        return Float.valueOf(offerPriceString);
    }

}
