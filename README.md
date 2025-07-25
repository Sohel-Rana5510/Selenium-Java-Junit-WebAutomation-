# Selenium-JUnit WebAutomation Project
This repository contains professional-level automation projects using Java, JUnit 5, and Selenium WebDriver. The goal of these projects is to showcase real-world UI automation capabilities such as form interaction, date picker automation, dynamic table scraping, and data persistence, all tested under the JUnit framework.

## 📁 Project Overview:
### 1. Web Form Automation
#### 🔗 URL: https://demo.wpeverest.com/user-registration/guest-registration-form/
#### Descriptions: 

 This test automates a user registration form that includes:
- Input fields (first name, last name, email, phone, nationality, etc.)
- Secure password entry
- Two date picker widgets for Date of Birth and Date of Arrival

⚙️ Implementation Highlights:
➡️ Form fields are located using a combination of:

- By.tagName()
- By.className()
- By.cssSelector()
- By.id()
➡️Dynamic dropdowns (Month & Year) handled using Select and Keys
➡️Date selection uses aria-label attributes to pinpoint correct days
➡️Thread.sleep() used to ensure DOM stability before interacting
➡️Assertions can be added for validation (e.g., successful submission)

### 2. DSE Price Table Extractor
🔗 URL: https://dsebd.org/latest_share_price_scroll_by_value.php

 #### Description:
This test focuses on scraping a live dynamic table from the Dhaka Stock Exchange (DSE) webpage and writing it to a local file.

### Implementation 
- The table is located using
  WebElement table = driver.findElement(By.cssSelector(".table-responsive.inner-scroll table"));
- All table rows are retrieved using
  List<WebElement> rows = table.findElements(By.tagName("tr"));
- Ensures clean and structured data collection from dynamic content tables.


  ## Technology:
  - java 17
  - Selenium Webdriver
  - ChromeDriver
  - Gradle


## Test Reports:
<img width="1112" height="671" alt="image" src="https://github.com/user-attachments/assets/720742de-9fd9-4f59-bf3f-68780f5ae8aa" />
<img width="1097" height="472" alt="image" src="https://github.com/user-attachments/assets/213ca494-060d-424f-84a0-816df788406f" />




