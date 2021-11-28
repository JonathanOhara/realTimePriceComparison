package interfaces.search;

import comom.SeleniumUtil;
import comom.Util;
import interfaces.Filter;
import objects.Game;
import objects.Product;
import objects.Shop;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public abstract class SeleniumSearch implements Search {

    protected WebDriver driver;

    @Override
    public List<Product> search(Shop shop, Game game, Filter filter) {
        List<Product> products = null;

        try{
            driver = SeleniumUtil.getDriver();

            String url = replaceUrl(shop, game.getName());

            System.out.println("\tURL Final: "+url);

            driver.get(url);
            shop.setSearchUrl(url);

            System.out.println("\t\tDocumento Lido");

            afterConnectUrl(shop, game.getName());

            List<WebElement> els = driver.findElements(By.cssSelector(getProductListCssQuery()));

            System.out.println("\t\tResultados: "+els.size());
            System.out.print("\t\tStatus: ");

            products = readEachProduct(shop, game, els, filter);

        }catch(Exception e){
            e.printStackTrace();

            try {
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        System.out.println();

        return products;
    }

    protected void afterConnectUrl(Shop shop, String productName) throws InterruptedException {
        Thread.sleep(4000);
    }

    private List<Product> readEachProduct(Shop shop, Game game, List<WebElement> els, Filter filter) throws IOException {
        String previewName;
        String individualUrl;
        String price;
        WebElement productContainer;
        List<Product> products = null;

        if( els.size() > 0 ){
            products = new ArrayList<>(els.size());

            for( WebElement element : els ){
                productContainer = element;

                List<WebElement> titleElement = productContainer.findElements(By.cssSelector(getProductNameCssQuery()));

                if(titleElement.size() == 0){

                    if(getAlternativeProductNameCssQuery() != null){
                        titleElement = productContainer.findElements(By.cssSelector(getAlternativeProductNameCssQuery()));
                    }
                    if(titleElement.size() == 0) {
                        System.out.print("1!");
                        continue;
                    }
                }

                previewName = titleElement.get(0).getText();
//                System.out.println("\t\tNome do Produto: "+previewName);

                if(  filter.filter(previewName, game.getName(), shop.getNome(), ignoreNameFilter()) ){
                    individualUrl = getIndividualUrl(shop, productContainer);

                    List<WebElement> priceElements = element.findElements(By.cssSelector(getProductPriceCssQuery()));

                    if(priceElements.size() > 0){
                        WebElement priceElement = priceElements.get(0);

                        price = priceElement.getText();

                        if(isPriceValid(price)){
                            price = priceSanatize(price);

                            if(validProduct(productContainer)) {
                                Product product = new Product(game, previewName, "", individualUrl, productContainer, price);
                                product = afterBuildProduct(product);
                                products.add(product);
                            }
                        }else{
                            System.out.print("2!");
                        }
                    }else{
                        System.out.print("3!");
                    }

                }else{
                    System.out.print("4!");
                }
                afterFindProductName(previewName);
            }

        }
        return products;
    }

    protected boolean ignoreNameFilter() {
        return false;
    }

    protected Product afterBuildProduct(Product product) {
        return product;
    }

    protected boolean validProduct(WebElement productContainer) {
        return true;
    }

    protected String getAlternativeProductNameCssQuery(){
        return null;
    }

    protected void afterFindProductName(String previewName){

    }

    protected boolean isPriceValid(String price) {
        return !price.isBlank();
    }

    protected String priceSanatize(String price){
        return price.trim();
    }

    protected String getIndividualUrl(Shop shop, WebElement productContainer) {
        String individualUrl;

        individualUrl = productContainer.findElements(By.cssSelector("a")).get(0).getAttribute("href");
        if(completeIndividualUrl()){
            individualUrl = Util.makeAbsoluteURL(shop.getMainUrl(), individualUrl );
        }

        return individualUrl;
    }

    protected boolean completeIndividualUrl() {
        return false;
    }

    protected abstract String getProductListCssQuery();

    protected abstract String getProductNameCssQuery();

    protected abstract String getProductPriceCssQuery();

    protected abstract String replaceUrl(Shop shop, String productName) throws MalformedURLException, URISyntaxException;

}
