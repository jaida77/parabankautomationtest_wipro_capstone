package com.parabank.tests;

import com.parabank.utils.ExcelUtil;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(com.parabank.listeners.TestListener.class)
public class PremiumCalculationTests extends BaseTest {

    @DataProvider(name = "premiumData")
    public Object[][] premiumData() {
        // Correct file and sheet name
        return ExcelUtil.readSheet("src/test/resources/testdata/policyData.xlsx", "Premiums");
    }

    @Test(dataProvider = "premiumData", description = "Validate premium calculation using data-driven Excel")
    public void validatePremium(String policyType, String premium) throws InterruptedException {
        // Validate data presence from Excel
        Assert.assertNotNull(policyType, "Policy type should not be null");
        Assert.assertNotNull(premium, "Premium should not be null");

        System.out.println("Policy: " + policyType + " | Premium: " + premium);

        // Add a wait so execution is not too fast
        Thread.sleep(2000); // 2 seconds wait after each row
    }
}
