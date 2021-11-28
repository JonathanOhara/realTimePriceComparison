package interfaces.search.shops;

import comom.Util;
import interfaces.search.SeleniumSearch;
import objects.ProductType;
import objects.Shop;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class CarrefourSeleniumSearch extends SeleniumSearch {

	@Override
	public String getName() {
		return "Carrefour";
	}

	@Override
	public String getMainUrl() {
		return "https://www.carrefour.com.br/";
	}

	@Override
	public String getSearchPattern(ProductType productType) {
		return "https://www.carrefour.com.br/busca/<BUSCA>";
	}

	@Override
	protected String getProductListCssQuery() {
		return ".vtex-search-result-3-x-galleryItem";
	}

	@Override
	protected String getProductNameCssQuery() {
		return "h2.carrefourbr-carrefour-components-0-x-productName";
	}

	@Override
	protected String getProductPriceCssQuery() {
		return ".vtex-product-price-1-x-currencyContainer";
	}

	@Override
	protected String replaceUrl(Shop shop, String productName) throws MalformedURLException, URISyntaxException {
		return Util.prepareUrlMode2(shop.getSearchPattern(), productName);
	}
	
	
}
