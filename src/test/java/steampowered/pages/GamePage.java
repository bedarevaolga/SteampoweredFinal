package steampowered.pages;

import framework.BaseSteamPage;

import framework.elements.Label;
import org.openqa.selenium.*;


public class GamePage extends BaseSteamPage {
    public GamePage() {
        super(By.xpath("//div[@class='blockbg']"));
    }


    private Label lblGameName = new Label(By.xpath("//div[@id='appHubAppName']"));


    public String getGameName() {
        return lblGameName.getText();
    }


}
