package steampowered.pages;

import framework.BaseSteamPage;
import framework.Browser;
import framework.elements.Button;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import static framework.elements.BaseElement.isDownloadsExists;
import static framework.elements.BaseElement.isFileLoadingCompleteWaiter;


public class InstallPage extends BaseSteamPage {

    public InstallPage() {
        super(By.xpath("//div[@class='online_stats']"));
    }

    private Button btnInstall = new Button(By.xpath("//a[@class='about_install_steam_link']"));

    public void installGame() {
        btnInstall.clickElement();

    }

    public boolean isFileDownloaded() {
        return isFileLoadingCompleteWaiter();
    }
}
