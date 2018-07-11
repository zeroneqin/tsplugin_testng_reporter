package com.qinjun.autotest.tsplugin.util;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


public class XmlUtil {
    public static Element getRoot(String path) {
        Element  element = null;
        try {
            SAXReader  saxReader = new SAXReader();
            Document document = saxReader.read(XmlUtil.class.getResourceAsStream(path));
            element = document.getRootElement();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return element;
    }

}
