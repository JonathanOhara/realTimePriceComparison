package interfaces.search.shops;

import comom.Util;
import interfaces.search.SeleniumSearch;
import objects.ProductType;
import objects.Shop;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class ShopeeSeleniumSearch extends SeleniumSearch {

	@Override
	protected void afterConnectUrl(Shop shop, String productName) throws InterruptedException {
		Thread.sleep(3000);
		super.afterConnectUrl(shop, productName);
	}

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
		return "https://shopee.com.br/search?category=26942&keyword=<BUSCA>&subcategory=26957";
	}

	@Override
	protected void afterFindProductName(String previewName) {
		WebDriver driver = (WebDriver) pageDocument.getSourceObject();

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
		return "div._3GAFiR > div.ZG__4J > div";
	}

	@Override
	protected String getProductPriceCssQuery() {
		return "div._3_FVSo > div.zp9xm9.kNGSLn.l-u0xK";
	}

	@Override
	protected String replaceUrl(Shop shop, String productName) throws MalformedURLException, URISyntaxException {
		return Util.prepareUrlMode2(shop.getSearchPattern(), productName);
	}
	
	
}
