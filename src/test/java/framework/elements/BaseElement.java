package framework.elements;

import framework.Browser;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static framework.Browser.*;
import static framework.Browser.getImplicitlyWait;


public class BaseElement {


    protected static Browser browser = Browser.getInstance();
    protected String name;
    protected By locator;
    protected WebElement element;
    protected List<WebElement> list;
    protected List<BaseElement> baseElementList;

    public BaseElement() {
    }

    protected BaseElement(final By loc) {
        locator = loc;
    }

    protected BaseElement(final WebElement webEl) {
        element = webEl;
    }

    protected BaseElement(final By loc, final String nameOf) {
        locator = loc;
        name = nameOf;
    }

    public void waitForPageToLoad() {
        new WebDriverWait(browser.getDriver(), Integer.parseInt(configLoader.getProperty("waitForPageToLoad"))).until((ExpectedCondition<Boolean>) wd ->
                ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
    }

    public void waitElementBeClickable(WebElement element) {
        new WebDriverWait(browser.getDriver(), Integer.parseInt(configLoader.getProperty("waitElementBeClickable"))).until(ExpectedConditions
                .elementToBeClickable(element));
    }

    public boolean isElementPresentedOnPage(By locator) {
        boolean isElementDisplayed = true;
        try {
            new WebDriverWait(browser.getDriver(), Integer.parseInt(configLoader.getProperty("waitElementVisible")))
                    .until(driver -> driver.findElement(locator));
        } catch (NoSuchElementException | TimeoutException e) {
            isElementDisplayed = false;
        }
        return isElementDisplayed;
    }

    public String getTextValue() {
        waitElementBeClickable(element);
        return element.getText();
    }

    public String getText() {
        waitForIsElementPresent();
        return element.getText();
    }

    public void click() {
        waitElementBeClickable(element);
        if (browser.getDriver() instanceof JavascriptExecutor) {
            ((JavascriptExecutor) browser.getDriver()).executeScript("arguments[0].style.border='3px solid red'", element);
        }
        element.click();
    }

    public void clickElement() {
        waitForIsElementPresent();
        if (browser.getDriver() instanceof JavascriptExecutor) {
            ((JavascriptExecutor) browser.getDriver()).executeScript("arguments[0].style.border='3px solid red'", element);
        }
        element.click();
    }

    public void clickAndWait() {
        waitForIsElementPresent();
        clickElement();
        waitForPageToLoad();
    }

    public void select(String value) {
        waitForIsElementPresent();
        Select select = new Select(element);
        select.selectByVisibleText(value);
    }

    public void sendKeys(Keys key) {
        Actions action = new Actions(browser.getDriver());
        action.sendKeys(key).build().perform();
    }

    public void moveToElement() {
        waitForIsElementPresent();
        Actions action = new Actions(browser.getDriver());
        action.moveToElement(element).build().perform();
    }


    public static boolean isDownloadsExists() {

        WebDriverWait wait = new WebDriverWait(browser.getDriver(), getForPageToLoadWait());
        String downloadPath = System.getProperty("user.dir") + "\\" + configLoader.getProperty("downloadPath");
        File file = new File(downloadPath + configLoader.getProperty("downloadedFile"));
        wait.until(driver -> file.exists());
        return file.exists();
    }

    public static boolean isFileLoadingComplete() {

        String mainWindow = browser.getDriver().getWindowHandle();
        JavascriptExecutor js = (JavascriptExecutor) Browser.getInstance().getDriver();
        js.executeScript("window.open()");
        for (String winHandle : browser.getDriver().getWindowHandles()) {
            browser.getDriver().switchTo().window(winHandle);
        }
        browser.getDriver().get("chrome://downloads");
        JavascriptExecutor downloadWindowExecutor = (JavascriptExecutor) Browser.getInstance().getDriver();
        Long percentage = (long) 0;
        while (percentage != 100 && !isDownloadsExists()) {
            try {
                percentage = (long) downloadWindowExecutor.executeScript("return document.querySelector('downloads-manager').shadowRoot.querySelector('#downloadsList downloads-item').shadowRoot.querySelector('#progress').value");
                System.out.println(percentage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        browser.getDriver().close();
        browser.getDriver().switchTo().window(mainWindow);
        return true;
    }

    public static boolean isFileLoadingCompleteWaiter() {
        WebDriverWait wait = new WebDriverWait(browser.getDriver(), getForPageToLoadWait());
        wait.until(driver -> isFileLoadingComplete());
        return true;
    }

    public static void deleteInstalledFile() {
        File file = new File(configLoader.getProperty("downloadPath") + configLoader.getProperty("downloadedFile"));
        if (file.exists()) {
            file.delete();
        }
    }

    public boolean isPresent() {

        WebDriverWait wait = new WebDriverWait(browser.getDriver(), getImplicitlyWait());
        browser.getDriver().manage().timeouts().implicitlyWait(getImplicitlyWait(), TimeUnit.SECONDS);
        try {
            wait.until((ExpectedCondition<Boolean>) driver -> {
                try {
                    list = driver.findElements(locator);
                    for (WebElement el : list) {
                        if (el instanceof WebElement && el.isDisplayed()) {
                            element = el;
                            return element.isDisplayed();
                        }
                    }
                    element = driver.findElement(locator);
                } catch (Exception e) {
                    return false;
                }
                return element.isDisplayed();
            });
        } catch (Exception e) {
            return false;
        }
        try {
            browser.getDriver().manage().timeouts().implicitlyWait(getImplicitlyWait(), TimeUnit.SECONDS);
            return element.isDisplayed();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public void waitForIsElementPresent() {
        isPresent();
        Assert.assertTrue(element.isDisplayed());
    }

    public List<BaseElement> getElements() {
        baseElementList = new ArrayList<>();
        waitForIsElementPresent();
        for (WebElement webElement : list) {
            baseElementList.add(new BaseElement(webElement));
        }
        return baseElementList;
    }
}
