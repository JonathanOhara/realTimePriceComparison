package objects.normalizer.impl;

import comom.SeleniumUtil;
import objects.normalizer.PageDocument;
import objects.normalizer.PageElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class SeleniumPageDocument implements PageDocument {
    protected WebDriver driver;

    @Override
    public void init() {
        driver = SeleniumUtil.getDriver();
    }

    @Override
    public void connect(String url) {
        Random random = new Random();
        int minWait = 200;
        int maxWait = 500;

        try {
            Thread.sleep(random.nextInt(maxWait - minWait) + minWait) ;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try{
            driver.get(url);
        }catch (WebDriverException ex){
            ex.printStackTrace();

        }

    }

    @Override
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    @Override
    public List<PageElement> getByCssSelector(String selector) {
        List<WebElement> elements = driver.findElements(By.cssSelector(selector));
        return elements.stream().map(el -> new SeleniumPageElement(el)).collect(Collectors.toList());
    }

    @Override
    public Object getSourceObject() {
        return driver;
    }


}
