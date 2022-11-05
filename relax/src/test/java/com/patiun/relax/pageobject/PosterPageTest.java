package com.patiun.relax.pageobject;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class PosterPageTest {

    private WebDriver driver;
    private PosterPageObject posterPageObject;

    @Step("Launch the web browser and go to https://afisha.relax.by/")
    @BeforeEach
    public void setup() {
        driver = new ChromeDriver();
        driver.get("https://afisha.relax.by/");
        driver.manage().window().maximize();
        posterPageObject = new PosterPageObject(driver);
        posterPageObject.closeAdvertisement();
    }

    @Step("Leave Relax and quit the web browser")
    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    @Story("User tries to view an event's category and feedback section")
    @Description("The event's page should display teh correct category and feedback section")
    public void testPosterEventShouldDisplayCategoryAndFeedbackSection() {
        //given
        PosterEventPageObject posterEventPageObject = posterPageObject.openCardOfPoster(1);
        //when
        WebElement categoryElement = posterEventPageObject.getCategoryElement();
        boolean categoryElementIsDisplayed = categoryElement.isDisplayed();
        WebElement feedbackElement = posterEventPageObject.getFeedbackElement();
        boolean feedbackElementIsDisplayed = feedbackElement.isDisplayed();
        //then
        Assertions.assertTrue(categoryElementIsDisplayed);
        Assertions.assertTrue(feedbackElementIsDisplayed);
    }
}
