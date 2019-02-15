package Driver;

import Data.UserData;
//import io.appium.java_client.MobileElement;
//import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Key;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.stalenessOf;




public class MainMethods extends UserData {

    public static WebDriver driver;
    public WebDriverWait wait;

    //Main driver methods

    public void waitForElement(WebElement element) {
        wait = new WebDriverWait(driver, 60);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForElementStaleness(WebElement element){
        wait = new WebDriverWait(driver, 2);
        wait.until(stalenessOf(element));
    }

    public void waitForElementClickable(WebElement element){
        wait = new WebDriverWait(driver, 60);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitForElementEmpty(WebElement element){
        wait = new WebDriverWait(driver, 30);
       // wait.until(((element.getAttribute("Value").length()!=0), true);
    }

    public boolean isElementVisible(WebElement element) {
        try {
            waitForElement(element);
            /*if (element.isEnabled()) {
                return true;
            } else {
                return false;
            }*/
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isElementNotVisible(WebElement element) {

        try {
            wait = new WebDriverWait(driver, 10);
            wait.until(stalenessOf(element));
        } catch (NoSuchElementException e) {
            return true;
        } catch (TimeoutException e) {
            return true;
        } catch (Exception e){
            return false;
        }
        return false;
    }


    public boolean isRedirectTo(String url) {
        wait = new WebDriverWait(driver, 90);
        try {
            wait.until(ExpectedConditions.urlContains(url));
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public void clickElement(WebElement element) {
        waitForElementClickable(element);
        element.click();
    }


    public void inputDataField(WebElement input, String text) {
        waitForElement(input);
        input.sendKeys(text);
    }

    public void inputDataFieldClear(WebElement input, String text) {
        waitForElement(input);
        wait.until(ExpectedConditions.attributeToBeNotEmpty(input, "value"));

        input.clear();
        input.sendKeys(text);
    }

    public void checkCheckbox(WebElement checkbox) {
        checkbox.sendKeys(Keys.SPACE);
    }

    public void hoverOverElement(WebElement element) {
        Actions action = new Actions(driver);
        action.moveToElement(element).build().perform();
    }

    public void moveToElement(WebElement element) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;

        jse.executeScript("arguments[0].scrollIntoView()", element);
    }

    public void dragToClick(WebElement drag, WebElement click) {
        Actions builder = new Actions(driver);
        Action dragAndDrop = builder.clickAndHold(drag)
                .moveToElement(click)
                .release(click)
                .build();
        dragAndDrop.perform();
        click.click();
    }

    public void fillDropdown(WebElement element, String text) {
        waitForElement(element);
        element.sendKeys(text);
        Actions builder = new Actions(driver);
        builder.sendKeys(Keys.RETURN).build().perform();
    }

    public void selectDropdown(WebElement dropDown, String text) {
        waitForElement(dropDown);
        Select select = new Select(dropDown);
        select.selectByVisibleText(text);
    }

    public void selectDropdownValue(WebElement dropDown, String text) {
        waitForElement(dropDown);
        Select select = new Select(dropDown);
        select.selectByValue(text);
    }

    public void selectDropdownSpan(WebElement dropdown, List<WebElement> allElementsInList, int index) throws InterruptedException {
        Thread.sleep(1000);
        dropdown.click();
        allElementsInList.get(index).click();
    }

    public void switchToFrame(WebElement frame) {

        driver.switchTo().frame(frame);
    }

    public static int getResponseCode(String urlString) throws MalformedURLException, IOException{
        URL url = new URL(urlString);
        HttpURLConnection huc = (HttpURLConnection)url.openConnection();
        huc.setRequestMethod("GET");
        huc.connect();
        return huc.getResponseCode();
    }

    public void uploadFile(String fileName, WebElement element){
        File dir = new File("src");
        File file = new File(dir, fileName);
        String path = file.getAbsolutePath(); //путь к файлу
        element.sendKeys(path);
    }

    //
    ///Mobile methods

    /*public void switchToWeb (WebDriver driver) {
        Set<String> contextNames = ((AndroidDriver) driver).getContextHandles();
        logger.info("Ready to switch to WebView...");
        for (String contextName : contextNames) {
            logger.info(contextName);
            if (contextName.contains("WEBVIEW_io")) {
                ((AndroidDriver) driver).context(contextName);
            }
            logger.info("Switched to WebView.");
        }
    }

    public void inputDataFieldMobile(MobileElement element, String text, WebDriver driver) {
        waitForElement(element, driver);
        //element.setValue(text);
    }*/
}
