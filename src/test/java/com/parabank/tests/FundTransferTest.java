package com.parabank.tests;


import com.parabank.listeners.TestListener;
import com.parabank.pages.LoginPage;
import com.parabank.pages.TransferFundsPage;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(TestListener.class)
public class FundTransferTest extends BaseTest {

    @Test(description = "Perform fund transfer and validate confirmation")
    public void verifyFundTransfer() {
        // Login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("john", "demo");

        // Navigate to Transfer Funds
        TransferFundsPage transferFundsPage = new TransferFundsPage(driver);
        transferFundsPage.open();

        // Perform transfer
        transferFundsPage.transfer("50");

        // Verify confirmation
        String confirmationMsg = transferFundsPage.getConfirmation().toLowerCase();
        Assert.assertTrue(
                confirmationMsg.contains("transfer") || confirmationMsg.contains("complete"),
                "Expected transfer confirmation but got: " + confirmationMsg
        );
    }
}
