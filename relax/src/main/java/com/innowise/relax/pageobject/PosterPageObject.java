package com.innowise.relax.pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class PosterPageObject extends AbstractPageObject {

    private static final By POSTER_CARD_BY = By.xpath("//*[@id=\"append-shcedule\"]//div[@class='b-afisha-layout_strap--item']");
    private static final By CLOSE_AD_BUTTON_BY = By.xpath("//div[@class='bb17e40e0']");

    public PosterPageObject(WebDriver driver) {
        super(driver);
    }

    @Step("Open {cardNumber} card of poster")
    public PosterEventPageObject openCardOfPoster(int cardNumber) {
        List<WebElement> cards = driver.findElements(POSTER_CARD_BY);
        WebElement card = cards.get(cardNumber - 1);
        card.click();
        return new PosterEventPageObject(driver);
    }

    @Step("Close advertisement")
    public void closeAdvertisement() {
        clickButton(CLOSE_AD_BUTTON_BY);
    }
}
