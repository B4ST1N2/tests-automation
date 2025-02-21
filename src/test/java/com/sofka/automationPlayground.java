package com.sofka;

import dev.failsafe.internal.util.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

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

        WebElement button = driver.findElement(By.xpath("//button[contains(text(),'Button with Dynamic ID')]"));

        button.click();
        assertEquals("Button with Dynamic ID",button.getText());

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
    void testScrollbars() throws InterruptedException {
        WebElement Scrollbars = driver.findElement(By.xpath("//*[@id=\"overview\"]/div/div[3]/div[1]/h3/a"));
        Scrollbars.click();

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("let scrollContainer = document.querySelector('div[style=\"height:150px;overflow-y: scroll;width:300px;overflow-x:scroll\"]');" +
                "scrollContainer.scrollTop = scrollContainer.scrollHeight;" +
                "scrollContainer.scrollLeft = scrollContainer.scrollWidth;");

        WebElement botton = driver.findElement(By.xpath("//*[@id=\"hidingButton\"]"));
        botton.click();

        Thread.sleep(2000);

        assertTrue(botton.isDisplayed());
    }

    @Test
    void testMouseOver() {
        WebElement mouseOver = driver.findElement(By.xpath("//*[@id=\"overview\"]/div/div[4]/div[3]/h3/a"));
        mouseOver.click();

        WebElement link = driver.findElement(By.xpath("/html/body/section/div/div[1]/a"));


        WebElement clickCount = driver.findElement(By.xpath("//*[@id=\"clickCount\"]"));
        int countBeforeSecondClick = Integer.parseInt(clickCount.getText());

        link.click();

        WebElement updatedClickCount = driver.findElement(By.xpath("//*[@id=\"clickCount\"]"));
        int countAfterSecondClick = Integer.parseInt(updatedClickCount.getText());

        assertEquals(countBeforeSecondClick + 1, countAfterSecondClick);
    }

    @Test
    void testVerifyText(){
        WebElement verifyText = driver.findElement(By.xpath("//*[@id=\"overview\"]/div/div[3]/div[3]/h3/a"));
        verifyText.click();

        WebElement text = driver.findElement(By.xpath("//span[@class='badge-secondary' and contains(text(), 'Welcome')]"));

        String actualText = text.getText().trim();

        assertTrue(actualText.contains("Welcome"));
    }

    @Test
    void testProgressBar(){
        WebElement progressBartest = driver.findElement(By.xpath("//*[@id=\"overview\"]/div/div[3]/div[4]/h3/a"));
        progressBartest.click();

        WebElement startButton = driver.findElement(By.id("startButton"));
        startButton.click();

        // Esperar a que la barra de progreso alcance al menos el 75%
        WebElement progressBar = driver.findElement(By.id("progressBar"));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.attributeToBe(progressBar, "aria-valuenow", "75"));

        // Obtener el valor de la barra de progreso
        String progressValue = progressBar.getAttribute("aria-valuenow");
        System.out.println("Valor de la barra de progreso: " + progressValue);

        // Hacer clic en el botón "Detener"
        WebElement stopButton = driver.findElement(By.id("stopButton"));
        stopButton.click();

        // Verificar que el valor de la barra de progreso está lo más cerca posible de 75%
        int progressInt = Integer.parseInt(progressValue);
        int expectedProgress = 75;

        // Comparar la diferencia y determinar si la prueba pasa
        int difference = Math.abs(progressInt - expectedProgress);
        assertTrue(difference <= 5, "La diferencia entre el valor final y 75% es demasiado grande.");


    }

    @Test
    void testVisibility() {
        WebElement visibility = driver.findElement(By.xpath("//*[@id=\"overview\"]/div/div[4]/div[1]/h3/a"));
        visibility.click();

        WebElement hideButton = driver.findElement(By.id("hideButton"));
        hideButton.click(); // Simulate click on the "Hide" button

        // Wait for the button to disappear (this is a better approach)
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("removedButton")));



        // Now verify if the element is no longer present
        assertFalse(isElementPresent(By.id("removedButton")), "The 'Removed' button should be removed.");
    }

    public boolean isElementPresent(By locator) {
        try {
            List<WebElement> elements = driver.findElements(locator);
            return !elements.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    @Test
    void testAlerts(){
        WebElement alerts = driver.findElement(By.xpath("//*[@id=\"overview\"]/div/div[5]/div[3]/h3/a"));
        alerts.click();

        // Test for Alert
        WebElement alertButton = driver.findElement(By.id("alertButton"));
        alertButton.click(); // Trigger Alert
        Alert alert = driver.switchTo().alert();
        assertEquals("Today is a working day.\nOr less likely a holiday.", alert.getText());
        alert.accept(); // Accept the alert

        // Test for Confirm
        WebElement confirmButton = driver.findElement(By.id("confirmButton"));
        confirmButton.click(); // Trigger Confirm
        alert = driver.switchTo().alert();
        assertTrue(alert.getText().contains("Today is Friday"));
        alert.accept(); // Click OK to confirm

        // Test for Prompt
        WebElement promptButton = driver.findElement(By.id("promptButton"));
        promptButton.click(); // Trigger Prompt
        alert = driver.switchTo().alert();
        alert.sendKeys("cats"); // Input value into the prompt
        alert.accept(); // Accept the prompt

    }

    @Test
    void testSampleApp() throws InterruptedException {
        WebElement sampleApp = driver.findElement(By.xpath("//*[@id=\"overview\"]/div/div[4]/div[2]/h3/a"));
        sampleApp.click();

        // Localizar los elementos de entrada (usuario y contraseña)
        WebElement userNameField = driver.findElement(By.xpath("//*[@placeholder='User Name']"));
        WebElement passwordField = driver.findElement(By.xpath("//*[@name='Password']"));
        WebElement loginButton = driver.findElement(By.id("login"));


        // Ingresar las credenciales correctas
        userNameField.sendKeys("usuarioValido");
        passwordField.sendKeys("pwd");

        // Hacer clic en el botón de login
        loginButton.click();
        Thread.sleep(2000);

        // Verificar el estado de inicio de sesión
        WebElement loginStatus = driver.findElement(By.id("loginstatus"));
        assertEquals("Welcome, usuarioValido!", loginStatus.getText());
    }

    @Test
    public void testLoginFailure() {
        WebElement sampleApp = driver.findElement(By.xpath("//*[@id=\"overview\"]/div/div[4]/div[2]/h3/a"));
        sampleApp.click();

        WebElement userNameField = driver.findElement(By.xpath("//*[@placeholder='User Name']"));
        WebElement passwordField = driver.findElement(By.xpath("//*[@name='Password']"));
        WebElement loginButton = driver.findElement(By.id("login"));

        // Ingresar credenciales incorrectas
        userNameField.sendKeys("usuarioIncorrecto");
        passwordField.sendKeys("wrongPassword");

        // Hacer clic en el botón de login
        loginButton.click();

        // Verificar que el estado de inicio de sesión indique un error
        WebElement loginStatus = driver.findElement(By.id("loginstatus"));
        assertEquals("Invalid username/password", loginStatus.getText());
    }

    @AfterEach
    public void tearDown() {driver.quit();}
}
