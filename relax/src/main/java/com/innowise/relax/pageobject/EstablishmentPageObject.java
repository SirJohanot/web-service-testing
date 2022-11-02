package com.innowise.relax.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class EstablishmentPageObject extends AbstractPageObject {

    private static final By PHONE_NUMBER_BY = By.xpath("//a[@class='PhoneLink']");
    private static final By ADDRESS_BY = By.xpath("//button[contains(@class, 'PersonalContacts__address')]");
    private static final By WORKING_HOURS_BY = By.xpath("//button[contains(@class, 'PersonalContacts__worktime')]/span");
    private static final By TAKEOUT_ELEMENT_BY = By.xpath("//div[@class='PersonalDelivery__block']");
    private static final By DISTRICT_ELEMENT_BY = By.xpath("//div[@class='ContentBox__content Features__content']//span[text()='Район']/../../../div[2]/div/span/span");
    private static final By SHOW_MORE_BUTTON_BY = By.xpath("//div[@class='ContentBox Features isVisible']//div[@class='ContentBox__showMore Link Link--primary']");
    private static final By CUISINE_ELEMENT_BY = By.xpath("//div[@class='ContentBox__content Features__content']//span[text()='Кухня']/../../../div[2]/div/span/a");

    public EstablishmentPageObject(WebDriver driver) {
        super(driver);
    }

    public String getPhoneNumber() {
        WebElement phoneNumberElement = getElementWhenVisible(PHONE_NUMBER_BY);
        return phoneNumberElement.getAttribute("href");
    }

    public String getAddress() {
        WebElement addressElement = getElementWhenVisible(ADDRESS_BY);
        return addressElement.getAttribute("title");
    }

    public String getWorkingHours() {
        WebElement workHoursElement = getElementWhenVisible(WORKING_HOURS_BY);
        return workHoursElement.getText();
    }

    public boolean hasTakeout() {
        List<WebElement> takeoutBlock = driver.findElements(TAKEOUT_ELEMENT_BY);
        return !takeoutBlock.isEmpty();
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
