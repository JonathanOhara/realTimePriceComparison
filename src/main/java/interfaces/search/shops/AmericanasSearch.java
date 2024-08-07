package interfaces.search.shops;

import comom.Util;
import interfaces.search.JsoupSearch;
import interfaces.search.SeleniumSearch;
import objects.ProductType;
import objects.Shop;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class AmericanasSearch extends SeleniumSearch {

	@Override
	public String getName() {
		return "Americanas";
	}

	@Override
	public String getMainUrl() {
		return "https://www.americanas.com.br/";
	}

	@Override
	public String getSearchPattern(ProductType productType) {
		return "https://www.americanas.com.br/busca/<BUSCA>";
	}

	@Override
	protected boolean completeIndividualUrl() {
		return true;
	}

	@Override
	protected String getProductListCssQuery() {
 		return "div.grid__StyledGrid-sc-1man2hx-0.iFeuoP > div";
	}

	@Override
	protected String getProductNameCssQuery() {
		return ".product-name";
	}

	@Override
	protected String getProductPriceCssQuery() {
		return ".list-price";
	}

	@Override
	protected String replaceUrl(Shop shop, String productName) throws MalformedURLException, URISyntaxException {
		return Util.prepareUrlMode3(shop.getSearchPattern(), productName);
	}

}
