package com.twonnews.testautomation.steps;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.twonnews.testautomation.helper.ElementHelper;
import com.twonnews.testautomation.manager.DriverManager;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SampleSteps {
    private ElementHelper elementHelper;
    private WebDriver driver;
    private final List<String> checkedMenus = new ArrayList<>();
    private final Map<String, String> mismatchedTitles = new HashMap<>();
    private final Map<String, String> errorMessages = new HashMap<>();

    @Before
    public void setup() {
        this.driver = DriverManager.getDriver();
        this.elementHelper = new ElementHelper(driver);
    }

    @After
    public void tearDown() {
        DriverManager.quitDriver();
    }

    /**
     * Clicks on the specified element.
     * @param elementName The name of the element to click.
     */
    @When("^\"([^\"]*)\" objesine tiklanir$")
    public void clickElement(String elementName) {
        elementHelper.click(elementName);
    }

    /**
     * Enters a value into the specified element.
     * @param elementName The name of the element.
     * @param value The value to enter.
     */
    @When("^\"([^\"]*)\" objesine \"([^\"]*)\" degeri yazilir$")
    public void sendKeysToElement(String elementName, String value) {
        elementHelper.sendKeys(elementName, value);
    }

    /**
     * Waits until the specified element becomes visible.
     * @param elementName The name of the element to wait for.
     */
    @When("^\"([^\"]*)\" objesi gorulene kadar beklenir$")
    public void waitForElement(String elementName) {
        elementHelper.waitForElement(elementName);
    }

    /**
     * Verifies if the specified element is visible.
     * @param elementName The name of the element to check.
     */
    @Then("^\"([^\"]*)\" objesinin goruldugu kontrol edilir$")
    public void checkElementVisible(String elementName) {
        elementHelper.isElementDisplayed(elementName);
    }

    /**
     * Navigates to the specified URL.
     * @param url The URL to navigate to.
     */
    @Given("^\"([^\"]*)\" sayfasina gidilir$")
    public void navigateToUrl(String url) {
        elementHelper.navigateToUrl(url);
    }

    /**
     * Waits for a specified number of seconds.
     * @param seconds The number of seconds to wait.
     */
    @When("^(\\d+) saniye beklenir$")
    public void waitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
            System.out.println("✓ " + seconds + " saniye beklendi");
        } catch (InterruptedException e) {
            System.err.println("✗ Bekleme işlemi kesintiye uğradı");
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Scrolls the page.
     */
    @When("^sayfa kaydirilir$")
    public void scrollPage() {
        elementHelper.scrollPage();
    }

    /**
     * Enters a date value into the specified date input field.
     * @param elementName The name of the date input element.
     * @param dateValue The date value to enter.
     */
    @When("^\"([^\"]*)\" tarih alanina \"([^\"]*)\" degeri girilir$")
    public void setDateInput(String elementName, String dateValue) {
        elementHelper.setDateInput(elementName, dateValue);
    }

    /**
     * Uploads a file to the specified element.
     * @param fileName The name of the file to upload.
     * @param elementName The name of the element to upload to.
     */
    @When("^\"([^\"]*)\" dosyasi \"([^\"]*)\" alanina yuklenir$")
    public void uploadFile(String fileName, String elementName) {
        elementHelper.uploadFile(elementName, fileName);
    }

    /**
     * Checks all navbar elements by clicking each one and verifying the page title.
     */
    @When("^Tum navbar elementleri kontrol edilir$")
    public void checkAllNavbarElements() {
        String mainMenuXPath = "//*[@id='menu-1-5dc673f1']/li";
        Actions actions = new Actions(driver);
    
        List<WebElement> mainMenuItems = driver.findElements(By.xpath(mainMenuXPath));
        System.out.println("\n=== MENÜ KONTROL BAŞLADI ===");
        System.out.println("Ana menü sayısı: " + mainMenuItems.size());
    
        for (int i = 1; i <= mainMenuItems.size(); i++) {
            try {
                String mainMenuItemXPath = mainMenuXPath + "[" + i + "]";
                WebElement mainMenuItem = driver.findElement(By.xpath(mainMenuItemXPath));
                String mainMenuText = mainMenuItem.getText().trim();
                
                System.out.println("\nAna Menü Kontrolü: " + mainMenuText);
                
                String subMenuXPath = mainMenuItemXPath + "/ul/li";
                List<WebElement> subMenuItems = driver.findElements(By.xpath(subMenuXPath));
                
                if (!subMenuItems.isEmpty()) {
                    System.out.println("Alt menü sayısı: " + subMenuItems.size());
                    
                    checkMenuItem(mainMenuItem, mainMenuText);
                    
                    driver.navigate().to(driver.getCurrentUrl());
                    elementHelper.waitForPageLoad();
                    mainMenuItem = driver.findElement(By.xpath(mainMenuItemXPath));
                    
                    for (int j = 1; j <= subMenuItems.size(); j++) {
                        try {
                            actions.moveToElement(mainMenuItem).perform();
                            elementHelper.waitForSeconds(1);
                            
                            String currentSubMenuXPath = subMenuXPath + "[" + j + "]";
                            WebElement subMenuItem = driver.findElement(By.xpath(currentSubMenuXPath));
                            String subMenuText = subMenuItem.getText().trim();
                            System.out.println("Alt Menü Kontrolü: " + subMenuText);
                            
                            checkMenuItem(subMenuItem, subMenuText);
                            
                            driver.navigate().to(driver.getCurrentUrl());
                            elementHelper.waitForPageLoad();
                            mainMenuItem = driver.findElement(By.xpath(mainMenuItemXPath));
                            
                        } catch (Exception e) {
                            String errorMsg = "Alt menü kontrolünde hata: " + e.getMessage();
                            System.err.println("❌ " + errorMsg);
                            errorMessages.put("Alt Menü #" + j, errorMsg);
                            
                            driver.navigate().to(driver.getCurrentUrl());
                            elementHelper.waitForPageLoad();
                        }
                    }
                } else {
                    checkMenuItem(mainMenuItem, mainMenuText);
                    
                    driver.navigate().to(driver.getCurrentUrl());
                    elementHelper.waitForPageLoad();
                }
                
            } catch (Exception e) {
                String errorMsg = "Ana menü kontrolünde hata: " + e.getMessage();
                System.err.println("❌ " + errorMsg);
                errorMessages.put("Ana Menü #" + i, errorMsg);
                
                driver.navigate().to(driver.getCurrentUrl());
                elementHelper.waitForPageLoad();
            }
        }
        
        generateFinalReport();
    }

    /**
     * Presses the Enter key.
     */
    @When("^Enter butonuna basilir$")
    public void pressEnterKey() {
        elementHelper.pressEnter();
    }
    
    /**
     * Verifies that the specified element contains the expected text.
     * @param elementName The name of the element.
     * @param expectedText The expected text.
     */
    @Then("^\"([^\"]*)\" objesinin iceriginde \"([^\"]*)\" degeri yazdigi kontrol edilir$")
    public void verifyElementText(String elementName, String expectedText) {
        elementHelper.verifyElementText(elementName, expectedText);
    }

    /**
     * Scrolls the page until the specified element is visible.
     * @param elementName The name of the element to scroll to.
     */
    @When("^\"([^\"]*)\" objesi ekranda gorulene kadar sayfa kaydirilir$")
    public void scrollToElement(String elementName) {
        elementHelper.scrollToElement(elementName);
    }

    /**
     * Verifies if the checkbox is selected, and clicks it if not.
     * @param elementName The name of the checkbox element.
     */
    @Then("^\"([^\"]*)\" checkbox objesinin tikli oldugu kontrol edilir ve tikli degilse tiklenir$")
    public void verifyAndClickCheckbox(String elementName) {
        elementHelper.verifyAndClickCheckbox(elementName);
    }

    /**
     * Checks a menu item by clicking it and verifying the page title.
     * @param menuItem The WebElement representing the menu item.
     * @param menuText The text of the menu item.
     */
    private void checkMenuItem(WebElement menuItem, String menuText) {
        try {
            elementHelper.waitForElementToBeVisible(menuItem);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", menuItem);
            elementHelper.waitForSeconds(1);

            String currentUrl = driver.getCurrentUrl();

            try {
                menuItem.click();
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", menuItem);
            }

            new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(driver -> !driver.getCurrentUrl().equals(currentUrl));

            elementHelper.waitForPageLoad();
            elementHelper.waitForSeconds(2);

            String pageTitle = "";
            try {
                WebElement titleElement = new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//h1[contains(@class, 'entry-title')]")));
                pageTitle = titleElement.getText().trim();
            } catch (Exception e1) {
                try {
                    WebElement titleElement = new WebDriverWait(driver, Duration.ofSeconds(5))
                        .until(ExpectedConditions.presenceOfElementLocated(By.tagName("h1")));
                    pageTitle = titleElement.getText().trim();
                } catch (Exception e2) {
                    System.err.println("❌ Başlık elementi bulunamadı: " + menuText);
                    errorMessages.put(menuText, "Başlık elementi bulunamadı");
                    return;
                }
            }

            if (!menuText.equalsIgnoreCase(pageTitle)) {
                mismatchedTitles.put(menuText, pageTitle);
                System.out.println("❌ Başlık uyuşmazlığı: Menü='" + menuText + "', Sayfa Başlığı='" + pageTitle + "'");
            } else {
                System.out.println("✅ Başlık doğrulandı: " + menuText);
            }

            checkedMenus.add(menuText);

        } catch (Exception e) {
            System.err.println("❌ Menü kontrolü başarısız: " + menuText);
            System.err.println("Hata: " + e.getMessage());
            errorMessages.put(menuText, e.getMessage());

            try {
                driver.navigate().refresh();
                elementHelper.waitForPageLoad();
            } catch (Exception refreshError) {
                System.err.println("✗ Sayfa yenileme başarısız: " + refreshError.getMessage());
            }
        }
    }
    
    /**
     * Generates a final report after checking all menu items.
     */
    private void generateFinalReport() {
        System.out.println("\n=== MENÜ KONTROL RAPORU ===");
        
        System.out.println("\nKontrol Edilen Menüler (" + checkedMenus.size() + " adet):");
        checkedMenus.forEach(menu -> System.out.println("✓ " + menu));
    
        if (!mismatchedTitles.isEmpty()) {
            System.out.println("\nUyuşmayan Başlıklar (" + mismatchedTitles.size() + " adet):");
            mismatchedTitles.forEach((menu, title) -> 
                System.out.println("✗ Menü: '" + menu + "' -> Sayfa Başlığı: '" + title + "'"));
        }
        
        if (!errorMessages.isEmpty()) {
            System.out.println("\nHata Oluşan Menüler (" + errorMessages.size() + " adet):");
            errorMessages.forEach((menu, error) -> 
                System.out.println("✗ " + menu + " -> Hata: " + error));
        }
        
        StringBuilder summary = new StringBuilder();
        if (!mismatchedTitles.isEmpty()) {
            summary.append(mismatchedTitles.size()).append(" başlık uyuşmazlığı, ");
        }
        if (!errorMessages.isEmpty()) {
            summary.append(errorMessages.size()).append(" hata, ");
        }
        
        if (summary.length() > 0) {
            throw new AssertionError("Test tamamlandı fakat sorunlar var: " + 
                summary.substring(0, summary.length() - 2));
        } else {
            System.out.println("\n✅ Tüm menü kontrolleri başarıyla tamamlandı!");
        }
    }
}