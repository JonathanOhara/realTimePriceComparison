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
		return "https://www.atacadodosjogos.com.br/<BUSCA>";
	}

	@Override
	protected String priceSanatize(String price) {
		price = price.replaceAll("[\\r\\n]+", "");

		int indexOfRS = price.indexOf("R$");

		if(indexOfRS > -1) {
			price = price.substring(indexOfRS);
		}

		return super.priceSanatize(price);
	}

	@Override
	protected boolean completeIndividualUrl() {
		return true;
	}

	@Override
	protected String getProductListCssQuery() {
		return "ol.ui-search-layout > li";
	}

	@Override
	protected String getProductNameCssQuery() {
		return "h2.ui-search-item__title";
	}

	@Override
	protected String getProductPriceCssQuery() {
		return "span.andes-money-amount.ui-search-price__part.shops__price-part.andes-money-amount--cents-superscript";
	}

	@Override
	protected String replaceUrl(Shop shop, String productName) throws MalformedURLException, URISyntaxException {
		return Util.prepareUrlMode3(shop.getSearchPattern(), productName);
	}

}
