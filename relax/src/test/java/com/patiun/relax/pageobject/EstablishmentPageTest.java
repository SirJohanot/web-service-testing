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

public class EstablishmentPageTest {

    private static final String ESTABLISHMENT_NAME = "Luna";
    private static final String ESTABLISHMENT_PHONE_NUMBER = "tel:+375 293330074";
    private static final String ESTABLISHMENT_ADDRESS = "Могилев, ул. Ленинская, 22";
    private static final String ESTABLISHMENT_WORKING_HOURS = "до 00:00";

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
    @Story("User tries to search the catalog for the Luna restaurant")
    @Description("Phone, Address and working hours have to correspond with the expected value")
    public void testEstablishmentPageShouldCorrectlyDisplayPhoneAddressAndWorkingHours() {
        //given
        homePageObject.search(ESTABLISHMENT_NAME);
        EstablishmentPageObject establishmentPageObject = homePageObject.selectSearchResult(ESTABLISHMENT_NAME);
        //when
        String actualPhoneNumber = establishmentPageObject.getPhoneNumber();
        String actualAddress = establishmentPageObject.getAddress();
        String actualWorkingHours = establishmentPageObject.getWorkingHours();
        //then
        Assertions.assertEquals(ESTABLISHMENT_PHONE_NUMBER, actualPhoneNumber);
        Assertions.assertEquals(ESTABLISHMENT_ADDRESS, actualAddress);
        Assertions.assertEquals(ESTABLISHMENT_WORKING_HOURS, actualWorkingHours);
    }

}
