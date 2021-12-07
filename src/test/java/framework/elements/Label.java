package framework.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;


public class Label extends BaseElement{

    public Label(final By locator, final String name) {
        super(locator, name);
    }


    public Label(By locator) {
        super(locator);
    }
    public Label(WebElement element) {
        super(element);
    }

    public Label(String strLocator,  String name) {

        super(By.xpath(String.format(strLocator, name)));
    }






}
