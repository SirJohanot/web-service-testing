package com.patiun.relax.pageobject;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class RestaurantsPageTest {

    private static final String DISTRICT = "Заводской";
    private static final String CUISINE = "Белорусская";
    private static final boolean ALLOW_TAKEOUT_MENU = true;

    private WebDriver driver;
    private HomePageObject homePageObject;

    @Step("Launch the web browser and go to https://relax.by/")
    @BeforeEach
    public void setup() {
        driver = new ChromeDriver();
        driver.get("https://relax.by/");
        driver.manage().window().maximize();
        homePageObject = new HomePageObject(driver);
    }

    @Step("Leave Relax and quit the web browser")
    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    @Story("User tries to find a restaurant with the following filters: district-Zavodskoy; cuisine-Belarusian; takeout food-yes; takeout menu-yes")
    @Description("The search results should correspond with the set filters")
    public void testFiltersShouldDisplayTheCorrectSearchResults() {
        //given
        homePageObject.selectFoodInTheTopMenu();
        RestaurantsPageObject restaurantsPageObject = homePageObject.selectRestaurantsFromFood();
        restaurantsPageObject.openFilters();
        restaurantsPageObject.setDistrictFilter(DISTRICT);
        restaurantsPageObject.toggleTakeout();
        restaurantsPageObject.setCuisine(CUISINE);
        restaurantsPageObject.setTakeoutMenu(ALLOW_TAKEOUT_MENU);
        restaurantsPageObject.closeFilters();
        EstablishmentPageObject establishmentPageObject = restaurantsPageObject.clickOnSearchResult(1);
        //when
        String resultDistrict = establishmentPageObject.getDistrict();
        boolean resultTakeout = establishmentPageObject.hasTakeout();
        String resultCuisines = establishmentPageObject.getCuisines();
        boolean resultContainsTheRequiredCuisine = resultCuisines.contains(CUISINE);
        //then
        Assertions.assertEquals(DISTRICT, resultDistrict);
        Assertions.assertTrue(resultTakeout);
        Assertions.assertTrue(resultContainsTheRequiredCuisine);
    }

}
