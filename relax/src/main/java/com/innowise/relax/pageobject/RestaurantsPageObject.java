package com.innowise.relax.pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class RestaurantsPageObject extends AbstractPageObject {

    private static final By OPEN_FILTERS_BUTTON_BY = By.xpath("//div[contains(@class, 'FilterToolbar__allFiltersButton')]");
    private static final By CLOSE_FILTERS_BUTTON_BY = By.xpath("//div[@class='FilterSidebar__header']//div[@class='TouchIcon FilterSidebar__close']");
    private static final By DISTRICT_FILTER_BY = By.xpath("//div[@class='FilterSidebar__content']/div[2]");
    private static final String DISTRICT_SELECTION_ELEMENT_XPATH = "//div[@class='List List--selection FilterSelect__list']//span[text()='%s']";
    private static final By TAKEOUT_FILTER_BY = By.xpath("//div[@class='FilterSidebar__content']/div[3]");
    private static final By MORE_CUISINE_OPTIONS_BUTTON_BY = By.xpath("//div[@class='FilterSidebar__content']/div[8]/div[2]/div");
    private static final String CUISINE_OPTION_BUTTON_XPATH = "//div[@class='FilterSidebar__content']/div[8]//span[text()='%s']";
    private static final By ALLOW_TAKEOUT_MENU_BUTTON_BY = By.xpath("//div[@class='FilterSidebar__content']/div[27]/div[2]/label[1]");
    private static final By DISALLOW_TAKEOUT_MENU_BUTTON_BY = By.xpath("//div[@class='FilterSidebar__content']/div[27]/div[2]/label[2]");
    private static final By SEARCH_RESULT_ELEMENT_BY = By.xpath("//div[contains(@class, 'Place PlaceDefault body-1')]");

    public RestaurantsPageObject(WebDriver driver) {
        super(driver);
    }

    @Step("Open filters")
    public void openFilters() {
        clickButton(OPEN_FILTERS_BUTTON_BY);
    }

    @Step("Set {districtName} for the district filter")
    public void setDistrictFilter(String districtName) {
        clickButton(DISTRICT_FILTER_BY);
        String completeDistrictSelectionXpath = String.format(DISTRICT_SELECTION_ELEMENT_XPATH, districtName);
        By districtSelectionBy = By.xpath(completeDistrictSelectionXpath);
        clickButton(districtSelectionBy);
    }

    @Step("Toggle 'Takeout'")
    public void toggleTakeout() {
        clickButton(TAKEOUT_FILTER_BY);
    }

    @Step("Set 'Cuisine' to {cuisine}")
    public void setCuisine(String cuisine) {
        List<WebElement> moreCuisineOptionsButtons = driver.findElements(MORE_CUISINE_OPTIONS_BUTTON_BY);
        if (!moreCuisineOptionsButtons.isEmpty()) {
            clickButton(MORE_CUISINE_OPTIONS_BUTTON_BY);
        }
        String completeCuisineOptionXpath = String.format(CUISINE_OPTION_BUTTON_XPATH, cuisine);
        By cuisineOptionBy = By.xpath(completeCuisineOptionXpath);
        clickButton(cuisineOptionBy);
    }

    @Step("Set 'Takeout menu' to {takeoutMenu}")
    public void setTakeoutMenu(boolean takeoutMenu) {
        if (takeoutMenu) {
            clickButton(ALLOW_TAKEOUT_MENU_BUTTON_BY);
        } else {
            clickButton(DISALLOW_TAKEOUT_MENU_BUTTON_BY);
        }
    }

    @Step("Close filters")
    public void closeFilters() {
        clickButton(CLOSE_FILTERS_BUTTON_BY);
    }

    @Step("Click on search result {resultNumber}")
    public EstablishmentPageObject clickOnSearchResult(int resultNumber) {
        List<WebElement> searchResults = driver.findElements(SEARCH_RESULT_ELEMENT_BY);
        WebElement searchResult = searchResults.get(resultNumber - 1);
        searchResult.click();
        List<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        return new EstablishmentPageObject(driver);
    }

}
