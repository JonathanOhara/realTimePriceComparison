package interfaces.search;

import interfaces.Filter;
import objects.Game;
import objects.Product;
import objects.Shop;
import objects.normalizer.impl.SeleniumPageDocument;

import java.util.List;

public abstract class SeleniumSearch extends UnifiedSearch {

    protected SeleniumPageDocument pageDocument;

    @Override
    public List<Product> search(Shop shop, Game game, Filter filter) {
        pageDocument = new SeleniumPageDocument();
        return super.search(pageDocument, shop, game, filter);
    }

}
