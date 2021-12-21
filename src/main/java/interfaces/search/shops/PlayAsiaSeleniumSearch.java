package interfaces.search.shops;

import comom.Util;
import interfaces.search.SeleniumSearch;
import objects.ProductType;
import objects.Shop;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class PlayAsiaSeleniumSearch extends SeleniumSearch {

	@Override
	public String getName() {
		return "Play Asia";
	}

	@Override
	public String getMainUrl() {
		return "https://www.play-asia.com/";
	}

	@Override
	protected boolean isProductPage(String currentUrl) {
		return !currentUrl.contains("/search/");
	}

	@Override
	public String getSearchPattern(ProductType productType) {
		switch (productType){
			case DEFAULT:
				return "https://www.play-asia.com/search/<BUSCA>";
			case NDS:
				return "https://www.play-asia.com/search/<BUSCA>#fc=co:32";
			case N3DS:
				return "https://www.play-asia.com/search/<BUSCA>#fc=co:110";
			case PSP:
				return "https://www.play-asia.com/search/<BUSCA>#fc=co:33";
			case PSVITA:
				return "https://www.play-asia.com/search/<BUSCA>#fc=co:121";
			case SWITCH:
				return "https://www.play-asia.com/search/<BUSCA>#fc=co:405";
		}
		return "https://www.play-asia.com/search/<BUSCA>";
	}

	@Override
	protected boolean completeIndividualUrl() {
		return true;
	}

	@Override
	protected String getProductListCssQuery() {
		return "#n_pf_holder > div.p_prev";
	}

	@Override
	protected String getProductNameCssQuery() {
		return "span.p_prev_n";
	}

	@Override
	protected String getProductPriceCssQuery() {
		return "span.price_val";
	}

	@Override
	protected String getProductPagePriceCssQuery() {
		return "#price_pi > span.price_val";
	}

	@Override
	protected String replaceUrl(Shop shop, String productName) throws MalformedURLException, URISyntaxException {
		return Util.prepareUrlMode1(shop.getSearchPattern(), productName);
	}

}
