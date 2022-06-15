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
	protected String priceSanatize(String price) {
		price = price.replace(".",",");
		return super.priceSanatize(price);
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
