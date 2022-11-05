package com.innowise.relax.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PosterEventPageObject extends AbstractPageObject {

    private static final By CATEGORY_BY = By.xpath("//div[@class='body-text-3 b-afisha_cinema_description']");
    private static final By FEEDBACK_FORM_BY = By.xpath("//div[@class='send-feedback']");

    public PosterEventPageObject(WebDriver driver) {
        super(driver);
    }

    public WebElement getCategoryElement() {
        return getElementWhenVisible(CATEGORY_BY);
    }

    public WebElement getFeedbackElement() {
        return getElementWhenVisible(FEEDBACK_FORM_BY);
    }

}
