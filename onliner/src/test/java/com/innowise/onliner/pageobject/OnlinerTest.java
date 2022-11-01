package com.innowise.onliner.pageobject;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class OnlinerTest {

    private static final String PRODUCT_NAME = "Смартфон Samsung Galaxy A52 SM-A525F/DS 4GB/128GB (черный)";

    private static WebDriver driver;
    private static HomePageObject homePageObject;
    private static ProductOffersPageObject productOffersPageObject;

    @Step("Launch the web browser and go to Onliner")
    @BeforeAll
    public static void setup() {
        driver = new ChromeDriver();
        driver.get("https://onliner.by/");
        driver.manage().window().maximize();
        homePageObject = new HomePageObject(driver);
    }

    @Step("Leave Onliner and quit the web browser")
    @AfterAll
    public static void tearDown() {
        driver.quit();
    }

    @Test
    @Story("User tries to search the catalog for a Samsung smartphone")
    @Description("The product has to be displayed upon search")
    public void testSearchShouldDisplayTheRequestedProduct() {
        //given
        //when
        homePageObject.search(PRODUCT_NAME);
        boolean productIsPresentInSearchResults = homePageObject.getProductFromSearchResults(PRODUCT_NAME).isDisplayed();
        productOffersPageObject = homePageObject.clickViewOffers(PRODUCT_NAME);
        //then
        Assertions.assertTrue(productIsPresentInSearchResults);
    }

    @Test
    @Story("User tries to add the cheapest offer of a Samsung phone to Cart")
    @Description("The box with product, text [Product added to cart] and the buttons [Go to cart] and [Continue shopping] will appear on the screen")
    public void testAddToCartShouldDisplayTextAndButtons() {
        //given
        //when
        productOffersPageObject.addTheCheapestOfferToCart();
        boolean messageIsDisplayed = productOffersPageObject.productAddedToCartMessageIsVisible();
        boolean continueShoppingButtonIsDisplayed = productOffersPageObject.continueShoppingButtonIsVisible();
        boolean goToCartButtonIsDisplayed = productOffersPageObject.goToCartButtonIsVisible();
        //then
        Assertions.assertTrue(messageIsDisplayed);
        Assertions.assertTrue(continueShoppingButtonIsDisplayed);
        Assertions.assertTrue(goToCartButtonIsDisplayed);
    }
}
