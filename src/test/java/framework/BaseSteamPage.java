package framework;

import framework.elements.Button;
import framework.elements.Label;
import org.openqa.selenium.By;

import static steampowered.tests.ChoosingOfTheGameWithMaxDiscountTest.localLoader;

public abstract class BaseSteamPage extends Page {

    protected Button btnLanguage = new Button(By.xpath("//span[@class='pulldown global_action_link']"));
    protected Label lblLanguage = new Label(By.xpath("//a[@class=\"popup_menu_item tight\"]"));
    protected static String button = "//a[contains(text(),'%s')]";

    protected BaseSteamPage(final By locator) {
        super(locator);
    }

    public void clickInstallSteam(String btn) {
        new Button(button, localLoader.getProperty(btn)).clickAndWait();
    }

}
