package framework;

import framework.elements.BaseElement;
import framework.elements.Button;
import framework.elements.Label;
import org.openqa.selenium.By;

import org.testng.Assert;

import static steampowered.tests.ChoosingOfTheGameWithMaxDiscountTest.localLoader;

public class BaseSteamPage extends BaseEntity {

    protected BaseElement baseElement;
    protected static By titleLocator;

    protected Button btnLanguage = new Button(By.xpath("//span[@class='pulldown global_action_link']"));
    protected Label lblLanguage = new Label(By.xpath("//a[@class=\"popup_menu_item tight\"]"));
    protected static  String button = "//a[contains(text(),'%s')]";

    protected BaseSteamPage(final By locator) {
        init(locator);
        baseElement = new BaseElement();
        Assert.assertTrue(baseElement.isElementPresentedOnPage(locator));
    }


    private void init(final By locator) {
        titleLocator = locator;
    }

    public void clickInstallSteam(String btn) {
        new Button(button, localLoader.getProperty(btn)).clickAndWait();
    }

}
