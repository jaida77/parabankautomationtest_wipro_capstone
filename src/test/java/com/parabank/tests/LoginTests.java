package com.parabank.tests;

import com.parabank.pages.LoginPage;
import com.parabank.pages.LoginPage.LoginStatus;
import com.parabank.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(com.parabank.listeners.TestListener.class)
public class LoginTests extends BaseTest { // Assuming BaseTest correctly initializes 'driver'

    @Test(description = "Verify valid login attempt")
    public void validLogin() {
        LoginPage login = new LoginPage(driver);

        // Read credentials from config (UPDATE YOUR config.properties if these don't work)
        String validUser = ConfigReader.get("validUsername");
        String validPass = ConfigReader.get("validPassword");

        // IMPORTANT: Add logging here to see what credentials are being used
        System.out.println("Attempting valid login with user: " + validUser);

        // Perform login and wait for outcome
        LoginStatus status = login.performLoginAndWait(validUser, validPass);

        Assert.assertEquals(status, LoginStatus.SUCCESS, "Valid login should succeed.");
        // Logout only if we actually logged in
        if (login.isLoggedIn()) {
            System.out.println("Valid login successful, logging out.");
            login.logout();
        } else {
            System.out.println("Valid login failed, status: " + status);
        }
    }

    @Test(description = "Verify invalid login attempt (expect ERROR)")
    public void invalidLogin() {
        LoginPage login = new LoginPage(driver);

        // Use known-bad credentials
        String invalidUser = "invalid_user_" + System.currentTimeMillis(); // Use unique invalid user
        String invalidPass = "badpass_" + System.currentTimeMillis();     // Use unique invalid pass
        System.out.println("Attempting invalid login with user: " + invalidUser);

        LoginStatus status = login.performLoginAndWait(invalidUser, invalidPass);

        // Expect an explicit ERROR outcome
        Assert.assertEquals(status, LoginStatus.ERROR, "Invalid login should show an error.");

        // Also assert we are not logged in
        Assert.assertFalse(login.isLoggedIn(), "Invalid login should not result in being logged in.");
        System.out.println("Invalid login attempted, status: " + status + ". User not logged in: " + !login.isLoggedIn());
    }
}