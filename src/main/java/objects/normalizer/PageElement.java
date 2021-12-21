package objects.normalizer;

import java.util.List;

public interface PageElement {
    List<PageElement> getByCssSelector(String selector);

    String getText();

    String getAttribute(String attributeName);
}
