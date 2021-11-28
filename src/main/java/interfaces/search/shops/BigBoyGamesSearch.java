package interfaces.search.shops;

import comom.Util;
import interfaces.search.JsoupSearch;
import objects.ProductType;
import objects.Shop;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class BigBoyGamesSearch extends JsoupSearch {

	@Override
	public String getName() {
		return "Big Boy Games";
	}

	@Override
	public String getMainUrl() {
		return "http://www.bigboygames.com.br/";
	}

	@Override
	public String getSearchPattern(ProductType productType) {
		return "https://www.bigboygames.com.br/loja/busca.php?palavra_busca=<BUSCA>";
	}

	@Override
	protected String getProductListCssQuery() {
		return ".showcase-catalog > ul > li";
	}

	@Override
	protected String getProductNameCssQuery() {
		return ".product-name";
	}

	@Override
	protected String getProductPriceCssQuery() {
		return ".preco-avista";
	}

	@Override
	protected String replaceUrl(Shop shop, String productName) throws MalformedURLException, URISyntaxException {
		return Util.prepareUrlMode1(shop.getSearchPattern(), productName);
	}
	
}
