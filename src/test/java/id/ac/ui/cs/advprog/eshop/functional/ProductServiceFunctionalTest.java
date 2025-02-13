package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
public class ProductServiceFunctionalTest {
    /**
     * The port number assigned to the running application during test execution.
     * Set automatically during each test run by Spring Framework's test context.
     */
    @LocalServerPort
    private int serverPort;

    /**
     * the base URL for testing. Default to code {@code http://localhost}.
     */
    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setupTest(){
        baseUrl = String.format("%s:%d/product/list", testBaseUrl, serverPort);
    }

    @Test
    void createProductValid(ChromeDriver driver) throws Exception {

        // Exercise
        driver.get(baseUrl);

        // List Page
        WebElement createButton = driver.findElement(By.tagName("a"));

        createButton.click();

        // Create Page
        WebElement submitButton = driver.findElement(By.xpath("//button[@type='submit']"));
        WebElement nameField = driver.findElement(By.id("nameInput"));
        WebElement QuantityField = driver.findElement(By.id("quantityInput"));

        nameField.sendKeys("mobil");
        QuantityField.sendKeys(Keys.BACK_SPACE);
        QuantityField.sendKeys("10");
        submitButton.click();

        List<WebElement> productField = driver.findElements(By.tagName("td"));
        String productName = productField.get(0).getText();
        String productQuantity = productField.get(1).getText();

        // Assert Value
        assertEquals(driver.getCurrentUrl(), baseUrl);
        assertEquals("mobil", productName);
        assertEquals("10", productQuantity);
    }

    @Test
    void createProductNotValid(ChromeDriver driver) throws Exception {

        // Exercise
        driver.get(baseUrl);

        // List Page
        WebElement createButton = driver.findElement(By.tagName("a"));

        createButton.click();

        // Create Page
        WebElement submitButton = driver.findElement(By.xpath("//button[@type='submit']"));
        WebElement nameField = driver.findElement(By.id("nameInput"));
        WebElement QuantityField = driver.findElement(By.id("quantityInput"));

        nameField.sendKeys("mobil");
        QuantityField.sendKeys(Keys.BACK_SPACE);
        QuantityField.sendKeys("-1");
        submitButton.click();


        // Assert Value
        assertNotNull(driver.findElement(By.id("errorAlert")));
    }


}
