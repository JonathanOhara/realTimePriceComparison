package objects.normalizer.impl;

import objects.normalizer.PageElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class SeleniumPageElement implements PageElement {
    private WebElement webElement;

    public SeleniumPageElement(WebElement webElement) {
        this.webElement = webElement;
    }

    @Override
    public List<PageElement> getByCssSelector(String selector) {
        List<WebElement> elements = webElement.findElements(By.cssSelector(selector));
        return elements.stream().map(el -> new SeleniumPageElement(el)).collect(Collectors.toList());
    }

    @Override
    public String getText() {
        return webElement.getText();
    }

    @Override
    public String getAttribute(String attributeName) {
        return webElement.getAttribute(attributeName);
    }
}
