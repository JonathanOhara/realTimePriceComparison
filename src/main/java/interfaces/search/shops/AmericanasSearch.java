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
		return ".product-grid-new__GridItem-sc-1fb03nt-0 .product-grid-new__ColGridItem-sc-1fb03nt-1";
	}

	@Override
	protected String getProductNameCssQuery() {
		return ".product-name__Name-sc-1shovj0-0";
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
