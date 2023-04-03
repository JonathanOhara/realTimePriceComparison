package objects.normalizer.impl;

import comom.SeleniumUtil;
import objects.normalizer.PageDocument;
import objects.normalizer.PageElement;
import org.openqa.selenium.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

public class SeleniumPageDocument implements PageDocument {

    @Override
    public void init() {
        SeleniumUtil.getDriver();
    }

    @Override
    public boolean connect(String url) {
        return connect(url, 0);
    }


    public boolean connect(String url, int retry) {
        Random random = new Random();
        int minWait = 100;
        int maxWait = 400;

        try {
            Thread.sleep(random.nextInt(maxWait - minWait) + minWait) ;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try{
            SeleniumUtil.getDriver().get(url);
        }catch (WebDriverException ex){
            if(retry++ < 3){
                System.err.println("Ex: " + ex.getCause() );
                System.err.println("Connect error number: " + retry + ". Retrying..." );
                SeleniumUtil.closeDriver();
                init();
                connect(url, retry);
            }else{
                return false;
            }
        }
        return true;

    }

    @Override
    public String getCurrentUrl() {
        return SeleniumUtil.getDriver().getCurrentUrl();
    }

    @Override
    public List<PageElement> getByCssSelector(String selector) {
        List<WebElement> elements = SeleniumUtil.getDriver().findElements(By.cssSelector(selector));
        return elements.stream().map(el -> new SeleniumPageElement(el)).collect(Collectors.toList());
    }

    @Override
    public Object getSourceObject() {
        return SeleniumUtil.getDriver();
    }


}
