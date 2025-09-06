package com.parabank.tests;

import com.parabank.core.WebDriverFactory;
import com.parabank.utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;

    @Parameters({"browser"})
    @BeforeMethod(alwaysRun = true)
    public void setUp(@Optional String paramBrowser){
        String browser = (paramBrowser == null || paramBrowser.isEmpty()) ? ConfigReader.get("browser") : paramBrowser;
        boolean headless = ConfigReader.getBoolean("headless");
        driver = WebDriverFactory.createInstance(browser, headless);
        int implicit = ConfigReader.getInt("implicitWait", 10);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicit));
        driver.manage().window().maximize();
        driver.get(ConfigReader.get("baseUrl"));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(){
        if (driver != null) driver.quit();
    }
}
