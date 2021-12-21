package objects;

import comom.Keys;
import objects.normalizer.PageElement;

import java.util.Optional;


public class Product {
	private Game game;
	private String name;
	private String imageUrl;
	private String url;
	private String value;

	private String shipping;

	private PageElement pageElement;

	public Product() {}

	public Product(Game game, String name, String imageUrl, String url, PageElement pageElement, String value) {
		super();
		this.game = game;
		this.name = name;
		this.imageUrl = imageUrl;
		this.url = url;
		this.value = value;
		this.pageElement = pageElement;
	}


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	public float getFloatValue(){
		float returnValue;

		String value = getValue();

		returnValue = normalizeValue(value);

		return returnValue;
	}

	public float getValueWithShipmentIfItsPresent(){
		float returnValue;

		String value = getValue();

		returnValue = normalizeValue(value);

		returnValue += Optional.ofNullable(getShipping()).map(shipping -> normalizeValue(shipping)).orElse(0.0f);

		return returnValue;
	}

	private float normalizeValue(String value) {
		float returnValue;
		float valueModificator = 1;
		try{

			if( value.indexOf(",") != value.lastIndexOf(",")){
				value.replace(",","");
			}

			if( value.startsWith("$") ){ //Nintendo eShop
				value = value.replace("$", "").trim();
				value = value.replace("*", "").trim();

				valueModificator = Keys.DOLAR_VALUE;
			}

			value = value.replace("por R$", "").trim();
			value = value.replace("R$", "").trim();
			value = value.replace("R ", "").trim();

			if( value.contains(".") ){
				if( value.indexOf(".") == value.length() - 2 || value.indexOf(".") == value.length() - 3 ){

				}else{
					value = value.replace(".", "").trim();
				}
			}

			if( value.contains(",") ){
				if( value.indexOf(",") == value.length() - 2 || value.indexOf(",") == value.length() - 3 ){
					value = value.replace(",", ".").trim();
				}else{
					value = value.replace(",", "").trim();
				}
			}

			returnValue = Float.parseFloat(value) * valueModificator;
		}catch(Exception e){
			returnValue = 999999.99f;
		}
		return returnValue;
	}

/*
	public static void main(String[] args) {
		Product product = new Product();
		
		product.setValue("$19.99");
		System.out.println( product.getFloatValue() );
		
		product.setValue("$19.99*");
		System.out.println( product.getFloatValue() );
		
		product.setValue("por R$ 79,90");
		System.out.println( product.getFloatValue() );
		
		product.setValue("R$ 99,90 (33% de desconto)");
		System.out.println( product.getFloatValue() );
		
		product.setValue("R$ 219,90");
		System.out.println( product.getFloatValue() );
		
		product.setValue("R$ 219.90");
		System.out.println( product.getFloatValue() );
		
		product.setValue("219,9");
		System.out.println( product.getFloatValue() );

		product.setValue("188.9");
		System.out.println( product.getFloatValue() );
		
		product.setValue("1.000,01");
		System.out.println( product.getFloatValue() );
		
		product.setValue("1,000.01");
		System.out.println( product.getFloatValue() );

		product.setValue("R 1,048.88");
		System.out.println( product.getFloatValue() );

		product.setValue("Indisponível");
		System.out.println( product.getFloatValue() );
	}
*/

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}


	public String getShipping() {
		return shipping;
	}

	public void setShipping(String shipping) {
		this.shipping = shipping;
	}

	public PageElement getPageElement() {
		return pageElement;
	}

	public void setPageElement(PageElement pageElement) {
		this.pageElement = pageElement;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	@Override
	public String toString() {
		return "Product [" + (name != null ? "name=" + name + "| " : "")
				+ (imageUrl != null ? "imageUrl=" + imageUrl + "| " : "")
				+ (url != null ? "url=" + url + "| " : "")
				+ (value != null ? "value=" + value : "") + "]";
	}

	
	
}
