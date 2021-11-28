package interfaces.search.shops;

import comom.Util;
import interfaces.search.SeleniumSearch;
import objects.ProductType;
import objects.Shop;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class FuturisticGamesSeleniumSearch extends SeleniumSearch {

	@Override
	public String getName() {
		return "Futuristic Games";
	}

	@Override
	public String getMainUrl() {
		return "https://www.futuristicgames.com.br/";
	}

	@Override
	public String getSearchPattern(ProductType productType) {
		return "https://www.futuristicgames.com.br/<BUSCA>";
	}

	@Override
	protected String priceSanatize(String price) {
		price = price.replaceAll("\\r|\\n", "");
		return super.priceSanatize(price);
	}

	@Override
	protected String getProductListCssQuery() {
		return "section.ui-search-results > ol > li";
	}

	@Override
	protected String getProductNameCssQuery() {
		return "h2.ui-search-item__title";
	}

	@Override
	protected String getProductPriceCssQuery() {
		return "span.price-tag-amount";
	}

	@Override
	protected String replaceUrl(Shop shop, String productName) throws MalformedURLException, URISyntaxException {
		return Util.prepareUrlMode3(shop.getSearchPattern(), productName);
	}

}
