package interfaces.search.shops;

import comom.Util;
import interfaces.search.JsoupSearch;
import objects.ProductType;
import objects.Shop;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class Shop4BRSearch extends JsoupSearch {

	@Override
	public String getName() {
		return "Shop4BR";
	}

	@Override
	public String getMainUrl() {
		return "https://www.shop4br.com/";
	}

	@Override
	public String getSearchPattern(ProductType productType) {
		return "https://www.shop4br.com/search/<BUSCA>";
	}

	@Override
	protected String priceSanatize(String price) {
		if(price.indexOf("R$") > 0){
			price = price.substring(price.indexOf("R$"));
		}
		return super.priceSanatize(price);
	}

	@Override
	protected String getProductListCssQuery() {
		return ".product_grid > div";
	}

	@Override
	protected String getProductNameCssQuery() {
		return "h3";
	}

	@Override
	protected String getProductPriceCssQuery() {
		return "b.price";
	}

	@Override
	protected String replaceUrl(Shop shop, String productName) throws MalformedURLException, URISyntaxException {
		return Util.prepareUrlMode1(shop.getSearchPattern(), productName);
	}
}