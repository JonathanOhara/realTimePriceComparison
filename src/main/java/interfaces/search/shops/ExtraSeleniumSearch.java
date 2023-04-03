package interfaces.search.shops;

import comom.Util;
import interfaces.search.SeleniumSearch;
import objects.ProductType;
import objects.Shop;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class ExtraSeleniumSearch extends SeleniumSearch {

	@Override
	public boolean isShopEnabled() {
		return false;
	}

	@Override
	public String getName() {
		return "Extra";
	}

	@Override
	public String getMainUrl() {
		return "https://www.extra.com.br/";
	}

	@Override
	public String getSearchPattern(ProductType productType) {
		return "https://www.extra.com.br/<BUSCA>/b";
	}

	@Override
	protected void afterConnectUrl(Shop shop, String productName) throws InterruptedException {
		WebDriver driver = (WebDriver) pageDocument.getSourceObject();

		try {
			Thread.sleep(100);
			if(driver.findElements(By.cssSelector(getProductPriceCssQuery())).isEmpty()) {

				if(driver.findElements(By.cssSelector(".ddbJTM")).size() > 1) {
					//TODO: arrumar gambiarra
					driver.findElements(By.cssSelector(".ddbJTM")).get(1).findElements(By.cssSelector("input[type='checkbox']")).get(0).click();
					Thread.sleep(1000);
					driver.findElements(By.cssSelector(".ddbJTM input[type=checkbox]:checked")).get(0).click();
				}else{
					driver.navigate().refresh();
				}
				Thread.sleep(2000);
			}


		} catch (InterruptedException e) {
			e.printStackTrace();
			Thread.sleep(30000);
		}
	}

	@Override
	protected String getProductListCssQuery() {
		return "ul.eCZTbf > li";
	}

	@Override
	protected String getProductNameCssQuery() {
		return "p.ePzNqK";
	}

	@Override
	protected String getProductPriceCssQuery() {
		return ".dtxHql";
	}

	@Override
	protected String replaceUrl(Shop shop, String productName) throws MalformedURLException, URISyntaxException {
		return Util.prepareUrlMode3(shop.getSearchPattern(), productName);
	}

}
