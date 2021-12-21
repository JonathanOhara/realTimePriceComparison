package interfaces.search.shops;

import comom.SeleniumUtil;
import comom.Util;
import interfaces.search.SeleniumSearch;
import objects.Product;
import objects.ProductType;
import objects.Shop;
import objects.normalizer.PageElement;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class EbaySeleniumSearch extends SeleniumSearch {

	@Override
	public String getName() {
		return "ebay";
	}

	@Override
	public String getMainUrl() {
		return "https://www.ebay.com/";
	}

//	@Override
//	public String getSearchPattern(ProductType productType) {
//		return "https://www.ebay.com/sch/139973/i.html?_from=R40&_nkw=<BUSCA>&LH_TitleDesc=0";
//	}

	@Override
	public String getSearchPattern(ProductType productType) {
		switch (productType){
			case DEFAULT:
				return "https://www.ebay.com/sch/139973/i.html?_from=R40&_nkw=<BUSCA>&LH_TitleDesc=0";
			case NDS:
				return "https://www.ebay.com/sch/i.html?_from=R40&_nkw=<BUSCA>&_sacat=0&rt=nc&Platform=Nintendo%2520DS&_dcat=139973";
			case N3DS:
				return "https://www.ebay.com/sch/i.html?_from=R40&_nkw=<BUSCA>&_sacat=0&_oaa=1&Platform=Nintendo%25203DS&rt=nc&Region%2520Code=NTSC%252DU%252FC%2520%2528US%252FCanada%2529&_dcat=139973";
			case PSP:
				return "https://www.ebay.com/sch/i.html?_from=R40&_nkw=<BUSCA>&_sacat=0&LH_TitleDesc=0&rt=nc&Platform=Sony%2520PSP&_dcat=139973";
			case PSVITA:
				return "https://www.ebay.com/sch/i.html?_dcat=139973&_fsrp=1&rt=nc&_from=R40&_nkw=<BUSCA>&_sacat=0&LH_TitleDesc=0&Platform=Sony%2520PlayStation%2520Vita";
			case SWITCH:
				return "https://www.ebay.com/sch/i.html?_from=R40&_nkw=<BUSCA>&_sacat=0&_oaa=1&rt=nc&Platform=Nintendo%2520Switch&_dcat=139973";
		}
		return "https://www.ebay.com/sch/139973/i.html?_from=R40&_nkw=<BUSCA>&LH_TitleDesc=0";
	}

	@Override
	protected void afterConnectUrl(Shop shop, String productName) throws InterruptedException {

//		JavascriptExecutor js = (JavascriptExecutor)driver;
//		js.executeScript("document.cookie = '__gads=ID=e11899c933242dfd:T=1637444988:S=ALNI_MYYlvRpFf2newXMa7fmyouk_hIdWw; ebay=%5Ejs%3D1%5Esbf%3D%23000000%5E; nonsession=BAQAAAXw0ACieAAaAADMACWN6pSQxMTcwMC0wMDUAygAgZVvYpDNmNjM2MGExMTdkMGFhYjA0M2UxY2E2MWZmZWNmMTg3AMsAAmGZeKwxM/7sBFb/r5OFfMW6mBvFfY64AORJ; npii=btguid/3f6360a117d0aab043e1ca61ffecf187655bd8a4^cguid/3f63674b17d0acf0d225fec8ff33e398655bd8a4^; dp1=bpbf/%2300e000e00000000000000000637aa524^bl/BRen-US655bd8a4^';");
//		Thread.sleep(100);
//		driver.navigate().refresh();


//		WebElement languageElement = driver.findElement(By.cssSelector("#gh-eb-Geo > div > a > .gh-eb-Geo-txt"));
//		String language = languageElement.getText();
//		if(!"English".equals(language)){
//			languageElement.click();
//			Thread.sleep(500);
//			driver.findElement(By.cssSelector("#gh-eb-Geo > div > .gh-submenu a")).click();
//		}

		super.afterConnectUrl(shop, productName);
	}

	@Override
	protected Product afterBuildProduct(Product product) {
		String shipmentCost = product.getPageElement().getByCssSelector(".s-item__logisticsCost").get(0).getText();

		if(shipmentCost.indexOf("R$") > -1) {
			String shipmentMsg = " shipping";
			if(shipmentCost.indexOf(" de frete") > -1){
				shipmentMsg = " de frete";
			}
			shipmentCost = shipmentCost.substring(shipmentCost.indexOf("R$"), shipmentCost.indexOf(shipmentMsg)).trim();

		}else{
			if(shipmentCost.contains("Free")){
				shipmentCost = "0";
			}else{
				shipmentCost = "999";
			}
		}

		product.setShipping(shipmentCost);

		return product;
	}

	@Override
	protected boolean validProduct(PageElement productContainer) {
		boolean valid = true;
		String shipmentCost = productContainer.getByCssSelector(".s-item__logisticsCost").get(0).getText();

		if(shipmentCost.toLowerCase().contains("não")){
			System.out.println("[ERROR] Item pode não enviar para o Brasil");
			valid = false;
		}

		return valid && super.validProduct(productContainer);
	}

	@Override
	protected boolean ignoreNameFilter() {
		if( SeleniumUtil.isUsingChromeProfile() ) {
			return false;
		}
		return true;

	}

	@Override
	protected String getProductListCssQuery() {
		return "#srp-river-results > ul > li";
	}

	@Override
	protected String getProductNameCssQuery() {
		return "div.s-item__info.clearfix > div.s-item__reviews > a > span > span.clipped";
	}

	@Override
	protected String getAlternativeProductNameCssQuery() {
		return "h3.s-item__title";
	}

	@Override
	protected String getProductPriceCssQuery() {
		return "span.s-item__price";
	}

	@Override
	protected String replaceUrl(Shop shop, String productName) throws MalformedURLException, URISyntaxException {
		return Util.prepareUrlMode1(shop.getSearchPattern(), productName);
	}


}
