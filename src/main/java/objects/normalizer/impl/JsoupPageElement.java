package objects.normalizer.impl;

import objects.normalizer.PageElement;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.stream.Collectors;

public class JsoupPageElement implements PageElement {
    private Element webElement;

    public JsoupPageElement(Element webElement) {
        this.webElement = webElement;
    }

    @Override
    public List<PageElement> getByCssSelector(String selector) {
        Elements elements = webElement.select(selector);
        return elements.stream().map(el -> new JsoupPageElement(el)).collect(Collectors.toList());
    }

    @Override
    public String getText() {
        return webElement.text();
    }

    @Override
    public String getAttribute(String attributeName) {
        return webElement.attr(attributeName);
    }
}
