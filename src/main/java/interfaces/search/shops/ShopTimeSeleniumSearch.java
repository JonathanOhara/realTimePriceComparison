package interfaces.search.shops;

import comom.Util;
import interfaces.search.SeleniumSearch;
import objects.ProductType;
import objects.Shop;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class ShopTimeSeleniumSearch extends SeleniumSearch {

	@Override
	public boolean isShopEnabled() {
		return false;
	}

	@Override
	public String getName() {
		return "Shop Time";
	}

	@Override
	public String getMainUrl() {
		return "https://www.shoptime.com.br/";
	}

	@Override
	public String getSearchPattern(ProductType productType) {
		return "https://www.shoptime.com.br/busca/<BUSCA>";
	}

	@Override
	protected String getProductListCssQuery() {
		return ".iFeuoP > div";
	}

	@Override
	protected String getProductNameCssQuery() {
		return ".jXqYDg";
	}

	@Override
	protected String getProductPriceCssQuery() {
		return ".hYdDoe";
	}

	@Override
	protected String replaceUrl(Shop shop, String productName) throws MalformedURLException, URISyntaxException {
		return Util.prepareUrlMode3(shop.getSearchPattern(), productName);
	}

}
