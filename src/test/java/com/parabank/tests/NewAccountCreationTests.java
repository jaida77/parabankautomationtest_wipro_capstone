package com.parabank.tests;

import com.parabank.pages.*;
import com.parabank.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(com.parabank.listeners.TestListener.class)
public class NewAccountCreationTests extends BaseTest {

    @DataProvider(name = "policyTypes")
    public Object[][] policies(){
        return new Object[][]{
                {"SAVINGS"}, {"CHECKING"}
        };
    }

    @Test(dataProvider = "policyTypes", description = "Simulate policy creation via Open New Account")
    public void createPolicy(String type){
        LoginPage login = new LoginPage(driver);
        login.login(ConfigReader.get("validUsername"), ConfigReader.get("validPassword"));
        HomePage home = new HomePage(driver);
        home.goToOpenAccount();
        OpenAccountPage open = new OpenAccountPage(driver);
        open.openNewAccount(type);
        Assert.assertTrue(open.isSuccess(), "Policy (account) should be opened for type: " + type);
    }
}
