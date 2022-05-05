package interfaces.search.shops;

import comom.Util;
import interfaces.search.SeleniumSearch;
import objects.ProductType;
import objects.Shop;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class RiHappySeleniumSearch extends SeleniumSearch {

	@Override
	protected void afterConnectUrl(Shop shop, String productName) throws InterruptedException {
		Thread.sleep(6000);
		super.afterConnectUrl(shop, productName);
	}

	@Override
	public String getName() {
		return "RiHappy";
	}

	@Override
	public String getMainUrl() {
		return "https://www.rihappy.com.br/";
	}

	@Override
	public String getSearchPattern(ProductType productType) {
		return "https://www.rihappy.com.br/<BUSCA>";
	}

	@Override
	protected boolean completeIndividualUrl() {
		return true;
	}

	@Override
	protected String getProductListCssQuery() {
		return "div.vtex-search-result-3-x-gallery > div";
	}

	@Override
	protected String getProductNameCssQuery() {
		return "h2 > span";
	}

	@Override
	protected String getProductPriceCssQuery() {
		return ".vtex-product-price-1-x-sellingPriceValue";
	}

	@Override
	protected String replaceUrl(Shop shop, String productName) throws MalformedURLException, URISyntaxException {
		return Util.prepareUrlMode2(shop.getSearchPattern(), productName);
	}
}