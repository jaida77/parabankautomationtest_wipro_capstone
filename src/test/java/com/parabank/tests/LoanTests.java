package com.parabank.tests;


import com.parabank.listeners.TestListener;
import com.parabank.pages.LoginPage;
import com.parabank.pages.RequestLoanPage;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(TestListener.class)
public class LoanTests extends BaseTest {

    @Test(description = "Apply for loan and verify approval/rejection")
    public void verifyLoanRequest() {
        // Step 1: Login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("john", "demo");

        // Step 2: Navigate to Request Loan Page
        RequestLoanPage loanPage = new RequestLoanPage(driver);
        loanPage.open();

        // Step 3: Apply for a loan
        loanPage.apply("5000", "500");

        // Step 4: Capture loan status
        String statusMsg = loanPage.status().toLowerCase();
        System.out.println("Loan status: " + statusMsg);

        // Step 5: Verify approval or rejection
        Assert.assertTrue(
                statusMsg.contains("approved") || statusMsg.contains("denied") || statusMsg.contains("declined"),
                "Unexpected loan status: " + statusMsg
        );
    }
}
