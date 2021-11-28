package interfaces.search.shops;

import comom.Util;
import interfaces.search.JsoupSearch;
import objects.ProductType;
import objects.Shop;
import org.jsoup.nodes.Element;


import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class CogumeloShopSearch extends JsoupSearch {

	@Override
	public String getName() {
		return "Cogumelo Shop";
	}

	@Override
	public String getMainUrl() {
		return "https://www.cogumeloshop.com.br/";
	}

	@Override
	public String getSearchPattern(ProductType productType) {
		return "https://www.cogumeloshop.com.br/loja/busca.php?palavra_busca=<BUSCA>";
	}

	@Override
	protected boolean validProduct(Element productContainer) {
		boolean valid = true;

		if(productContainer.select("div.preco-produto").text().startsWith("Indispon")){
			System.out.println("[ERROR] TAG indisponivel encontrada");
			valid = false;
		}

		return valid && super.validProduct(productContainer);
	}

	@Override
	protected String priceSanatize(String price){
		if(price.contains("por: ")){
			price = price.substring(price.indexOf("por:") + 4);
		}

		if(price.contains("no boleto")){
			price = price.replace( "no boleto", "");
		}

		return super.priceSanatize(price);
	}

	@Override
	protected String getProductListCssQuery() {
		return ".showcase-catalog > ul > li";
	}

	@Override
	protected String getProductNameCssQuery() {
		return "span[itemprop='name']";
	}

	@Override
	protected String getProductPriceCssQuery() {
		return "#PrecoPrincipal1";
	}

	@Override
	protected String replaceUrl(Shop shop, String productName) throws MalformedURLException, URISyntaxException {
		return Util.prepareUrlMode1(shop.getSearchPattern(), productName);
	}
	
}
