package framework;


import framework.elements.BaseElement;
import org.openqa.selenium.By;
import org.testng.Assert;

public abstract class Page {

    protected BaseElement baseElement;
    protected static By titleLocator;

    protected Page(final By locator) {
        init(locator);
        baseElement = new BaseElement();
        Assert.assertTrue(baseElement.isElementPresentedOnPage(locator));
    }

    private void init(final By locator) {
        titleLocator = locator;
    }
}
