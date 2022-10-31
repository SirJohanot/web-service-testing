package com.innowise.googletranslate.pageobject;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class GoogleTranslateTest {

    private WebDriver driver;
    private GoogleTranslatePageObject pageObject;

    @Step("Launch the web browser and go to Google Translate")
    @BeforeEach
    public void setup() {
        driver = new ChromeDriver();
        driver.get("https://translate.google.com/");
        pageObject = new GoogleTranslatePageObject(driver);
    }

    @Step("Leave Google Translate and quit the web browser")
    @AfterEach
    public void tearDown() {
        pageObject = null;
        driver.quit();
    }

    @Test
    @Story("User tries to translate 'Test' from English to Russian")
    @Description("Translation should properly translate from english to russian")
    public void testTranslateShouldTranslateFromEnglishToRussian() {
        //given
        String sourceLanguage = "Russian";
        String targetLanguage = "English";
        String sourceText = "Test";
        String expectedTranslation = "Тест";
        //when
        pageObject.setSourceLanguage(targetLanguage);
        pageObject.setTargetLanguage(sourceLanguage);
        pageObject.enterSourceText(sourceText);
        String actualTranslation = pageObject.getTranslatedText();
        //then
        Assertions.assertEquals(expectedTranslation, actualTranslation);
    }

}
