package com.example.testing;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.io.File;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class ExampleTest {
    WebDriver driver;
    @BeforeSuite
    public void beforeSuit() {
    	System.out.println("Suite is Running");
    }

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("--headless");

        driver = new ChromeDriver(options);
        //driver = new ChromeDriver();
        //driver.manage().window().maximize();
    }
    @BeforeMethod
    public void beforeMethod() {
    	driver.manage().window().maximize();
    }
    
    @DataProvider(name = "TestLoginData")
    public Object[][] TestLoginData() {
        return new Object[][] {
            {"standard_user", "secret_sauce"}
        };
    }

    @Test(priority = 1, groups = "smoke")
    public void openGoogle() {
        driver.get("https://www.google.com");
        System.out.println("Title: " + driver.getTitle());
    }

    @Test(priority = 2)
    public void search() throws InterruptedException {
        WebElement e = driver.findElement(By.xpath("//*[@title = 'Search']"));
        e.sendKeys("hello");
        e.sendKeys(Keys.ENTER);
        Thread.sleep(2000);
        System.out.println("searched");
    }

    @Test(priority = 3, dataProvider = "TestLoginData", groups = {"functional"})
    public void checkLogin(String username, String password) {
        driver.get("https://www.saucedemo.com");

        driver.findElement(By.name("user-name")).sendKeys(username);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.id("login-button")).click();

        boolean loginSuccessful = driver.getCurrentUrl().contains("inventory");
        boolean loginFailed = driver.findElements(By.cssSelector(".error-message-container")).size() > 0;

        if (loginSuccessful) {
            System.out.println("Login Success!!!");
            Assert.assertTrue(true, "Login successful");
        } else if (loginFailed) {
            String errorMsg = driver.findElement(By.cssSelector(".error-message-container")).getText();
            System.out.println("Login Failed: " + errorMsg);
            Assert.fail("Login Failed: " + errorMsg);
        } else {
            Assert.fail("Login Failed: Unknown error");
        }
    }
    
    @Test
    public void invalidLogin() {
    	
    	driver.get("https://www.saucedemo.com");
    	driver.findElement(By.name("user-name")).sendKeys("standard_user");
        driver.findElement(By.name("password")).sendKeys("secret_saucade");
        driver.findElement(By.id("login-button")).click();
        
        Assert.assertFalse(driver.getCurrentUrl().contains("inventory"), "Success!! failed");
        System.out.println("working as expected!!");
    	
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
    
}
