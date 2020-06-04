package com.ices.simulation.service;

import com.ices.simulation.service.parseXML.ParseXMLUtils;
import com.ices.simulation.service.parseXML.myParse;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
//将上传的bpmn解析，存入信息，解析成多个状态图
public class XMLDealService {

    @Autowired
    ParseXMLUtils parseXMLUtil;

    private static Document document;

    public void parse(String path) throws DocumentException, IOException {
        parseXMLUtil.parseProcess(path);
        myParse.parse(path);
    }

    public String readXMLToString(File file) throws DocumentException {
        SAXReader reader=new SAXReader();
        document=reader.read(file);
        String xmlStr = document.asXML();
        xmlStr = xmlStr.replaceFirst("<definitions.*>", "<definitions>");
        xmlStr = xmlStr.replaceAll("<bpmn.*:.*", "");
        xmlStr = xmlStr.replaceAll("</bpmndi:.*>", "");
        xmlStr = xmlStr.replaceAll("<omg.*:.*", "");
        document= DocumentHelper.parseText(xmlStr);
        return document.asXML();
    }
}
