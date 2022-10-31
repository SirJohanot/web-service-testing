package com.innowise.googletranslate.pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class GoogleTranslatePageObject {

    private static final By SOURCE_LANGUAGE_SELECTION_BUTTON_BY = By.xpath("//button[@aria-label='More source languages']");
    private static final By SOURCE_TEXT_TEXTAREA_BY = By.xpath("//textarea[@aria-label='Source text']");
    private static final String SOURCE_LANGUAGE_BUTTON_XPATH = "//*[@id=\"yDmH0d\"]/c-wiz/div/div[2]/c-wiz/div[2]/c-wiz/div[1]/div[1]/c-wiz/div[2]/c-wiz/div[1]/div/div[3]/div/div[3]/div[div='%s']";

    private static final By TARGET_LANGUAGE_SELECTION_BUTTON_BY = By.xpath("//button[@aria-label='More target languages']");
    private static final By TRANSLATED_TEXT_BY = By.xpath("//span[@jsname='W297wb']");
    private static final String TARGET_LANGUAGE_BUTTON_XPATH = "//*[@id=\"yDmH0d\"]/c-wiz/div/div[2]/c-wiz/div[2]/c-wiz/div[1]/div[1]/c-wiz/div[2]/c-wiz/div[2]/div/div[3]/div/div[2]/div[div ='%s']";

    private final WebDriver driver;

    public GoogleTranslatePageObject(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Set the source language to {language}")
    public void setSourceLanguage(String language) {
        setLanguage(SOURCE_LANGUAGE_SELECTION_BUTTON_BY, SOURCE_LANGUAGE_BUTTON_XPATH, language);
    }

    @Step("Set the target language to {language}")
    public void setTargetLanguage(String language) {
        setLanguage(TARGET_LANGUAGE_SELECTION_BUTTON_BY, TARGET_LANGUAGE_BUTTON_XPATH, language);
    }

    private void setLanguage(By selectionButtonBy, String buttonXpath, String language) {
        WebElement languageSelectionElement = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(selectionButtonBy));
        languageSelectionElement.click();
        String completeLanguageButtonXpath = String.format(buttonXpath, language);
        By languageButtonBy = By.xpath(completeLanguageButtonXpath);
        WebElement languageElement = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(languageButtonBy));
        languageElement.click();
        if (languageElement.isDisplayed()) {
            languageElement.click();
        }
    }

    @Step("Set the source text to {text}")
    public void enterSourceText(String text) {
        WebElement sourceTextElement = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable((SOURCE_TEXT_TEXTAREA_BY)));
        sourceTextElement.sendKeys(text);
    }

    public String getTranslatedText() {
        WebElement translatedTextElement = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(driver -> driver.findElement((TRANSLATED_TEXT_BY)));
        return translatedTextElement.getText();
    }

}
