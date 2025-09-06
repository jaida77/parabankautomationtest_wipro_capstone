package com.parabank.tests;


import com.parabank.pages.ActivityPage;
import com.parabank.pages.LoginPage;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.parabank.listeners.TestListener;

@Listeners(TestListener.class)
public class TransactionHistoryTests extends BaseTest {

    @Test(description = "Open first account activity and verify transaction history navigation")
    public void verifyTransactionHistory() {
        // Step 1: Login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("john", "demo");

        // Step 2: Navigate to first account activity
        ActivityPage activityPage = new ActivityPage(driver);
        activityPage.openFirstAccount();

        // Step 3: Verify page title contains "Account"
        String title = activityPage.getTitle().toLowerCase();
        Assert.assertTrue(
                title.contains("account"),
                "Expected account activity page but got: " + title
        );

        // Step 4: Click the "Funds Transfer Received" transaction link (first row, second column)
        By transactionLink = By.xpath("//*[@id='transactionTable']/tbody/tr/td[2]/a");
        driver.findElement(transactionLink).click();
        
        // Step 5: Verify that transaction details page loaded
        String pageSource = driver.getPageSource().toLowerCase();
        Assert.assertTrue(
                pageSource.contains("transaction"),
                "Expected transaction details page but content was: " + pageSource.substring(0, 200)
        );
    }
}
