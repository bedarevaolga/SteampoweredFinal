package steampowered.pages;

import framework.BaseSteamPage;
import framework.elements.Button;
import org.openqa.selenium.By;


public class InstallPage extends BaseSteamPage {

    public InstallPage() {
        super(By.xpath("//div[@class='online_stats']"));
    }

    private Button btnInstall = new Button(By.xpath("//a[@class='about_install_steam_link']"));

    public void installGame() {
        btnInstall.clickElement();
    }

    public boolean isDownloadsExists() {

            return baseElement.isDownloadsExists();


    }


}
