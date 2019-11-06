package com.korov.springboot.util;

import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.DefaultElement;

import java.util.Iterator;

@Slf4j
public class XmlUtil {
    public static Element getElements(String url) {
        SAXReader saxReader = new SAXReader();
        Document document = null;
        try {
            document = saxReader.read(url);
        } catch (DocumentException e) {
            log.error("read xml fail");
            return new DefaultElement("Empty Element");
        }
        return document.getRootElement();
    }

    public static Element getElement(Element element, String elementName) {
        Iterator<Element> iterator = element.elementIterator();
        Element resultElement = null;
        while (iterator.hasNext()) {
            resultElement = iterator.next();
            if (StringUtil.equals(elementName, resultElement.getName())) {
                return resultElement;
            }
        }
        return new DefaultElement("Empty Element");
    }

    public static Element getElement(String url, String elementName) {
        Element rootElement = getElements(url);
        Iterator<Element> iterator = rootElement.elementIterator();
        Element resultElement = null;
        while (iterator.hasNext()) {
            resultElement = iterator.next();
            if (StringUtil.equals(elementName, resultElement.getName())) {
                return resultElement;
            }
        }
        return new DefaultElement("Empty Element");
    }
}
