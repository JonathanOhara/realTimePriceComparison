package interfaces.search.shops;

import comom.Util;
import interfaces.search.SeleniumSearch;
import objects.ProductType;
import objects.Shop;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class MagazineLuizaSeleniumSearch extends SeleniumSearch {

	@Override
	public boolean isShopEnabled() {
		return false;
	}

	@Override
	public String getName() {
		return "Magazine Luiza";
	}

	@Override
	public String getMainUrl() {
		return "https://www.magazineluiza.com.br/";
	}

	@Override
	public String getSearchPattern(ProductType productType) {
		return "http://www.magazineluiza.com.br/busca/<BUSCA>/";
	}

	@Override
	protected String getProductListCssQuery() {
		return "div[data-testid='product-list'] > ul > li";
	}

	@Override
	protected String getProductNameCssQuery() {
		return "h2[data-testid='product-title']";
	}

	@Override
	protected String getProductPriceCssQuery() {
		return "p[data-testid='price-value']";
	}

	@Override
	protected String replaceUrl(Shop shop, String productName) throws MalformedURLException, URISyntaxException {
		return Util.prepareUrlMode1(shop.getSearchPattern(), productName);
	}

}
