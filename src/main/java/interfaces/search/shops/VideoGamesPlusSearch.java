package interfaces.search.shops;

import comom.Util;
import interfaces.search.JsoupSearch;
import objects.ProductType;
import objects.Shop;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

import static comom.Keys.CAD_VALUE;

public class VideoGamesPlusSearch extends JsoupSearch {

	@Override
	public String getName() {
		return "VideoGamePlus";
	}

	@Override
	public String getMainUrl() {
		return "https://videogamesplus.ca/";
	}

	@Override
	public String getSearchPattern(ProductType productType) {
		//return "https://videogamesplus.ca/search?type=article%2Cpage%2Cproduct&q=<BUSCA>";
		switch (productType){
			case DEFAULT:
				return "https://videogamesplus.ca/search?type=article%2Cpage%2Cproduct&q=<BUSCA>";
			case NDS:
				return "https://videogamesplus.ca/search?type=article%2Cpage%2Cproduct&q=<BUSCA>+product_type%3ANINTENDO+DS";
			case N3DS:
				return "https://videogamesplus.ca/search?type=article%2Cpage%2Cproduct&q=<BUSCA>+product_type%3ANINTENDO+3DS";
			case PSP:
				return "https://videogamesplus.ca/search?type=article%2Cpage%2Cproduct&q=<BUSCA>+product_type%3APSP";
			case PSVITA:
				return "https://videogamesplus.ca/search?type=article%2Cpage%2Cproduct&q=<BUSCA>+product_type%3APS+VITA";
			case SWITCH:
				return "https://videogamesplus.ca/search?type=article%2Cpage%2Cproduct&q=<BUSCA>+product_type%3ANINTENDO+SWITCH";
		}
		return "https://videogamesplus.ca/search?type=article%2Cpage%2Cproduct&q=<BUSCA>";
	}

	@Override
	protected boolean completeIndividualUrl() {
		return true;
	}

	@Override
	protected String getProductListCssQuery() {
 		return "ul.productgrid--items > li";
	}

	@Override
	protected String getProductNameCssQuery() {
		return "h2.productitem--title";
	}

	@Override
	protected String getProductPriceCssQuery() {
		return ".price__current > span.money";
	}

	@Override
	protected String priceSanatize(String price) {
		if(!price.isBlank()) {
			price = price.trim();
			price = price.substring(1);
			price = price.replace("CAD","").trim();
			double cadPrice = Double.parseDouble(price);
			price = String.valueOf(cadPrice * CAD_VALUE);
		}

		return price;
	}

	@Override
	protected String replaceUrl(Shop shop, String productName) throws MalformedURLException, URISyntaxException {
		return Util.prepareUrlMode1(shop.getSearchPattern(), productName);
	}

}
