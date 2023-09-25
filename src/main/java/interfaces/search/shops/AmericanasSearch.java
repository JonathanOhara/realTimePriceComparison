package interfaces.search.shops;

import comom.Util;
import interfaces.search.JsoupSearch;
import objects.ProductType;
import objects.Shop;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class AmericanasSearch extends JsoupSearch {

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
		return ".product-name__Name-sc-1jrnqy1-0";
	}

	@Override
	protected String getProductPriceCssQuery() {
		return ".price-info__ListPriceWithMargin-sc-1xm1xzb-2";
	}

	@Override
	protected String replaceUrl(Shop shop, String productName) throws MalformedURLException, URISyntaxException {
		return Util.prepareUrlMode3(shop.getSearchPattern(), productName);
	}

}
