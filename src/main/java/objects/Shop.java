package objects;

import interfaces.Filter;
import interfaces.search.Search;

import java.util.List;



public class Shop {
	
	private String nome;
	private String mainUrl;
	private String searchPattern;
	private String searchUrl;
	private Search searcher;
	private boolean enabled;

	private List<Product> products;


	public Shop(String nome, String mainUrl, String searchPattern, Search searcher) {
		super();
		this.nome = nome;
		this.mainUrl = mainUrl;
		this.searchPattern = searchPattern;
		this.searcher = searcher;
	}

	public List<Product> searchProduct(Game game, Filter filter){
		System.out.println("Loja: "+nome);

		long time = System.currentTimeMillis();
		products = searcher.search(this, game, filter);
		System.out.println("\tTempo Para buscar: "+(System.currentTimeMillis() - time));


		if( products != null ){
			/*
			Collections.sort(products, new Comparator<Product>() {
				@Override
				public int compare(Product o1, Product o2) {
					float valor1, valor2;

					valor1 = valor2 = 0;
					valor1 = o1.getFloatValue();
					valor2 = o2.getFloatValue();

					if( "Nintendo eShop".equals(valor1) ){

					}


					return Float.compare(valor1, valor2);
				}
			});
			*/

		}
		return products;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getMainUrl() {
		return mainUrl;
	}
	public void setMainUrl(String mainUrl) {
		this.mainUrl = mainUrl;
	}
	public String getSearchPattern() {
		return searchPattern;
	}
	public void setSearchPattern(String searchPattern) {
		this.searchPattern = searchPattern;
	}
	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public String getSearchUrl() {
		return searchUrl;
	}

	public void setSearchUrl(String searchUrl) {
		this.searchUrl = searchUrl;
	}

	public boolean isEnabled() {
		return searcher.isShopEnabled();
	}
}
