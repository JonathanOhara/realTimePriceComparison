package interfaces.search;

import comom.Util;
import interfaces.Filter;
import objects.Game;
import objects.Product;
import objects.Shop;
import objects.normalizer.PageDocument;
import objects.normalizer.PageElement;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public abstract class UnifiedSearch implements Search {

    public boolean isShopEnabled(){
        return true;
    }
    public List<Product> search(PageDocument pageDocument, Shop shop, Game game, Filter filter) {
        List<Product> products = null;

        try{
            pageDocument.init();

            String url = replaceUrl(shop, game.getName());

            System.out.println("\tURL Final: "+url);

            if( pageDocument.connect(url) ) {
                shop.setSearchUrl(url);

                System.out.println("\t\tDocumento Lido");

                afterConnectUrl(shop, game.getName());

                List<PageElement> els = pageDocument.getByCssSelector(getProductListCssQuery());

                if(els.isEmpty() && getProductListCssQueryAlternative() != null){
                    System.out.println("\t\tUsando Selector Alternativo");
                    els = pageDocument.getByCssSelector(getProductListCssQueryAlternative());
                }

                System.out.println("\t\tResultados: " + els.size());
                System.out.print("\t\tStatus: ");

                products = readEachProduct(pageDocument, shop, game, els, filter);
            }else {
                System.err.println("Cannot connect to: "+url);
            }

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
        Thread.sleep(2000);
    }

    private List<Product> readEachProduct(PageDocument pageDocument, Shop shop, Game game, List<PageElement> els, Filter filter) throws IOException {
        String previewName;
        List<Product> products = null;

        if( els.size() > 0 ){
            products = new ArrayList<>(els.size());

            for( PageElement productContainer : els ){

                List<PageElement> titleElement = productContainer.getByCssSelector(getProductNameCssQuery());

                if(titleElement.size() == 0){

                    if(getAlternativeProductNameCssQuery() != null){
                        titleElement = productContainer.getByCssSelector(getAlternativeProductNameCssQuery());
                    }
                    if(titleElement.size() == 0) {
                        System.out.print("Title!");
                        continue;
                    }
                }

                previewName = titleElement.get(0).getText();
//                System.out.println("\t\tNome do Produto: "+previewName);

                if( filter.filter(previewName, game.getName(), shop.getNome(), ignoreNameFilter()) ){
                    List<PageElement> priceElements = new ArrayList<>();
                    if(getProductPriceCssQuery() != null && !getProductPriceCssQuery().isBlank()) {
                        priceElements = productContainer.getByCssSelector(getProductPriceCssQuery());
                    }
                    readProduct(game, previewName, productContainer, products, priceElements, getIndividualUrl(shop, productContainer));
                }else{
                    System.out.print("Filter!");
                }
                afterFindProductName(previewName);
            }
        }else if(isProductPage(pageDocument.getCurrentUrl()) && getProductPagePriceCssQuery() != null){
            System.out.println("\t\tProduct page found ");
            products = new ArrayList<>(els.size());
            List<PageElement> priceElements = pageDocument.getByCssSelector(getProductPagePriceCssQuery());
            readProduct(game, game.getName(), null, products, priceElements, pageDocument.getCurrentUrl());
        }

        return products;
    }

    private void readProduct(Game game, String previewName, PageElement productContainer, List<Product> products, List<PageElement> priceElements, String individualUrl) {
        String price;

        String productPriceManually = getProductPriceManually(productContainer);
        if(priceElements.size() > 0 || productPriceManually != null) {
            if (priceElements.size() > 0){
                PageElement priceElement = priceElements.get(0);
                price = priceElement.getText();
            }else{
                price = productPriceManually;
            }

            if(isPriceValid(price)){
                price = priceSanatize(price);

                if(validProduct(productContainer)) {
                    Product product = new Product(game, previewName, "", individualUrl, productContainer, price);
                    product = afterBuildProduct(product);
                    products.add(product);
                }
            }else{
                System.out.print("InvPrice!");
            }
        }else{
            System.out.print("NoPrice!");
        }
    }

     protected boolean isProductPage(String currentUrl) {
        return false;
    }

    protected String getProductPagePriceCssQuery(){
        return null;
    }

    protected boolean ignoreNameFilter() {
        return false;
    }

    protected Product afterBuildProduct(Product product) {
        return product;
    }

    protected boolean validProduct(PageElement productContainer) {
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

    protected String getIndividualUrl(Shop shop, PageElement productContainer) {
        String individualUrl;

        individualUrl = productContainer.getByCssSelector("a").get(0).getAttribute("href");
        if(completeIndividualUrl()){
            individualUrl = Util.makeAbsoluteURL(shop.getMainUrl(), individualUrl );
        }

        return individualUrl;
    }

    protected boolean completeIndividualUrl() {
        return false;
    }

    protected abstract String getProductListCssQuery();

    protected String getProductListCssQueryAlternative() {
        return null;
    }

    protected abstract String getProductNameCssQuery();

    protected abstract String getProductPriceCssQuery();

    protected String getProductPriceManually(PageElement pageElement){
        return null;
    }

    protected abstract String replaceUrl(Shop shop, String productName) throws MalformedURLException, URISyntaxException;

}
