package com.parabank.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement; // Added for clarity
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List; // Added for clarity

// For logging (replace with your preferred logging implementation, e.g., Logback, Log4j2)
// This uses a simple logger for demonstration.
import java.util.logging.Logger;
import java.util.logging.Level;

public class LoginPage {
    // Logger for better debuggability
    private static final Logger logger = Logger.getLogger(LoginPage.class.getName());

    public enum LoginStatus { SUCCESS, ERROR, TIMEOUT }

    // Constants for wait times
    private static final Duration DEFAULT_WAIT_TIMEOUT = Duration.ofSeconds(8);

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators
    private final By usernameField = By.name("username");
    private final By passwordField = By.name("password");
    private final By loginButton = By.xpath("//input[@value='Log In' or @type='submit']");
    private final By logoutLink = By.linkText("Log Out");
    private final By errorMessage = By.cssSelector("p.error, .error");

    /**
     * Constructor for LoginPage.
     * @param driver The WebDriver instance.
     */
    public LoginPage(WebDriver driver) {
        this(driver, DEFAULT_WAIT_TIMEOUT);
    }

    /**
     * Constructor for LoginPage with a custom wait timeout.
     * @param driver The WebDriver instance.
     * @param waitTimeout The duration for explicit waits.
     */
    public LoginPage(WebDriver driver, Duration waitTimeout) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, waitTimeout);
        logger.log(Level.INFO, "LoginPage initialized with wait timeout: {0} seconds", waitTimeout.getSeconds());
    }

    /**
     * Enters username and password and clicks the login button.
     * @param user The username to enter.
     * @param pass The password to enter.
     */
    public void login(String user, String pass) {
        logger.log(Level.INFO, "Attempting to input credentials for user: {0}", user);
        driver.findElement(usernameField).clear();
        driver.findElement(usernameField).sendKeys(user);
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(pass);
        driver.findElement(loginButton).click();
        logger.log(Level.INFO, "Login button clicked.");
    }

    /**
     * Performs a login attempt and waits for either a successful login (logout link)
     * or an error message to appear.
     * @param user The username.
     * @param pass The password.
     * @return The LoginStatus (SUCCESS, ERROR, or TIMEOUT).
     */
    public LoginStatus performLoginAndWait(String user, String pass) {
        logger.log(Level.INFO, "Performing login and waiting for outcome for user: {0}", user);
        login(user, pass);

        try {
            logger.log(Level.FINE, "Waiting for logout link or error message to be present...");
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.presenceOfElementLocated(logoutLink),
                    ExpectedConditions.presenceOfElementLocated(errorMessage)
            ));
            logger.log(Level.FINE, "Wait condition met.");
        } catch (TimeoutException e) {
            logger.log(Level.WARNING, "Login outcome wait timed out after {0} seconds for user: {1}",
                    new Object[]{DEFAULT_WAIT_TIMEOUT.getSeconds(), user});
            return LoginStatus.TIMEOUT;
        }

        // Check for presence of elements after the wait.
        // Using findElements to avoid NoSuchElementException if not present.
        List<WebElement> logoutLinks = driver.findElements(logoutLink);
        List<WebElement> errorMessages = driver.findElements(errorMessage);

        boolean hasLogout = !logoutLinks.isEmpty(); // More idiomatic check
        boolean hasError = !errorMessages.isEmpty(); // More idiomatic check

        logger.log(Level.INFO, "Login result for user {0}: hasLogout = {1}, hasError = {2}",
                new Object[]{user, hasLogout, hasError});

        // 🟢 Priority: SUCCESS wins if a logout link is found.
        if (hasLogout) {
            logger.log(Level.INFO, "Login successful (logout link found) for user: {0}", user);
            return LoginStatus.SUCCESS;
        } else if (hasError) {
            logger.log(Level.WARNING, "Login failed (error message found) for user: {0}", user);
            // Optionally, log the error message text
            if (errorMessages.get(0).isDisplayed()) {
                 logger.log(Level.WARNING, "Error message text: {0}", errorMessages.get(0).getText());
            }
            return LoginStatus.ERROR;
        } else {
            // This case should ideally not be reached if the wait was successful,
            // but serves as a robust fallback.
            logger.log(Level.SEVERE, "Unexpected state: Wait condition met, but neither logout link nor error message found for user: {0}", user);
            return LoginStatus.TIMEOUT; // Fallback to TIMEOUT if no clear outcome
        }
    }

    /**
     * Checks if the user is currently logged in by looking for the logout link.
     * @return true if the logout link is present, false otherwise.
     */
    public boolean isLoggedIn() {
        boolean loggedIn = !driver.findElements(logoutLink).isEmpty();
        logger.log(Level.FINE, "Is user logged in? {0}", loggedIn);
        return loggedIn;
    }

    /**
     * Checks if an error message is currently visible on the page.
     * @return true if an error message element is present, false otherwise.
     */
    public boolean isErrorVisible() {
        boolean errorVisible = !driver.findElements(errorMessage).isEmpty();
        logger.log(Level.FINE, "Is error message visible? {0}", errorVisible);
        return errorVisible;
    }

    /**
     * Attempts to log out if the user is currently logged in.
     * Catches and logs any exceptions during logout.
     */
    public void logout() {
        if (isLoggedIn()) {
            try {
                logger.log(Level.INFO, "Attempting to log out.");
                driver.findElement(logoutLink).click();
                logger.log(Level.INFO, "Logout link clicked.");
                // Optionally wait for the login page to reappear or logout message
                // wait.until(ExpectedConditions.presenceOfElementLocated(usernameField));
            } catch (Exception e) {
                // Log the exception for debugging instead of ignoring it
                logger.log(Level.WARNING, "Failed to click logout link or an error occurred during logout: " + e.getMessage(), e);
            }
        } else {
            logger.log(Level.INFO, "Not logged in, no need to perform logout.");
        }
    }
}