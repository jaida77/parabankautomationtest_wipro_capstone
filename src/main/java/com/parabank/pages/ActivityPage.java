package com.parabank.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ActivityPage {
    private WebDriver driver;

    private By firstAccountLink = By.cssSelector("#accountTable tbody tr td a");
    private By pageTitle = By.tagName("h1");

    public ActivityPage(WebDriver driver) {
        this.driver = driver;
    }

    // Open the first account in the Accounts Overview table
    public void openFirstAccount() {
        driver.findElement(firstAccountLink).click();
    }

    // Get the page title text
    public String getTitle() {
        return driver.findElement(pageTitle).getText();
    }
}
