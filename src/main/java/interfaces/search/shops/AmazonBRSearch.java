package interfaces.search.shops;

import comom.Util;
import interfaces.search.JsoupSearch;
import interfaces.search.SeleniumSearch;
import objects.ProductType;
import objects.Shop;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class AmazonBRSearch extends SeleniumSearch {

	@Override
	protected String priceSanatize(String price) {
		return super.priceSanatize(price.replace("\n", ","));
	}

	@Override
	public String getName() {
		return "AmazonBR";
	}

	@Override
	public String getMainUrl() {
		return "https://www.amazon.com.br/";
	}

	@Override
	public String getSearchPattern(ProductType productType) {
		return "https://www.amazon.com.br/s?k=<BUSCA>&i=videogames";
	}

	@Override
	protected boolean completeIndividualUrl() {
		return true;
	}

	@Override
	protected String getProductListCssQuery() {
		return ".s-main-slot > div[data-component-type='s-search-result']";
	}

	@Override
	protected String getProductNameCssQuery() {
		return "h2 > a > span";
	}

	@Override
	protected String getProductPriceCssQuery() {
		return "span.a-price[data-a-color='base'] > span:nth-child(2)";
	}

	@Override
	protected String replaceUrl(Shop shop, String productName) throws MalformedURLException, URISyntaxException {
		return Util.prepareUrlMode1(shop.getSearchPattern(), productName);
	}
}
