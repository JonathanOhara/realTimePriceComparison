package interfaces.search.shops;

import comom.Util;
import interfaces.search.SeleniumSearch;
import objects.ProductType;
import objects.Shop;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class FastShopSeleniumSearch extends SeleniumSearch {

	@Override
	public boolean isShopEnabled() {
		return false;
	}

	@Override
	public String getName() {
		return "Fast Shop";
	}

	@Override
	public String getMainUrl() {
		return "https://www.fastshop.com.br/";
	}

	@Override
	public String getSearchPattern(ProductType productType) {
		return "https://www.fastshop.com.br/web/s/<BUSCA>";
	}

	@Override
	protected String getProductListCssQuery() {
		return "app-product-list > div.row > app-product-item";
	}

	@Override
	protected String getProductNameCssQuery() {
		return "h3.prod-title";
	}

	@Override
	protected String getProductPriceCssQuery() {
		return "span.price-fraction";
	}

	@Override
	protected String replaceUrl(Shop shop, String productName) throws MalformedURLException, URISyntaxException {
		return Util.prepareUrlMode2(shop.getSearchPattern(), productName);
	}

}