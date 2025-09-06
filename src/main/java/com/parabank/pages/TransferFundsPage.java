package com.parabank.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TransferFundsPage {
    private final WebDriver driver;

    // Locators
    private final By transferFundsLink = By.linkText("Transfer Funds");
    private final By amountField = By.id("amount");
    private final By fromAccountDropdown = By.id("fromAccountId");
    private final By toAccountDropdown = By.id("toAccountId");
    private final By transferButton = By.cssSelector("input[value='Transfer']");
    private final By confirmationMessage = By.cssSelector("#rightPanel .title");

    public TransferFundsPage(WebDriver driver) {
        this.driver = driver;
    }

    // Navigate to the Transfer Funds page
    public void open() {
        driver.findElement(transferFundsLink).click();
    }

    // Perform a transfer
    public void transfer(String amount) {
        driver.findElement(amountField).clear();
        driver.findElement(amountField).sendKeys(amount);
        // fromAccount & toAccount dropdowns can be handled with Select if needed
        driver.findElement(transferButton).click();
    }

    // Get the confirmation text
    public String getConfirmation() {
        return driver.findElement(confirmationMessage).getText();
    }
}
