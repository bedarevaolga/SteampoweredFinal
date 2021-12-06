package framework.elements;

import org.openqa.selenium.By;

public class Button extends BaseElement{

    public Button(final By locator, final String name) {
        super(locator, name);
    }


    public Button(By locator) {
        super(locator);
    }

    public Button(String strLocator,  String name) {

        super(By.xpath(String.format(strLocator, name)));
    }
}
