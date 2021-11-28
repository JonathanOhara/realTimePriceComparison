package interfaces.search.shops;

import comom.Util;
import interfaces.search.JsoupSearch;
import objects.ProductType;
import objects.Shop;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class TrilogyNintendoSearch extends JsoupSearch {

	@Override
	public String getName() {
		return "Trilogy Nintendo";
	}

	@Override
	public String getMainUrl() {
		return "https://nintendo.trilogygames.com.br/index.php?route=common/home";
	}

	@Override
	public String getSearchPattern(ProductType productType) {
		return "https://nintendo.trilogygames.com.br/index.php?search=<BUSCA>&route=product%2Fsearch";
	}

	@Override
	protected String getProductListCssQuery() {
		return ".product-layout";
	}

	@Override
	protected String getProductNameCssQuery() {
		return "h4 > a";
	}

	@Override
	protected String getProductPriceCssQuery() {
		return ".avista > span";
	}

	@Override
	protected String replaceUrl(Shop shop, String productName) throws MalformedURLException, URISyntaxException {
		return Util.prepareUrlMode1(shop.getSearchPattern(), productName);
	}
	
}
