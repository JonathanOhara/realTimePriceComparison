package interfaces.search.shops;

import comom.Util;
import interfaces.search.JsoupSearch;
import interfaces.search.SeleniumSearch;
import objects.ProductType;
import objects.Shop;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class AmazonUSSearch extends SeleniumSearch {

	@Override
	public String getName() {
		return "AmazonUS";
	}

	@Override
	public String getMainUrl() {
		return "https://www.amazon.com/";
	}

	@Override
	public String getSearchPattern(ProductType productType) {
		return "https://www.amazon.com/s?k=<BUSCA>&i=videogames";
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
		return "span.a-price[data-a-color='base'] > span.a-offscreen";
	}

	@Override
	protected String replaceUrl(Shop shop, String productName) throws MalformedURLException, URISyntaxException {
		return Util.prepareUrlMode1(shop.getSearchPattern(), productName);
	}
}
