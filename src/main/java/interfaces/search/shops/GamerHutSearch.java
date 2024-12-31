package interfaces.search.shops;

import comom.Util;
import interfaces.search.JsoupSearch;
import objects.ProductType;
import objects.Shop;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class GamerHutSearch extends JsoupSearch {

	@Override
	public String getName() {
		return "Gamer Hut";
	}

	@Override
	public String getMainUrl() {
		return "https://www.gamerhut.com.br/";
	}

	@Override
	public String getSearchPattern(ProductType productType) {
		return "https://www.gamerhut.com.br/<BUSCA>";
	}

	@Override
	protected String priceSanatize(String price) {
		price = price.replaceAll("[\\r\\n]+", "");

		int indexOfRS = price.indexOf("R$");

		if(indexOfRS > -1) {
			price = price.substring(indexOfRS);
		}

		return super.priceSanatize(price);
	}

	@Override
	protected boolean completeIndividualUrl() {
		return true;
	}

	@Override
	protected String getProductListCssQuery() {
		return "#root-app > div > div.ui-search-main.ui-search-main--without-header.ui-search-main--only-products.shops__search-main > section > ol > div";
	}


	@Override
	protected String getProductListCssQueryAlternative() {
		return "#root-app > div > div.ui-search-main.ui-search-main--without-header.ui-search-main--only-products.shops__search-main > section > ol > li";
	}

	@Override
	protected String getProductNameCssQuery() {
		return "a.poly-component__title";
	}

	@Override
	protected String getProductPriceCssQuery() {
		return "span.andes-money-amount.andes-money-amount--cents-superscript";
	}

	@Override
	protected String replaceUrl(Shop shop, String productName) throws MalformedURLException, URISyntaxException {
		return Util.prepareUrlMode3(shop.getSearchPattern(), productName);
	}

}
