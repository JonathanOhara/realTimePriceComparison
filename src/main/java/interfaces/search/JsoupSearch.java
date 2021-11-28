package interfaces.search;

import comom.Util;
import interfaces.Filter;
import objects.Game;
import objects.Product;
import objects.Shop;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public abstract class JsoupSearch implements Search {

    @Override
    public List<Product> search(Shop shop, Game game, Filter filter) {
        List<Product> products = null;

        Document document;

        try{
            document = readResults(shop, game.getName());

            System.out.println("\t\tDocumento Lido");

            Elements els = document.select( getProductListCssQuery() );

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

    protected List<Product> readEachProduct(Shop shop, Game game, Elements els, Filter filter) throws IOException {
        String previewName;
        String individualUrl;
        String price;
        Element productContainer;
        List<Product> products = null;

        if( els.size() > 0 ){
            products = new ArrayList<>(els.size());

            for( Element element : els ){
                productContainer = element;

                Elements titleElement = productContainer.select(getProductNameCssQuery());

                if(titleElement.size() == 0){

                    if(getAlternativeProductNameCssQuery() != null){
                        titleElement = productContainer.select(getAlternativeProductNameCssQuery());
                    }
                    if(titleElement.size() == 0) {
                        System.out.print("1!");
                        continue;
                    }
                }


                previewName = titleElement.get(0).text();
//                System.out.println("\t\tNome do Produto: "+previewName);

                if( filter.filter(previewName, game.getName(), shop.getNome(),ignoreNameFilter()) ){
                    individualUrl = getIndividualUrl(shop, productContainer);

                    Elements priceElements = element.select(getProductPriceCssQuery());

                    if(priceElements.size() > 0){
                        Element priceElement = priceElements.get(0);

                        price = priceElement.text();

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

    protected boolean validProduct(Element productContainer) {
        return true;
    }

    protected void afterFindProductName(String previewName){

    }

    protected String getAlternativeProductNameCssQuery(){
        return null;
    }

    protected boolean isPriceValid(String price) {
        return !price.isBlank();
    }

    protected String priceSanatize(String price){
        return price.trim();
    }

    protected String getIndividualUrl(Shop shop, Element productContainer) {
        String individualUrl;

        individualUrl = productContainer.select("a").first().attr("href");
        if(completeIndividualUrl()){
            individualUrl = Util.makeAbsoluteURL(shop.getMainUrl(), individualUrl );
        }

        return individualUrl;
    }

    protected boolean completeIndividualUrl() {
        return false;
    }

    protected Document readResults(Shop shop, String productName)	throws IOException, URISyntaxException {
        Document document;
        String url = replaceUrl(shop, productName);
        shop.setSearchUrl(url);

        System.out.println("\tURL Final: "+url);

        document = Util.readUrlDocument(url);
        return document;
    }

    protected abstract String getProductListCssQuery();

    protected abstract String getProductNameCssQuery();

    protected abstract String getProductPriceCssQuery();

    protected abstract String replaceUrl(Shop shop, String productName) throws MalformedURLException, URISyntaxException;

}
