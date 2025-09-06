package com.parabank.tests;

import com.parabank.pages.*;
import com.parabank.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(com.parabank.listeners.TestListener.class)
public class RenewalTests extends BaseTest {

    @Test(description = "Simulate renewal by opening another account (policy) and verifying overview")
    public void renewPolicy() throws InterruptedException{
        LoginPage login = new LoginPage(driver);
        login.login(ConfigReader.get("validUsername"), ConfigReader.get("validPassword"));
        HomePage home = new HomePage(driver);
        home.goToOpenAccount();
        OpenAccountPage open = new OpenAccountPage(driver);
        open.openNewAccount("SAVINGS");
        Assert.assertTrue(open.isSuccess(), "Renewal (new policy) should succeed");
        home.goToAccountsOverview();
        AccountOverviewPage overview = new AccountOverviewPage(driver);
        Assert.assertTrue(overview.hasAccounts(), "Accounts overview should list accounts (policies)");
        Thread.sleep(2000);
    }
}
