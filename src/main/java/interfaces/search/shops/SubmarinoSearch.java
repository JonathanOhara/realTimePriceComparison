package interfaces.search.shops;

import comom.Util;
import interfaces.search.JsoupSearch;
import objects.ProductType;
import objects.Shop;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class SubmarinoSearch extends JsoupSearch {

	@Override
	public String getName() {
		return "Submarino";
	}

	@Override
	public String getMainUrl() {
		return "https://www.submarino.com.br/";
	}

	@Override
	public String getSearchPattern(ProductType productType) {
		return "https://www.submarino.com.br/busca/<BUSCA>";
	}

	@Override
	protected boolean completeIndividualUrl() {
		return true;
	}

	@Override
	protected String getProductListCssQuery() {
		return ".iFeuoP > div";
	}

	@Override
	protected String getProductNameCssQuery() {
		return "h3";
	}

	@Override
	protected String getProductPriceCssQuery() {
		return ".pXfdS";
	}

	@Override
	protected String replaceUrl(Shop shop, String productName) throws MalformedURLException, URISyntaxException {
		return Util.prepareUrlMode3(shop.getSearchPattern(), productName);
	}

}
