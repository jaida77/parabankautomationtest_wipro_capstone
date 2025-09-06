package com.parabank.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {
    private WebDriver driver;

    private By billPayLink = By.linkText("Bill Pay");
    private By openAccountLink = By.linkText("Open New Account");
    private By accountsOverviewLink = By.linkText("Accounts Overview");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void goToBillPay() {
        driver.findElement(billPayLink).click();
    }

    public void goToOpenAccount() {
        driver.findElement(openAccountLink).click();
    }

    public void goToAccountsOverview() {
        driver.findElement(accountsOverviewLink).click();
    }
}
