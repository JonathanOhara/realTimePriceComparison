package interfaces.search.shops;

import comom.Util;
import interfaces.search.SeleniumSearch;
import objects.ProductType;
import objects.Shop;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class ShopeeSeleniumSearch extends SeleniumSearch {

	@Override
	public String getName() {
		return "Shopee";
	}

	@Override
	public String getMainUrl() {
		return "https://shopee.com.br/";
	}

	@Override
	public String getSearchPattern(ProductType productType) {
		return "https://shopee.com.br/search?keyword=<BUSCA>";
	}

	@Override
	protected void afterFindProductName(String previewName) {
		driver.findElement(By.cssSelector("body")).sendKeys(Keys.ARROW_DOWN);
		driver.findElement(By.cssSelector("body")).sendKeys(Keys.ARROW_DOWN);
		super.afterFindProductName(previewName);
	}

	@Override
	protected String getProductListCssQuery() {
		return ".shopee-search-item-result__items > div";
	}

	@Override
	protected String getProductNameCssQuery() {
		return "div.UjjMrh";
	}

	@Override
	protected String getProductPriceCssQuery() {
		return "div._1heB4J";
	}

	@Override
	protected String replaceUrl(Shop shop, String productName) throws MalformedURLException, URISyntaxException {
		return Util.prepareUrlMode2(shop.getSearchPattern(), productName);
	}
	
	
}
