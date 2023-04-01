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
		String url = "https://api.casasbahia.com.br/merchandising/oferta/v1/Preco/Produto/PrecoVenda?idsProduto=<SKU>&composicao=DescontoFormaPagamento&apiKey=d081fef8c2c44645bb082712ed32a047&utm_campaign=197989&utm_medium=afiliados&utm_source=zanox&utm_term=S938345C1H3AGR1Q4UY8H";

		JsoupPageElement element = (JsoupPageElement) productContainer;

		String href = element.getByCssSelector("a").get(0).getAttribute("href");

		String sku = href.substring(href.indexOf("Sku=") + 4);

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
		return "div.dPlWZd > div.esRPia";
	}

	@Override
	protected String getProductNameCssQuery() {
		return "h3.KTGxe";
	}

	@Override
	protected String getProductPriceCssQuery() {
		return ".hTVULn";
	}

	@Override
	protected String replaceUrl(Shop shop, String productName) throws MalformedURLException, URISyntaxException {
		return Util.prepareUrlMode3(shop.getSearchPattern(), productName);
	}

}
