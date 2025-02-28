package com.example.testing;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
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

    @Test
    public void openGoogle() {
        driver.get("https://www.google.com");
        System.out.println("Title: " + driver.getTitle());
    }
    @Test
    public void search() throws InterruptedException {
    	WebElement e =driver.findElement(By.xpath("//*[@title = 'Search']"));
    	e.sendKeys("hello");
    	e.sendKeys(Keys.ENTER);
    	Thread.sleep(2000);
    	System.out.println("searched");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
