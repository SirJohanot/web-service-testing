package com.patiun.relax.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EstablishmentPageObject extends AbstractPageObject {

    private static final By ACCEPT_COOKIES_BUTTON_BY = By.xpath("//div[@class='CookiesNotification__control']");
    private static final By PHONE_NUMBER_BY = By.xpath("//a[@class='PhoneLink']");
    private static final By ADDRESS_BY = By.xpath("//button[contains(@class, 'PersonalContacts__address')]");
    private static final By SHOW_WORKING_HOURS_BUTTON_BY = By.xpath("//button[contains(@class, 'PersonalContacts__worktime')]/span");
    private static final By CLOSE_WORKING_HOURS_BUTTON_BY = By.xpath("//div[@class='TouchIcon Popup__close']");
    private static final By WORKING_HOUR_BY = By.xpath("/html/body/div[7]/div/div[3]/div/div[1]/div[1]/main/div//div[@class='ContactsPopupOpening__subMain']");
    private static final By DISTRICT_ELEMENT_BY = By.xpath("//div[@class='ContentBox__content Features__content']//span[text()='Район']/../../../div[2]/div/span/span");
    private static final By SHOW_MORE_BUTTON_BY = By.xpath("//div[@class='ContentBox Features isVisible']//div[@class='ContentBox__showMore Link Link--primary']");
    private static final By CUISINE_ELEMENT_BY = By.xpath("//div[@class='ContentBox__content Features__content']//span[text()='Кухня']/../../../div[2]/div/span/a");

    public EstablishmentPageObject(WebDriver driver) {
        super(driver);
        clickButton(ACCEPT_COOKIES_BUTTON_BY);
    }

    public String getPhoneNumber() {
        WebElement phoneNumberElement = getElementWhenVisible(PHONE_NUMBER_BY);
        return phoneNumberElement.getAttribute("href");
    }

    public String getAddress() {
        WebElement addressElement = getElementWhenVisible(ADDRESS_BY);
        return addressElement.getAttribute("title");
    }

    public List<String> getWorkingHours() {
        clickButton(SHOW_WORKING_HOURS_BUTTON_BY);
        WebElement closeWorkingHoursButton = getElementWhenClickable(CLOSE_WORKING_HOURS_BUTTON_BY);

        List<WebElement> workHoursElements = driver.findElements(WORKING_HOUR_BY);
        List<String> workingHours = workHoursElements.stream()
                .map(WebElement::getText)
                .toList();

        closeWorkingHoursButton.click();

        return workingHours;
    }

    public String getDistrict() {
        WebElement districtElement = getElementWhenVisible(DISTRICT_ELEMENT_BY);
        return districtElement.getText();
    }

    public String getCuisines() {
        List<WebElement> showMoreButtons = driver.findElements(SHOW_MORE_BUTTON_BY);
        if (!showMoreButtons.isEmpty()) {
            WebElement showMoreButton = showMoreButtons.get(0);
            showMoreButton.click();
        }
        List<WebElement> cuisineElements = driver.findElements(CUISINE_ELEMENT_BY);
        StringBuilder stringBuilder = new StringBuilder();
        for (WebElement cuisineElement : cuisineElements) {
            String cuisineName = cuisineElement.getText();
            stringBuilder.append(cuisineName)
                    .append(" ");
        }
        return stringBuilder.toString();
    }

}
