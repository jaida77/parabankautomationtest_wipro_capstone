package com.parabank.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BillPayPage {
    private WebDriver driver;

    private By payeeName = By.name("payee.name");
    private By payeeAccount = By.name("payee.accountNumber");
    private By verifyAccount = By.name("verifyAccount");
    private By amount = By.name("amount");
    private By billPayButton = By.xpath("//input[@value='Send Payment']");
    private By successMessage = By.xpath("//h1[text()='Bill Payment Complete']");
    private By failureMessage = By.cssSelector(".error");

    public BillPayPage(WebDriver driver) {
        this.driver = driver;
    }

    public void sendPayment(String name, String acct, String verifyAcct, String amt) {
        driver.findElement(payeeName).clear();
        driver.findElement(payeeName).sendKeys(name);
        driver.findElement(payeeAccount).clear();
        driver.findElement(payeeAccount).sendKeys(acct);
        driver.findElement(verifyAccount).clear();
        driver.findElement(verifyAccount).sendKeys(verifyAcct);
        driver.findElement(amount).clear();
        driver.findElement(amount).sendKeys(amt);
        driver.findElement(billPayButton).click();
    }

    public boolean isPaymentSuccessful() {
        return driver.findElements(successMessage).size() > 0;
    }

    public boolean isPaymentFailed() {
        return driver.findElements(failureMessage).size() > 0;
    }
}
