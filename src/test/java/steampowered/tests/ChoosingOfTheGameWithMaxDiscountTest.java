package steampowered.tests;

import framework.Browser;
import framework.ConfigLoader;
import framework.elements.BaseElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import steampowered.pages.*;

import static framework.Browser.configLoader;


public class ChoosingOfTheGameWithMaxDiscountTest {

    private static Browser currentBrowser;
    public static ConfigLoader localLoader;

    @BeforeClass
    public static void testSetup() {
        currentBrowser = Browser.getInstance();
        currentBrowser.navigate(configLoader.getProperty("url"));
        BaseElement.deleteInstalledFile();
    }

    @Test
    @Parameters({"language", "year"})
    public void testChooseGameWithMaxDiscount(String language, String year) {

        MainPage mainPage = new MainPage();
        mainPage.changeLanguage(language);
        localLoader = new ConfigLoader(String.format("localization/local_%1$s.properties", language.toLowerCase()));
        mainPage.navigateSection("section", "subSection");

        ActionPage actionPage = new ActionPage();
       actionPage.choseGameWithMaxDiscount();

        if(currentBrowser.getDriver().getCurrentUrl().contains("agecheck")) {
            ValidationAgePage validationAgePage = new ValidationAgePage();
            validationAgePage.ageChek(year);
        }

        GamePage gamePage = new GamePage();

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(gamePage.getGameName(), ActionPage.getGameNameWithMaxDiscount());
        gamePage.clickInstallSteam("installSteam");

        InstallPage installPage = new InstallPage();
        installPage.installGame();
        softAssert.assertTrue(installPage.isFileDownloaded());
    }

    @AfterClass
    public void closeBrowser() {
        currentBrowser.teardown();
    }
}
