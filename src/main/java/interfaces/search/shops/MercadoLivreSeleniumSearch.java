package interfaces.search.shops;

import comom.Util;
import interfaces.search.SeleniumSearch;
import objects.ProductType;
import objects.Shop;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class MercadoLivreSeleniumSearch extends SeleniumSearch {

	@Override
	protected void afterConnectUrl(Shop shop, String productName) throws InterruptedException {
		Thread.sleep(1000);
		super.afterConnectUrl(shop, productName);
	}

	@Override
	public String getName() {
		return "Mercado Livre";
	}

	@Override
	public String getMainUrl() {
		return "https://www.mercadolivre.com.br/";
	}

	@Override
	public String getSearchPattern(ProductType productType) {
		switch (productType){
			case DEFAULT:
				return "https://games.mercadolivre.com.br/games/<BUSCA>";
			case NDS:
				return "https://games.mercadolivre.com.br/video-games/nintendo-ds/<BUSCA>";
			case N3DS:
				return "https://games.mercadolivre.com.br/video-games/nintendo-3ds/<BUSCA>";
			case PSP:
				return "https://games.mercadolivre.com.br/video-games/psp/<BUSCA>";
			case PSVITA:
				return "https://games.mercadolivre.com.br/video-games/ps-vita/<BUSCA>";
			case SWITCH:
				return "https://games.mercadolivre.com.br/video-games/nintendo-switch/<BUSCA>";
		}
		return "https://lista.mercadolivre.com.br/<BUSCA>";
//		return "https://games.mercadolivre.com.br/games/<BUSCA>";
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
	protected String getProductListCssQuery() {
		return "section.ui-search-results > ol > li";
	}

	@Override
	protected String getProductListCssQueryAlternative() {
		return "section.ui-search-results > ol > div > li";
	}

	@Override
	protected String getProductNameCssQuery() {
		return "h2.ui-search-item__title";
	}

	@Override
	protected String getProductPriceCssQuery() {
		return "span.andes-money-amount.ui-search-price__part.ui-search-price__part--medium.andes-money-amount--cents-superscript";
	}

	@Override
	protected String replaceUrl(Shop shop, String productName) throws MalformedURLException, URISyntaxException {
		return Util.prepareUrlMode3(shop.getSearchPattern(), productName);
	}

}
