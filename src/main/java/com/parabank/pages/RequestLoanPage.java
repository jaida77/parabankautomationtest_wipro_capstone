package com.parabank.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RequestLoanPage {
    private final WebDriver driver;

    private final By requestLoanLink = By.linkText("Request Loan");
    private final By loanAmount = By.id("amount");
    private final By downPayment = By.id("downPayment");
    private final By applyBtn = By.cssSelector("input[value='Apply Now']");
    private final By statusMessage = By.xpath("//*[@id='loanStatus']");

    public RequestLoanPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.findElement(requestLoanLink).click();
    }

    public void apply(String amount, String downPay) {
        driver.findElement(loanAmount).clear();
        driver.findElement(loanAmount).sendKeys(amount);

        driver.findElement(downPayment).clear();
        driver.findElement(downPayment).sendKeys(downPay);

        driver.findElement(applyBtn).click();
    }

    public String status() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(statusMessage)).getText();
    }
}
