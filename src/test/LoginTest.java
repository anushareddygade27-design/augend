package test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import LoginPage;



public class LoginTest {

    WebDriver driver;
    LoginPage loginPage;

    @BeforeMethod
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", 
            "C:\\Users\\AnushaReddy.Gade2\\Downloads\\chromedriver-win32\\chromedriver-win32\\chromedriver.exe");

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

        loginPage = new LoginPage(driver);
    }

    @Test
public void testSearchLeaveList() throws InterruptedException {
    // Login
    loginPage.login("Admin", "admin123");

    // Navigate to Dashboard and then Leave List
    DashboardPage dashboardPage = new DashboardPage(driver);
    dashboardPage.clickLeaveList();

    // Search leave by date
    LeaveListPage leaveListPage = new LeaveListPage(driver);
    leaveListPage.searchLeaveByDate("2025-08-01"); // Format YYYY-MM-DD (as OrangeHRM expects)
    
    // Assertion (example: check if results table is displayed)
    Assert.assertTrue(driver.getPageSource().contains("No Records Found") 
        || driver.getPageSource().contains("Records"), 
        "Leave List search did not return expected results.");
}


    @Test
    public void testValidLogin() throws InterruptedException {
        loginPage.login("Admin", "admin123");
        Thread.sleep(3000);
        Assert.assertTrue(driver.getCurrentUrl().contains("dashboard"),
                "Login failed! Not redirected to dashboard.");
    }

    @Test
    public void testInvalidLogin() throws InterruptedException {
        loginPage.login("WrongUser", "WrongPass");
        Thread.sleep(2000);
        Assert.assertTrue(loginPage.getErrorMessage().contains("Invalid credentials"),
                "Error message not displayed for invalid login.");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
