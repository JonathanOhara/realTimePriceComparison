package interfaces.search.shops;

import comom.Util;
import interfaces.search.SeleniumSearch;
import objects.ProductType;
import objects.Shop;
import objects.normalizer.PageElement;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class NetShoesSeleniumSearch extends SeleniumSearch {

	@Override
	public String getName() {
		return "Net Shoes";
	}

	@Override
	public String getMainUrl() {
		return "https://www.netshoes.com.br/";
	}

	@Override
	public String getSearchPattern(ProductType productType) {
		return "https://www.netshoes.com.br/busca?nsCat=Natural&q=<BUSCA>";
	}

	@Override
	protected String getIndividualUrl(Shop shop, PageElement productContainer) {
		String individualUrl;
		individualUrl = productContainer.getAttribute("href");
		return individualUrl;
	}

	@Override
	protected String getProductListCssQuery() {
		return "#item-list > .wrapper > a";
	}

	@Override
	protected String getProductNameCssQuery() {
		return "div.item-card__description__product-name";
	}

	@Override
	protected String getProductPriceCssQuery() {
		return "span[data-price='price']";
	}

	@Override
	protected String replaceUrl(Shop shop, String productName) throws MalformedURLException, URISyntaxException {
		return Util.prepareUrlMode1(shop.getSearchPattern(), productName);
	}
	
}
