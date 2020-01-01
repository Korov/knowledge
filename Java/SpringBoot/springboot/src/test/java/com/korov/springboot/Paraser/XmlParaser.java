package com.korov.springboot.Paraser;

import com.korov.springboot.util.XmlUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

public class XmlParaser {
    @Test
    public void test() {
        try {
            addTest();
            searchTest();
            System.out.println("-----------------------------------------------------------");
            updateTest();
            searchTest();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void searchTest() {
        Element rootElement = XmlUtil.getElements("src/test/resources/paraser/xmlparaser.xml");
        System.out.println("root element name:" + rootElement.getName());
        Iterator<Element> iterator = rootElement.elementIterator();
        while (iterator.hasNext()) {
            Element fistChild = iterator.next();
            System.out.println(fistChild.getName());
            System.out.println(fistChild.attribute("name").getValue());
            Iterator<Element> iterator1 = fistChild.elementIterator();
            while (iterator1.hasNext()) {
                Element element = iterator1.next();
                System.out.println("\t" + element.attributeValue("name"));
            }
        }

    }

    @Test
    public void addTest() throws DocumentException, IOException {
        Element rootElement = XmlUtil.getElements("src/test/resources/paraser/xmlparaser.xml");

        // 添加子节点
        Element brand = rootElement.addElement("brand");
        //给当前节点添加属性
        brand.addAttribute("name", "魅族");
        Element type = brand.addElement("type");
        type.addAttribute("name", "s16");
        OutputStream os = new FileOutputStream(new File("src/test/resources/paraser/xmlparaser.xml"));
        XMLWriter xmlWriter = new XMLWriter(os);
        xmlWriter.write(rootElement);
        xmlWriter.flush();
    }

    @Test
    public void updateTest() {
        //创建DOM4J解析器对象
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read("src/test/resources/paraser/xmlparaser.xml");
            //获取根节点
            Element rootElement = XmlUtil.getElements("src/test/resources/paraser/xmlparaser.xml");
            Iterator it = rootElement.elementIterator();
            while (it.hasNext()) {
                Element element = (Element) it.next();
                System.out.println(element.attributeValue("name"));
                if (element.attributeValue("name").equals("魅族")) {
                    Iterator iterator = element.elementIterator();
                    while (iterator.hasNext()) {
                        Element type = (Element) iterator.next();
                        if (type.attributeValue("name").equals("s16")) {
                            type.addAttribute("name", "16 pro");
                        }
                    }
                }
            }
            XMLWriter xmlWriter = new XMLWriter(new FileOutputStream(new File("src/test/resources/paraser/xmlparaser.xml")));
            xmlWriter.write(document);
            xmlWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
