package com.innowise.onliner.pageobject;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OnlinerTest {

    private static final String PRODUCT_NAME = "Смартфон Samsung Galaxy A52 SM-A525F/DS 4GB/128GB (черный)";

    private static WebDriver driver;
    private static HomePageObject homePageObject;
    private static ProductOffersPageObject productOffersPageObject;
    private static ShoppingCartPageObject shoppingCartPageObject;

    @Step("Launch the web browser and go to Onliner")
    @BeforeAll
    public static void setup() {
        driver = new ChromeDriver();
        driver.get("https://onliner.by/");
        driver.manage().window().maximize();
        homePageObject = new HomePageObject(driver);
        productOffersPageObject = new ProductOffersPageObject(driver);
        shoppingCartPageObject = new ShoppingCartPageObject(driver);
    }

    @Step("Leave Onliner and quit the web browser")
    @AfterAll
    public static void tearDown() {
        driver.quit();
    }

    @Test
    @Order(1)
    @Story("User tries to search the catalog for a Samsung smartphone")
    @Description("The product has to be displayed upon search")
    public void testSearchShouldDisplayTheRequestedProduct() {
        //given
        //when
        homePageObject.search(PRODUCT_NAME);
        boolean productIsPresentInSearchResults = homePageObject.switchToSearchResultsFrameAndGetProductSearchResult(PRODUCT_NAME).isDisplayed();
        productOffersPageObject = homePageObject.clickViewOffers(PRODUCT_NAME);
        //then
        Assertions.assertTrue(productIsPresentInSearchResults);
    }

    @Test
    @Order(2)
    @Story("User tries to add the cheapest offer of a Samsung phone to Cart")
    @Description("The box with product, text [Product added to cart] and the buttons [Go to cart] and [Continue shopping] will appear on the screen")
    public void testAddToCartShouldDisplayTextAndButtons() {
        //given
        //when
        productOffersPageObject.confirmRegion();
        productOffersPageObject.addTheCheapestOfferToCart();
        boolean messageIsDisplayed = productOffersPageObject.getProductAddedToCartMessageElement().isDisplayed();
        boolean continueShoppingButtonIsDisplayed = productOffersPageObject.getContinueShoppingButtonElement().isDisplayed();
        boolean goToCartButtonIsDisplayed = productOffersPageObject.getGoToCartButtonElement().isDisplayed();
        //then
        Assertions.assertTrue(messageIsDisplayed);
        Assertions.assertTrue(continueShoppingButtonIsDisplayed);
        Assertions.assertTrue(goToCartButtonIsDisplayed);
    }

    @Test
    @Order(3)
    @Story("User goes to the Shopping Cart page after adding a Samsung phone product to it")
    @Description("The added product will appear in the shopping cart")
    public void testShoppingCartShouldDisplayTheAddedProduct() {
        //given
        shoppingCartPageObject = productOffersPageObject.clickGoToCart();
        //when
        boolean productIsDisplayedInTheCart = shoppingCartPageObject.getProductWebElement(PRODUCT_NAME).isDisplayed();
        //then
        Assertions.assertTrue(productIsDisplayedInTheCart);
    }

    @Test
    @Order(4)
    @Story("User tries to remove the Samsung phone product from the shopping cart")
    @Description("The cart will be empty")
    public void testRemoveFromCartShouldRemoveTheProductFromTheList() {
        //given
        shoppingCartPageObject.hoverOverProductElement(PRODUCT_NAME);
        shoppingCartPageObject.removeProductFromCart(PRODUCT_NAME);
        shoppingCartPageObject.closeProductRemovedNotification(PRODUCT_NAME);
        //when
        List<WebElement> productsInCart = shoppingCartPageObject.getProductElements();
        boolean cartIsEmpty = productsInCart.isEmpty();
        //then
        Assertions.assertTrue(cartIsEmpty);
    }

}
