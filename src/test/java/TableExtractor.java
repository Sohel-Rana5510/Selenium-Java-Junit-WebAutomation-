import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TableExtractor {

    WebDriver driver;

    @BeforeAll
    void setUp() {
        // Set path to ChromeDriver if needed (uncomment and adjust path)
        // System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    @Test
    @DisplayName("Scrape DSE Table and Assert Success")
    void printAndSaveTableData() throws IOException {
        driver.get("https://dsebd.org/latest_share_price_scroll_by_value.php");

        // Wait explicitly for the table to ensure it’s loaded
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        WebElement table;
        try {
            table = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector(".table-responsive.inner-scroll table")));
        } catch (Exception e) {
            Assertions.fail("Table not found on the page: " + e.getMessage());
            return;
        }

        // Get all rows
        List<WebElement> rows = table.findElements(By.tagName("tr"));
        Assertions.assertFalse(rows.isEmpty(), "No rows found in the table");

        // Prepare file
        Path filePath = Paths.get("share_prices.txt"); // Simplified path for portability
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()))) {
            int totalCellCount = 0;
            List<List<String>> tableData = new ArrayList<>();

            // Step 1: Extract and print all cell values (including <th> and <td>)
            System.out.println("Table Cell Values:");
            for (WebElement row : rows) {
                List<WebElement> cells = row.findElements(By.cssSelector("td, th"));
                if (cells.isEmpty()) continue; // Skip empty rows

                List<String> cellValues = new ArrayList<>();
                for (WebElement cell : cells) {
                    String text = cell.getText().trim();
                    cellValues.add(text);
                    System.out.print(text + "\t");
                    totalCellCount++;
                }
                tableData.add(cellValues);
                System.out.println(); // New line after each row
                writer.write(String.join("\t", cellValues) + "\n");
            }

            // Step 2: Assert that data was extracted and file was written
            Assertions.assertTrue(totalCellCount > 0, "No cells were scraped from the table");
            Assertions.assertFalse(tableData.isEmpty(), "Table data is empty");
            Assertions.assertTrue(Files.exists(filePath), "Output file was not created");

            System.out.println("Table scraped and saved successfully to " + filePath);
        } catch (IOException e) {
            Assertions.fail("Error writing to file: " + e.getMessage());
        }
    }

    @AfterAll
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}