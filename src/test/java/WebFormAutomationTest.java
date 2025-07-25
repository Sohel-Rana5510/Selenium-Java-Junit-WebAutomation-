import org.junit.jupiter.api.*;

import java.time.Duration;
import java.util.List;

import com.github.javafaker.Faker;
import jdk.jfr.Description;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class WebFormAutomationTest {
    WebDriver driver;
    Faker faker = new Faker();

    public static int generateRandomNumber(int min, int max) {
        double randomNum = Math.random() * (max - min) + min;
        return (int) randomNum;

    }


    @BeforeAll

    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    @Test
    @DisplayName("Fill out the web form and submit it")
    public void fillForm() throws InterruptedException {
        driver.get("https://demo.wpeverest.com/user-registration/guest-registration-form/");
        List<WebElement> tagName = driver.findElements(By.tagName("input"));
        List<WebElement> dateitemSelect = driver.findElements(By.className("input-wrapper"));
        List<WebElement> yearSelect = driver.findElements(By.cssSelector("[type=number]"));
//        List<WebElement> dateSelect = driver.findElements(By.className("flatpickr-day"));
        WebElement occupation = driver.findElement(By.id("textarea_1665630078"));
        WebElement activites = driver.findElement(By.id("select_1665628361"));
        WebElement country = driver.findElement(By.id("country_1665629257"));
        List<WebElement> submitbutton = driver.findElements(By.cssSelector("[type=submit]"));






        tagName.get(4).sendKeys(faker.name().firstName()); // First Name
        tagName.get(7).sendKeys(faker.name().lastName()); // Last Name
        tagName.get(5).sendKeys(faker.name().username()+generateRandomNumber(1000,10000)+"@gmail.com"); // Email
        tagName.get(6).sendKeys(faker.name().username()+generateRandomNumber(1000,10000)+"!$^$"); // Password
        Thread.sleep(1000);
        tagName.get(8).click(); // Click on the checkbox for Gender Selection "Male"
        tagName.get(14).sendKeys("0163673"+generateRandomNumber(1000,10000)); // Phone Number
        tagName.get(15).sendKeys("0174926"+generateRandomNumber(1000,10000)); // Emergency Contact Number
        tagName.get(16).sendKeys("Bangladeshi"); // Nationality
        tagName.get(20).sendKeys("15"); // Intended Length of Stay

        country.click();
        Thread.sleep(2000);
        for (int i = 0; i < 18; i++) {
            country.sendKeys(Keys.ARROW_DOWN); // Navigate down the dropdown list
        }
        country.sendKeys(Keys.ENTER); // Select "Bangladesh" from the dropdown

        // Date of Birth (Selecting Year, Month, and Day)

        // Selecting Month:
        dateitemSelect.get(4).click();
        Thread.sleep(1000);

        WebElement monthDropdown = driver.findElement(By.className("flatpickr-monthDropdown-months"));
        Select selectMonth = new Select(monthDropdown);
        Thread.sleep(1000);
        selectMonth.selectByVisibleText("March");

        // Selecting Year:
        Thread.sleep(1000);
        yearSelect.get(1).sendKeys(Keys.chord(Keys.CONTROL, "a"));
        Thread.sleep(1000);
        yearSelect.get(1).sendKeys(Keys.BACK_SPACE);
        Thread.sleep(1000);
        yearSelect.get(1).sendKeys("1999");
        yearSelect.get(1).sendKeys(Keys.ENTER);

        // Select day by aria-label
        WebElement date = driver.findElement(By.cssSelector("[aria-label='March 3, 1999']")); //
        Thread.sleep(1000);
        date.click();

        // Open second date picker (Date of Arrival)
        dateitemSelect.get(6).click();
        Thread.sleep(1000);

        // Find fresh dropdown for Month
        WebElement monthDropdown2 = driver.findElements(By.className("flatpickr-monthDropdown-months")).get(1); // Get the second one
        Select selectMonth2 = new Select(monthDropdown2);
        selectMonth2.selectByVisibleText("April");

        // Find fresh input for Year
        WebElement yearInput2 = driver.findElements(By.cssSelector("input.cur-year")).get(1);
        yearInput2.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        Thread.sleep(1000);
        yearInput2.sendKeys(Keys.BACK_SPACE);
        Thread.sleep(1000);
        yearInput2.sendKeys("2024");
        yearInput2.sendKeys(Keys.ENTER);

        // Select day by aria-label
        Thread.sleep(1000);
        WebElement date1 = driver.findElement(By.cssSelector("[aria-label='April 20, 2024']"));
        date1.click();

        tagName.get(21).sendKeys("Room No: 101, Bed No: 1"); // Room Details
        occupation.sendKeys("Software Quality Assurance Engineer - Automation Tester, XYZ Company");
        tagName.get(22).click(); // Click on the checkbox for Require Parking "Yes"
        tagName.get(24).click(); // Click on the checkbox for Room Preference "Single Room"
        tagName.get(27).click(); // Click on the checkbox for Dietary restriction "None"
        activites.click(); // Click on the dropdown for Activities
        Thread.sleep(1000);
        activites.sendKeys(Keys.ARROW_DOWN);
        activites.sendKeys(Keys.ARROW_DOWN);
        activites.sendKeys(Keys.ENTER); // Select "Town Hall" from the dropdown

        tagName.get(33).click(); // Click on the checkbox for Terms and Conditions

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,1500)"); // Scroll down to the Submit button
        Thread.sleep(1000);
        submitbutton.get(2).click(); // Click on the Submit button

        Thread.sleep(2000);

        ; // Get the success message text

//        Assertions.assertTrue(msgActual.contains("User successfully registered."));

        WebElement successMsg = driver.findElement(By.id("ur-submit-message-node"));
        String actualMessage = successMsg.getText();
        System.out.println("Actual Message: "+actualMessage);

        Assertions.assertTrue(actualMessage.contains("User successfully registered."),
                "Expected success message was not displayed!");



    }
    @AfterAll
    public void teardown() throws InterruptedException {
        Thread.sleep(2000);
        driver.quit();
    }
}



