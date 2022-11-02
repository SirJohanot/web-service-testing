package com.innowise.relax.pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePageObject extends AbstractPageObject {

    private static final By SEARCH_INPUT_BY = By.xpath("//input[@class='Search__input']");
    private static final String SEARCH_RESULT_ELEMENT_XPATH = "//div[contains(text(), '%s') and contains(@class, 'SearchResults__item--title')]";
    private static final By FOOD_BUTTON_BY = By.xpath("//div[@title='Еда']");
    private static final By RESTAURANTS_BUTTON_BY = By.xpath("//a[text()='Рестораны']");

    public HomePageObject(WebDriver driver) {
        super(driver);
    }

    @Step("Input {searchString} into the search field")
    public void search(String searchString) {
        WebElement searchInputElement = driver.findElement(SEARCH_INPUT_BY);
        searchInputElement.sendKeys(searchString);
    }

    @Step("Select search result {searchResultTitle}")
    public EstablishmentPageObject selectSearchResult(String searchResultTitle) {
        String completeProductXpathString = String.format(SEARCH_RESULT_ELEMENT_XPATH, searchResultTitle);
        By searchResultBy = By.xpath(completeProductXpathString);
        clickButton(searchResultBy);
        return new EstablishmentPageObject(driver);
    }

    @Step("Select 'Food' in the top menu")
    public void selectFoodInTheTopMenu() {
        clickButton(FOOD_BUTTON_BY);
    }

    @Step("Select 'Restaurants' from 'Food'")
    public RestaurantsPageObject selectRestaurantsFromFood() {
        clickButton(RESTAURANTS_BUTTON_BY);
        return new RestaurantsPageObject(driver);
    }

}
