package interfaces.search;

import interfaces.Filter;
import objects.Game;
import objects.Product;
import objects.ProductType;
import objects.Shop;

import java.util.List;

public interface Search {
	String getName();
	String getMainUrl();
	String getSearchPattern(ProductType productType);

	List<Product> search(Shop shop, Game game, Filter filter );
}
