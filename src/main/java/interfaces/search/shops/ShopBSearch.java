package interfaces.search.shops;

import comom.Util;
import interfaces.search.JsoupSearch;
import objects.ProductType;
import objects.Shop;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class ShopBSearch extends JsoupSearch {

	@Override
	public String getName() {
		return "ShopB";
	}

	@Override
	public String getMainUrl() {
		return "http://www.shopb.com.br/";
	}

	@Override
	public String getSearchPattern(ProductType productType) {
		return "http://www.shopb.com.br/buscar?q=<BUSCA>";
	}

	@Override
	protected String getProductListCssQuery() {
		return ".listagem-item";
	}

	@Override
	protected String getProductNameCssQuery() {
		return ".info-produto a";
	}

	@Override
	protected String getProductPriceCssQuery() {
		return ".preco-promocional";
	}

	@Override
	protected String replaceUrl(Shop shop, String productName) throws MalformedURLException, URISyntaxException {
		return Util.prepareUrlMode1(shop.getSearchPattern(), productName);
	}
}
