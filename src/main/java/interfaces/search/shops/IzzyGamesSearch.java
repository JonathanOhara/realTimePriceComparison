package interfaces.search.shops;

import comom.Util;
import interfaces.search.JsoupSearch;
import objects.ProductType;
import objects.Shop;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class IzzyGamesSearch extends JsoupSearch {

	@Override
	public String getName() {
		return "Izzy Games";
	}

	@Override
	public String getMainUrl() {
		return "https://www.izzygames.com.br/";
	}

	@Override
	public String getSearchPattern(ProductType productType) {
		return "https://www.izzygames.com.br/buscar?q=<BUSCA>";
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