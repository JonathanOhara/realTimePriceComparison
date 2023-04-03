package interfaces.search.shops;

import comom.Util;
import interfaces.search.SeleniumSearch;
import objects.ProductType;
import objects.Shop;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class SoubaratoSearch extends SeleniumSearch {

	@Override
	public boolean isShopEnabled() {
		return false;
	}

	@Override
	public String getName() {
		return "Soubarato";
	}

	@Override
	public String getMainUrl() {
		return "https://www.soubarato.com.br/";
	}

	@Override
	public String getSearchPattern(ProductType productType) {
		return "https://www.soubarato.com.br/busca/<BUSCA>";
	}

	@Override
	protected boolean completeIndividualUrl() {
		return true;
	}

	@Override
	protected String getProductListCssQuery() {
 		return "div.row.product-grid.no-gutters.main-grid > div";
	}

	@Override
	protected String getProductNameCssQuery() {
		return ".product-name";
	}

	@Override
	protected String getProductPriceCssQuery() {
		return ".price";
	}

	@Override
	protected String replaceUrl(Shop shop, String productName) throws MalformedURLException, URISyntaxException {
		return Util.prepareUrlMode3(shop.getSearchPattern(), productName);
	}

}
