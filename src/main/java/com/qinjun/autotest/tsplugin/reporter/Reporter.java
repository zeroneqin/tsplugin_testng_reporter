package com.qinjun.autotest.tsplugin.reporter;

import com.qinjun.autotest.tsplugin.bean.result.ResultSuite;
import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.reporters.SuiteHTMLReporter;
import org.testng.reporters.XMLReporter;
import org.testng.xml.XmlSuite;

import javax.sql.rowset.spi.XmlReader;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;
import java.util.ArrayList;
import java.util.List;

public class Reporter implements IReporter {
    private List<ResultSuite> resultSuiteList = new ArrayList<ResultSuite>();

    public void generateReport(List<XmlSuite> xmlSuiteList, List<ISuite> suiteList, String outputDir) {
        try {
            System.out.println("Generate xml report start at:"+outputDir);
            XMLReporter xmlReporter = new XMLReporter();
            xmlReporter.generateReport(xmlSuiteList,suiteList,outputDir);
            System.out.println("Generate xml report end");

            }
            catch (Exception e) {
            e.printStackTrace();
        }

        for (ISuite suite : suiteList) {
            resultSuiteList.add(new ResultSuite(suite));
        }
        /*
        HtmlReporter htmlReporter = new HtmlReporter();
        try {
            htmlReporter.setResultSuiteList(resultSuiteList);
            htmlReporter.generateReport(xmlSuiteList,suiteList,outputDir);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        */
        EmailReporter emailReporter = new EmailReporter();
        try {
            emailReporter.setResultSuiteList(resultSuiteList);
            emailReporter.generateReport(xmlSuiteList,suiteList,outputDir);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }


}
