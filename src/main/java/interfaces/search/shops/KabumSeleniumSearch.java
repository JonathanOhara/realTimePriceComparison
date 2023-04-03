package interfaces.search.shops;

import comom.Util;
import interfaces.search.SeleniumSearch;
import objects.ProductType;
import objects.Shop;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class KabumSeleniumSearch extends SeleniumSearch {

	@Override
	public boolean isShopEnabled() {
		return false;
	}

	@Override
	public String getName() {
		return "Kabum";
	}

	@Override
	public String getMainUrl() {
		return "https://www.kabum.com.br/";
	}

	@Override
	public String getSearchPattern(ProductType productType) {
		return "https://www.kabum.com.br/busca/<BUSCA>";
	}

	@Override
	protected boolean isPriceValid(String price) {
		return price.indexOf("--") == -1 && super.isPriceValid(price);
	}

	@Override
	protected String getProductListCssQuery() {
		return "main > .productCard";
	}

	@Override
	protected String getProductNameCssQuery() {
		return ".nameCard";
	}

	@Override
	protected String getProductPriceCssQuery() {
		return "span.priceCard";
	}

	@Override
	protected String replaceUrl(Shop shop, String productName) throws MalformedURLException, URISyntaxException {
		return Util.prepareUrlMode3(shop.getSearchPattern(), productName);
	}
	
}
