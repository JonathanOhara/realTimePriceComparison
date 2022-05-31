package interfaces.search.shops;

import comom.Util;
import interfaces.search.JsoupSearch;
import objects.ProductType;
import objects.Shop;
import objects.normalizer.PageElement;
import objects.normalizer.impl.JsoupPageElement;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

import static comom.Util.readUrlNew;

public class CasasBahiaSeleniumSearch extends JsoupSearch {

	@Override
	public String getName() {
		return "Casas Bahia";
	}

	@Override
	public String getMainUrl() {
		return "https://www.casasbahia.com.br/";
	}

	@Override
	public String getSearchPattern(ProductType productType) {
		return "https://www.casasbahia.com.br/<BUSCA>/b";
	}

//	@Override
//	protected void afterConnectUrl(Shop shop, String productName) throws InterruptedException {
//
//		 WebDriver driver = (WebDriver) pageDocument.getSourceObject();
//
//		try {
//			Thread.sleep(100);
//			if(driver.findElements(By.cssSelector(getProductPriceCssQuery())).isEmpty()) {
//
//				if(driver.findElements(By.cssSelector(".ddbJTM")).size() > 1) {
//					//TODO: arrumar gambiarra
//					driver.findElements(By.cssSelector(".ddbJTM")).get(1).findElements(By.cssSelector("input[type='checkbox']")).get(0).click();
//					Thread.sleep(1000);
//					driver.findElements(By.cssSelector(".ddbJTM input[type=checkbox]:checked")).get(0).click();
//				}else{
//					driver.navigate().refresh();
//				}
//				Thread.sleep(2000);
//
//			}
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//			Thread.sleep(30000);
//		}
//	}


	@Override
	protected String getProductPriceManually(PageElement productContainer){
		String url = "https://npreco.api-casasbahia.com.br/Produtos/PrecoVenda/?idsproduto=<SKU>&utm_source=Google&utm_medium=BuscaOrganica&utm_campaign=DescontoEspecial";

		JsoupPageElement element = (JsoupPageElement) productContainer;

		String dataCy = element.getAttribute("data-cy");

		String sku = dataCy.substring(7);

		String jsonResponse = readUrlNew(url.replace("<SKU>", sku), null);

		String finalPrice = null;

		try {
			finalPrice = jsonResponse.substring(jsonResponse.indexOf("\"Preco\":") + 8, jsonResponse.indexOf(",\"PrecoSemDesconto\""));
		}catch (StringIndexOutOfBoundsException e){
			System.out.println("Price not found. JSON response:");
			System.out.println(jsonResponse);
			System.out.println();
		}

		System.out.println("finalPrice: "+finalPrice);

		return finalPrice;
	}

	@Override
	protected String getProductListCssQuery() {
		return "ul.eFvtpO > li";
	}

	@Override
	protected String getProductNameCssQuery() {
		return "h2.iBDOQj";
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
