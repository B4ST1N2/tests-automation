package com.sofka;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class YahooSearchTest {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromeDriver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://espanol.yahoo.com/");
    }

    @Test
    public void testYahooSearch() {
        WebElement cajaBusqueda = driver.findElement(By.xpath("//*[@id=\"ybar-sbq\"]"));
        cajaBusqueda.clear();
        cajaBusqueda.sendKeys("Ipad Air"); // Writing in
        cajaBusqueda.submit(); // Search
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        assertEquals("Ipad Air - Yahoo Search Tus resultados", driver.getTitle());
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

}
