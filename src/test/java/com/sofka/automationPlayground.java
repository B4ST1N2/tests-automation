package com.sofka;

import dev.failsafe.internal.util.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;


public class automationPlayground {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        // Configurar opciones para Chrome
        ChromeOptions options = new ChromeOptions();

        // Desactivar las advertencias de sitio no seguro
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--disable-web-security");
        options.addArguments("--allow-insecure-localhost");

        // Establecer el controlador de Chrome con las opciones configuradas
        System.setProperty("webdriver.chrome.driver", "./src/test/resources/chromeDriver/chromedriver.exe");
        driver = new ChromeDriver(options);

        driver.manage().window().maximize();
        driver.get("https://www.uitestingplayground.com/home");
    }

    @Test
    public void testDynamicId() {
        WebElement DynamicId = driver.findElement(By.xpath("//*[@id=\"overview\"]/div/div[1]/div[1]/h3/a"));
        DynamicId.click();
        WebElement button = driver.findElement(By.id("f81bfde0-388b-c764-02ca-391819cabb15"));
        assertEquals("Button with Dynamic ID", button);
    }

    @Test
    void testclassAttribute() throws InterruptedException {
        WebElement classAttribute = driver.findElement(By.xpath("//*[@id=\"overview\"]/div/div[1]/div[2]/h3/a"));
        classAttribute.click();

        WebElement blueBotton = driver.findElement(By.xpath("//button[contains(concat(' ', normalize-space(@class), ' '), ' btn-primary ')]"));
        blueBotton.click();

        Thread.sleep(2000);

        // Switch to the alert and accept it
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }

    @Test
    void testHiddenLayers(){
        WebElement hiddenLayers = driver.findElement(By.xpath("//*[@id=\"overview\"]/div/div[1]/div[3]/h3/a"));
        hiddenLayers.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        WebElement button = driver.findElement(By.xpath("//*[@id=\"blueButton\"]"));
        boolean isButtonClickableAfterFirstClick = wait.until(ExpectedConditions.elementToBeClickable(button)).isDisplayed();//Check if the button is still displayed(interactable) after click

        // Assert the expected behavior.  If the button *should* become inactive:
        assertFalse(isButtonClickableAfterFirstClick);

    }

    @Test
    void testLoadDelay(){
        WebElement loadDelay = driver.findElement(By.xpath("//*[@id=\"overview\"]/div/div[1]/div[4]/h3/a"));
        loadDelay.click();

        WebElement button = driver.findElement(By.xpath("/html/body/section/div/button"));
        button.click();

        assertEquals("Button Appearing After Delay",button.getText());
    }

    @Test
    void testAjaxData(){
        WebElement ajaxData = driver.findElement(By.xpath("//*[@id=\"overview\"]/div/div[2]/div[1]/h3/a"));
        ajaxData.click();

        WebElement button = driver.findElement(By.xpath("//*[@id=\"ajaxButton\"]"));
        button.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(16));
        WebElement text = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"content\"]/p")));
        assertEquals("Data loaded with AJAX get request.",text.getText());

    }

    @Test
    void testClientSideDelay() {
        WebElement clientSideDelay = driver.findElement(By.xpath("//*[@id=\"overview\"]/div/div[2]/div[2]/h3/a"));
        clientSideDelay.click();

        WebElement button = driver.findElement(By.xpath("//*[@id=\"ajaxButton\"]"));
        button.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(16));

        WebElement labelText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"content\"]/p")));

        assertEquals("Data calculated on the client side.",labelText.getText());
        assertNotNull(labelText);
    }

    @Test
    void click(){
        WebElement click = driver.findElement(By.xpath("//*[@id=\"overview\"]/div/div[2]/div[3]/h3/a"));
        click.click();

        WebElement button = driver.findElement(By.xpath("//*[@id=\"badButton\"]"));

        String initialBackgroundColor = button.getAttribute("class");

        button.click();

        String updatedBackgroundColor = button.getAttribute("class");

        assertNotEquals(initialBackgroundColor, updatedBackgroundColor);
    }

    @Test
    void testTextInput() throws InterruptedException {
        WebElement textInput = driver.findElement(By.xpath("//*[@id=\"overview\"]/div/div[2]/div[4]/h3/a"));
        textInput.click();

        WebElement text = driver.findElement(By.xpath("//*[@id=\"newButtonName\"]"));
        text.clear();
        text.sendKeys("Lewys es el mejor");

        WebElement button = driver.findElement(By.xpath("//*[@id=\"updatingButton\"]"));
        button.click();
        Thread.sleep(2000);

        assertEquals("Lewys es el mejor",button.getText());
    }

    @Test
    void testScrollbars(){
        WebElement Scrollbars = driver.findElement(By.xpath("//*[@id=\"overview\"]/div/div[3]/div[1]/h3/a"));
        Scrollbars.click();
    }

    @Test
    void testDynamicTable(){
        WebElement dynamicTable = driver.findElement(By.xpath("//*[@id=\"overview\"]/div/div[3]/div[2]/h3/a"));
        dynamicTable.click();
    }

    @Test
    void testVerifyText(){
        WebElement verifyText = driver.findElement(By.xpath("//*[@id=\"overview\"]/div/div[3]/div[3]/h3/a"));
        verifyText.click();
    }

    @Test
    void testProgressBar(){
        WebElement progressBar = driver.findElement(By.xpath("//*[@id=\"overview\"]/div/div[3]/div[4]/h3/a"));
        progressBar.click();
    }

    @Test
    void testVisibility(){
        WebElement visibility = driver.findElement(By.xpath("//*[@id=\"overview\"]/div/div[4]/div[1]/h3/a"));
        visibility.click();
    }

    @Test
    void testAlerts(){
        WebElement alerts = driver.findElement(By.xpath("//*[@id=\"overview\"]/div/div[5]/div[3]/h3/a"));
        alerts.click();
    }

    @Test
    void testSampleApp(){
        WebElement sampleApp = driver.findElement(By.xpath("//*[@id=\"overview\"]/div/div[4]/div[2]/h3/a"));
        sampleApp.click();
    }

    @AfterEach
    public void tearDown() {driver.quit();}
}
