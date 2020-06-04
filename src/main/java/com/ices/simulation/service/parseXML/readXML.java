package com.ices.simulation.service.parseXML;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.File;

public class readXML {

    public static String read(String filepath) throws DocumentException {
        File file = new File(filepath);
        SAXReader reader=new SAXReader();

        Document read = reader.read(file);
        String xmlstr = read.asXML();

        return xmlstr;
    }
}
