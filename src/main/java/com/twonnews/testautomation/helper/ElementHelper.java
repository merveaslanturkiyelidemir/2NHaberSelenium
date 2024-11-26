package com.twonnews.testautomation.helper;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;  // Add this import
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ElementHelper {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JsonReader jsonReader;

    public ElementHelper(WebDriver driver) {
        this.driver = driver;
        this.jsonReader = new JsonReader();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private By getLocator(String elementName) {
        String locatorType = jsonReader.getLocatorType(elementName);
        String locatorValue = jsonReader.getLocatorValue(elementName);
        
        return switch (locatorType.toLowerCase()) {
            case "xpath" -> By.xpath(locatorValue);
            case "css" -> By.cssSelector(locatorValue);
            case "id" -> By.id(locatorValue);
            case "name" -> By.name(locatorValue);
            default -> throw new IllegalArgumentException("Unsupported locator type: " + locatorType);
        };
    }

    /**
     * Clicks on the specified element.
     * @param elementName The name of the element to click.
     */
    public void click(String elementName) {
        By locator = getLocator(elementName);
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            element.click();
            System.out.println("✓ " + elementName + " elementine tıklandı");
        } catch (Exception e) {
            String error = "✗ " + elementName + " elementi tıklanamadı: " + e.getMessage();
            System.err.println(error);
            throw new RuntimeException(error);
        }
    }

    /**
     * Sends the specified value to the element.
     * @param elementName The name of the element.
     * @param value The value to send.
     */
    public void sendKeys(String elementName, String value) {
        By locator = getLocator(elementName);
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            element.clear();
            element.sendKeys(value);
            System.out.println("✓ " + elementName + " elementine '" + value + "' değeri yazıldı");
        } catch (Exception e) {
            String error = "✗ " + elementName + " elementine değer yazılamadı: " + e.getMessage();
            System.err.println(error);
            throw new RuntimeException(error);
        }
    }

    /**
     * Waits until the specified element is visible.
     * @param elementName The name of the element to wait for.
     */
    public void waitForElement(String elementName) {
        By locator = getLocator(elementName);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            System.out.println("✓ " + elementName + " elementi görünür olana kadar beklendi");
        } catch (Exception e) {
            String error = "✗ " + elementName + " elementi görünür olmadı: " + e.getMessage();
            System.err.println(error);
            throw new RuntimeException(error);
        }
    }

    /**
     * Checks if the specified element is displayed.
     * @param elementName The name of the element to check.
     * @return True if the element is displayed, false otherwise.
     */
    public boolean isElementDisplayed(String elementName) {
        By locator = getLocator(elementName);
        try {
            boolean isDisplayed = wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
            String message = isDisplayed ? 
                "✓ " + elementName + " elementi görünür" :
                "✗ " + elementName + " elementi görünmüyor";
            System.out.println(message);
            return isDisplayed;
        } catch (Exception e) {
            System.err.println("✗ " + elementName + " elementi bulunamadı: " + e.getMessage());
            return false;
        }
    }

    /**
     * Navigates to the specified URL.
     * @param url The URL to navigate to.
     */
    public void navigateToUrl(String url) {
        try {
            driver.get(url);
            System.out.println("✓ " + url + " sayfasına gidildi");
        } catch (Exception e) {
            String error = "✗ " + url + " sayfasına gidilemedi: " + e.getMessage();
            System.err.println(error);
            throw new RuntimeException(error);
        }
    }

    /**
     * Scrolls the page to the bottom.
     */
    public void scrollPage() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
            System.out.println("✓ Sayfa aşağı kaydırıldı");
        } catch (Exception e) {
            String error = "✗ Sayfa kaydırılamadı: " + e.getMessage();
            System.err.println(error);
            throw new RuntimeException(error);
        }
    }

    /**
     * Sets a date value into the specified date input field.
     * @param elementName The name of the date input element.
     * @param dateValue The date value to enter.
     */
    public void setDateInput(String elementName, String dateValue) {
        By locator = getLocator(elementName);
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            element.clear();
            element.sendKeys(dateValue);
            System.out.println("✓ " + elementName + " tarih alanına '" + dateValue + "' değeri girildi");
        } catch (Exception e) {
            String error = "✗ " + elementName + " tarih alanına değer girilemedi: " + e.getMessage();
            System.err.println(error);
            throw new RuntimeException(error);
        }
    }

    /**
     * Uploads a file to the specified element.
     * @param elementName The name of the element to upload to.
     * @param fileName The name of the file to upload.
     */
    public void uploadFile(String elementName, String fileName) {
        By locator = getLocator(elementName);
        try {
            String filePath = System.getProperty("user.dir") 
                + "/src/test/resources/" 
                + fileName;
            
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            
            // Save original element state
            String originalDisplay = element.getCssValue("display");
            String originalVisibility = element.getCssValue("visibility");
            String originalPosition = element.getCssValue("position");
            
            try {
                // Make element interactable
                js.executeScript("""
                    arguments[0].style.display = 'block';
                    arguments[0].style.visibility = 'visible';
                    arguments[0].style.position = 'fixed';
                    arguments[0].style.top = '0';
                    arguments[0].style.left = '0';
                    arguments[0].style.opacity = '1';
                    arguments[0].style.zIndex = '999999';
                """, element);
                
                // Try direct sendKeys first
                element.sendKeys(filePath);
                
                // If sendKeys fails, try JavaScript
                if (element.getAttribute("value") == null || element.getAttribute("value").isEmpty()) {
                    js.executeScript("arguments[0].value = arguments[1]", element, filePath);
                }
            } finally {
                // Restore original element state
                js.executeScript("""
                    arguments[0].style.display = arguments[1];
                    arguments[0].style.visibility = arguments[2];
                    arguments[0].style.position = arguments[3];
                """, element, originalDisplay, originalVisibility, originalPosition);
            }
            
            System.out.println("✓ " + fileName + " dosyası " + elementName + " alanına yüklendi");
        } catch (Exception e) {
            String error = "✗ " + fileName + " dosyası yüklenemedi: " + e.getMessage();
            System.err.println(error);
            throw new RuntimeException(error);
        }
    }

    /**
     * Retrieves navigation menu items based on the given selector.
     * @param selector The CSS selector for the menu items.
     * @return A list of WebElements representing the menu items.
     */
    public List<WebElement> getNavMenuItems(String selector) {
        try {
            return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(selector)));
        } catch (Exception e) {
            System.err.println("✗ Menu items could not be found: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves the page title.
     * @return The page title as a String.
     */
    public String getPageTitle() {
        try {
            WebElement titleElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id='main']/div/div/div/div/div[1]/section[1]/div/div/div/section/div/div/div/div/div/h1")));
            return titleElement.getText();
        } catch (Exception e) {
            System.err.println("✗ Sayfa başlığı alınamadı: " + e.getMessage());
            return "";
        }
    }

    /**
     * Clicks on a menu item.
     * @param menuItem The WebElement representing the menu item.
     */
    public void clickMenuLink(WebElement menuItem) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(menuItem)).click();
            System.out.println("✓ Menü elemanına tıklandı: " + menuItem.getText());
        } catch (Exception e) {
            String error = "✗ Menü elemanına tıklanamadı: " + e.getMessage();
            System.err.println(error);
            throw new RuntimeException(error);
        }
    }

    /**
     * Retrieves the locator value for the specified element name.
     * @param elementName The name of the element.
     * @return The locator value as a String.
     */
    public String getLocatorValue(String elementName) {
        return jsonReader.getLocatorValue(elementName);
    }

    /**
     * Waits until the page has fully loaded.
     */
    public void waitForPageLoad() {
        try {
            wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
            Thread.sleep(500); // Small buffer for dynamic content
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.err.println("✗ Page load wait failed: " + e.getMessage());
        }
    }

    /**
     * Moves to the specified element and clicks it.
     * @param element The WebElement to interact with.
     */
    public void moveToElementAndClick(WebElement element) {
        try {
            Actions actions = new Actions(driver);
            wait.until(ExpectedConditions.elementToBeClickable(element));
            
            // Elementin görünür olduğundan emin ol
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            waitForSeconds(1);
            
            // Önce hover yap
            actions.moveToElement(element).perform();
            waitForSeconds(1);
            
            // Sonra tıkla
            actions.click(element).perform();
            
            System.out.println("✓ Clicked: " + element.getText());
        } catch (Exception e) {
            throw new RuntimeException("Failed to hover and click element: " + e.getMessage());
        }
    }

    /**
     * Waits until the specified element is visible.
     * @param element The WebElement to wait for.
     */
    public void waitForElementToBeVisible(WebElement element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (Exception e) {
            throw new RuntimeException("Element visible olmadı: " + e.getMessage());
        }
    }

    /**
     * Waits for the specified number of seconds.
     * @param seconds The number of seconds to wait.
     */
    public void waitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Simulates pressing the Enter key.
     */
    public void pressEnter() {
        try {
            Actions actions = new Actions(driver);
            actions.sendKeys(Keys.ENTER).perform();
            System.out.println("✓ Enter tuşuna basıldı");
        } catch (Exception e) {
            String error = "✗ Enter tuşuna basılamadı: " + e.getMessage();
            System.err.println(error);
            throw new RuntimeException(error);
        }
    }

    /**
     * Verifies that the specified element contains the expected text.
     * @param elementName The name of the element.
     * @param expectedText The expected text to verify.
     */
    public void verifyElementText(String elementName, String expectedText) {
        By locator = getLocator(elementName);
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            String actualText = element.getText().trim();
            if (actualText.equals(expectedText)) {
                System.out.println("✓ " + elementName + " elementinin içeriği doğrulandı: " + expectedText);
            } else {
                String error = "✗ " + elementName + " elementinin içeriği beklenen değerle uyuşmuyor. Beklenen: '" 
                    + expectedText + "', Gerçek: '" + actualText + "'";
                System.err.println(error);
                throw new AssertionError(error);
            }
        } catch (Exception e) {
            String error = "✗ " + elementName + " elementinin içeriği kontrol edilemedi: " + e.getMessage();
            System.err.println(error);
            throw new RuntimeException(error);
        }
    }

    /**
     * Scrolls the page until the specified element is in view.
     * @param elementName The name of the element to scroll to.
     */
    public void scrollToElement(String elementName) {
        By locator = getLocator(elementName);
        try {
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
            waitForSeconds(1); // Wait for scroll animation
            System.out.println("✓ " + elementName + " elementi görülene kadar sayfa kaydırıldı");
        } catch (Exception e) {
            String error = "✗ " + elementName + " elementi için sayfa kaydırılamadı: " + e.getMessage();
            System.err.println(error);
            throw new RuntimeException(error);
        }
    }

    /**
     * Verifies if the checkbox is selected, and clicks it if not.
     * @param elementName The name of the checkbox element.
     */
    public void verifyAndClickCheckbox(String elementName) {
        By locator = getLocator(elementName);
        try {
            WebElement checkbox = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            
            if (!checkbox.isSelected()) {
                checkbox.click();
                System.out.println("✓ " + elementName + " checkbox'ı işaretlendi");
            } else {
                System.out.println("✓ " + elementName + " checkbox'ı zaten işaretli");
            }
        } catch (Exception e) {
            String error = "✗ " + elementName + " checkbox'ı kontrol edilemedi: " + e.getMessage();
            System.err.println(error);
            throw new RuntimeException(error);
        }
    }
}