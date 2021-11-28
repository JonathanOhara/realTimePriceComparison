package interfaces.search.shops;

import comom.Util;
import interfaces.search.JsoupSearch;
import objects.ProductType;
import objects.Shop;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class AtacadoDosJogosSearch extends JsoupSearch {

	@Override
	public String getName() {
		return "Atacado dos Jogos";
	}

	@Override
	public String getMainUrl() {
		return "https://www.atacadodosjogos.com.br/";
	}

	@Override
	public String getSearchPattern(ProductType productType) {
		return "https://www.atacadodosjogos.com.br/exibirpesquisa.php?busca=<BUSCA>";
	}

	@Override
	protected boolean completeIndividualUrl() {
		return true;
	}

	@Override
	protected String getProductListCssQuery() {
		return "section.vitrine > div";
	}

	@Override
	protected String getProductNameCssQuery() {
		return "h2.labelproduto > a";
	}

	@Override
	protected String getProductPriceCssQuery() {
		return "strong.labelprecoVendaDestaque";
	}

	@Override
	protected String replaceUrl(Shop shop, String productName) throws MalformedURLException, URISyntaxException {
		return Util.prepareUrlMode1(shop.getSearchPattern(), productName);
	}

}
