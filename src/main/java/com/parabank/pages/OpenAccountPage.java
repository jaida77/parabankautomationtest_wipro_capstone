package com.parabank.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class OpenAccountPage {
    private WebDriver driver;

    private By accountTypeDropdown = By.id("type");
    private By fromAccountDropdown = By.id("fromAccountId");
    private By openNewAccountBtn = By.xpath("//input[@value='Open New Account']");
    private By successMessage = By.xpath("//h1[text()='Account Opened!']");
    private By newAccountId = By.id("newAccountId");

    public OpenAccountPage(WebDriver driver) {
        this.driver = driver;
    }

    public void openNewAccount(String type) {
        driver.findElement(accountTypeDropdown).sendKeys(type);
        driver.findElement(fromAccountDropdown).click();
        driver.findElement(openNewAccountBtn).click();
    }

    public boolean isSuccess() {
        return driver.findElements(successMessage).size() > 0
                && driver.findElements(newAccountId).size() > 0;
    }
}
