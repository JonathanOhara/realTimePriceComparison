package objects.normalizer;

import java.util.List;

public interface PageDocument {

    void init();

    void connect(String url);

    String getCurrentUrl();

    List<PageElement> getByCssSelector(String cssSelector);

    Object getSourceObject();
}
