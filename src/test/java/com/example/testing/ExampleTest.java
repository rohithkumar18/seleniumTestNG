package com.example.testing;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

public class ExampleTest {
    WebDriver driver;

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }
    
    
    @DataProvider( name = "TestLoginData")
    public Object[][] TestLoginData(){
    	return new Object[][] {
    		{"standard_user", "secret_sauce"}
    	};
    }
    

    @Test(priority = 1)
    public void openGoogle() {
        driver.get("https://www.google.com");
        System.out.println("Title: " + driver.getTitle());
    }
    @Test(priority = 2)
    public void search() throws InterruptedException {
    	WebElement e =driver.findElement(By.xpath("//*[@title = 'Search']"));
    	e.sendKeys("hello");
    	e.sendKeys(Keys.ENTER);
    	Thread.sleep(2000);
    	System.out.println("searched");
    }
    @Test(priority = 3, dataProvider = "TestLoginData")
    public void checkLogin(String username, String password) {
        driver.get("https://www.saucedemo.com");

        driver.findElement(By.name("user-name")).sendKeys(username);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(By.id("login-button")).click();

        // Check if login is successful or an error message appears
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
    

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
