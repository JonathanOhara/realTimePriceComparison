package objects.normalizer.impl;

import comom.Util;
import objects.normalizer.PageDocument;
import objects.normalizer.PageElement;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class JsoupPageDocument implements PageDocument {
    protected Document document;

    @Override
    public void init() {
    }

    @Override
    public void connect(String url) {
        try {
            document = Util.readUrlDocument(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getCurrentUrl() {
        return document.location();
    }

    @Override
    public List<PageElement> getByCssSelector(String selector) {
        Elements elements = document.select(selector);
        return elements.stream().map(el -> new JsoupPageElement(el)).collect(Collectors.toList());
    }

    @Override
    public Object getSourceObject() {
        return document;
    }
}
