package interfaces.search.shops;

import comom.Util;
import interfaces.search.JsoupSearch;
import objects.ProductType;
import objects.Shop;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class GTAGamesSearch extends JsoupSearch {

	@Override
	public String getName() {
		return "GTA Games";
	}

	@Override
	public String getMainUrl() {
		return "https://www.gtagames.com.br/";
	}

	@Override
	public String getSearchPattern(ProductType productType) {
		return "https://www.gtagames.com.br/busca-busca_produtos?nome_produto=<BUSCA>";
	}

	@Override
	protected String getProductListCssQuery() {
		return ".containerListagem  > div.boxItemListagem";
	}

	@Override
	protected String getProductNameCssQuery() {
		return ".itemListagemNomeProduto";
	}

	@Override
	protected String getProductPriceCssQuery() {
		return ".itemListagemPorProduto > p";
	}

	@Override
	protected String replaceUrl(Shop shop, String productName) throws MalformedURLException, URISyntaxException {
		return Util.prepareUrlMode1(shop.getSearchPattern(), productName);
	}
	
}
