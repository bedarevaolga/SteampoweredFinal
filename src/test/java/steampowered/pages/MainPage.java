package steampowered.pages;

import framework.BaseSteamPage;
import framework.elements.BaseElement;
import framework.elements.Label;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.util.List;

import static steampowered.tests.ChoosingOfTheGameWithMaxDiscountTest.localLoader;


public class MainPage extends BaseSteamPage {

    public MainPage() {
        super(By.xpath("//div[@class='home_page_gutter']"));
    }

    private static String sectionLocator = "//a[@class= 'pulldown_desktop' and text()='%s']";
    private static String subsectionLocator = "//div[contains(@class, 'popup_menu_subheader popup_genre_expand_header')]/child::a[contains(text(), '%s')]";

    public void changeLanguage(String language) {
        btnLanguage.clickElement();
        List<BaseElement> languages = lblLanguage.getElements();
        for (BaseElement element : languages) {
            if (element.getTextValue().contains(language)) {
                element.click();
                return;
            }
        }
        btnLanguage.sendKeys(Keys.ESCAPE);
    }

    public void navigateSection(String section, String subsection) {
        new Label(sectionLocator, localLoader.getProperty(section)).clickElement();
        new Label(subsectionLocator, localLoader.getProperty(subsection)).clickElement();
    }
}


