package interfaces.search.shops;

import comom.Util;
import interfaces.search.JsoupSearch;
import interfaces.search.SeleniumSearch;
import objects.ProductType;
import objects.Shop;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class CarvalhoGamesSearch extends SeleniumSearch {

	@Override
	public String getName() {
		return "Carvalho Games";
	}

	@Override
	public String getMainUrl() {
		return "https://www.carvalhogames.com.br/";
	}

	@Override
	public String getSearchPattern(ProductType productType) {
		return "https://www.carvalhogames.com.br/buscar?q=<BUSCA>";
	}

	@Override
	protected String getProductListCssQuery() {
		return "#listagemProdutos > ul > div";
	}

	@Override
	protected String getProductNameCssQuery() {
		return ".info-produto > a";
	}

	@Override
	protected String getProductPriceCssQuery() {
		return "strong.titulo";
	}

	@Override
	protected String replaceUrl(Shop shop, String productName) throws MalformedURLException, URISyntaxException {
		return Util.prepareUrlMode1(shop.getSearchPattern(), productName);
	}
	
}
