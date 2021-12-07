package steampowered.pages;

import framework.BaseSteamPage;
import framework.elements.BaseElement;
import framework.elements.Label;
import org.openqa.selenium.By;

import java.util.List;

public class ActionPage extends BaseSteamPage {

    public ActionPage() {
        super(By.xpath("//div[@class='carousel_container quadscreenshot_carousel']"));
    }

    private Label lblDiscount = new Label(By.xpath("//div[@class='capsule header']/following-sibling::div/descendant::div[@class=\"discount_pct\"]"));
    private Label lblGameName = new Label(By.xpath("//h4[@class='hover_title']"));
    private static String maxDiscount = "//div[@class='capsule header']/following-sibling::div/descendant::div[@class=\"discount_pct\" and text()='%s']";
    private static String gameNameWithMaxDiscount = " ";


    public int findMaxDiscounts() {

        List<BaseElement> discounts = lblDiscount.getElements();
        int maxDiscount = parseDiscounts(discounts.get(0).getTextValue());
        for (BaseElement element : discounts) {
            if (parseDiscounts(element.getTextValue()) > maxDiscount) {
                maxDiscount = parseDiscounts(element.getTextValue());
            }
        }
        return maxDiscount;
    }

    public void choseGameWithMaxDiscount() {
        Label lblMaxDiscount = new Label(maxDiscount, "-" + findMaxDiscounts() + "%");
        List<BaseElement> maxDiscountsList = lblMaxDiscount.getElements();
        if (maxDiscountsList.size() == 1) {
            lblMaxDiscount.moveToElement();
            gameNameWithMaxDiscount = lblGameName.getText();
            lblMaxDiscount.clickAndWait();
        } else {
            int random = (int) (Math.random() * (maxDiscountsList.size()));
            maxDiscountsList.get(random).moveToElement();
            gameNameWithMaxDiscount = lblGameName.getTextValue();
            maxDiscountsList.get(random).clickAndWait();
        }
    }

    public static String getGameNameWithMaxDiscount() {
        return gameNameWithMaxDiscount;
    }


    public int parseDiscounts(String text) {
        return Integer.parseInt(text.substring(1, text.indexOf("%")));
    }
}
