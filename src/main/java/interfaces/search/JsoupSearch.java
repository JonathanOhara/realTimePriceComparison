package interfaces.search;

import interfaces.Filter;
import objects.Game;
import objects.Product;
import objects.Shop;
import objects.normalizer.impl.JsoupPageDocument;

import java.util.List;

public abstract class JsoupSearch extends UnifiedSearch {

    private JsoupPageDocument pageDocument;

    @Override
    public List<Product> search(Shop shop, Game game, Filter filter) {
        pageDocument = new JsoupPageDocument();
        return super.search(pageDocument, shop, game, filter);
    }
}
