package com.qinjun.autotest.tsplugin.reporter;

import com.qinjun.autotest.tsplugin.bean.EnumTestType;
import com.qinjun.autotest.tsplugin.bean.result.*;
import com.qinjun.autotest.tsplugin.util.CommonUtil;
import com.qinjun.autotest.tsplugin.util.HtmlUtil;
import com.qinjun.autotest.tsplugin.util.XmlUtil;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.xml.XmlSuite;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HtmlReporter implements IReporter {
    private List<ResultSuite> resultSuiteList = new ArrayList<ResultSuite>();
    private String subject = System.getProperty("subject");
    private EnumTestType testType = CommonUtil.getType(System.getProperty("type"));
    private String  outDir;

    @Override
    public void generateReport(List<XmlSuite> xmlSuiteList, List<ISuite> suiteList, String outputDir) {
        outDir = outputDir;
        ISuite suite;
        Iterator iterator;
        if (resultSuiteList == null) {
            resultSuiteList = new ArrayList<ResultSuite>();
            for (iterator = suiteList.iterator();iterator.hasNext();) {
                suite = (ISuite) iterator.next();
                resultSuiteList.add(new ResultSuite(suite));
            }
        }

        List<String> suitePaths = new ArrayList<String>();
        for (ResultSuite resultSuite: resultSuiteList) {
            Element element = generateMain(resultSuite);
            String dir = outDir+ File.separator+resultSuite.getId();
            HtmlUtil.saveAsHtml(element,dir,"index.html");
            suitePaths.add(dir);
        }
    }


    private Element generateMain(ResultSuite resultSuite) {
        Element root = XmlUtil.getRoot("/template_html/main.xml");
        Element title = root.element("head").element("title");
        title.setText(CommonUtil.getSubject(subject,testType,resultSuite));

        Element body = root.element("body");
        Element h2 = body.element("header").element("h2");
        h2.setText(CommonUtil.getSubject(subject,testType,resultSuite));

        Element aside = body.element("aside");
        for (Result result : resultSuite.getResultTestList()) {
            Element h3 = DocumentFactory.getInstance().createElement("h3");
            ResultTest resultTest = (ResultTest) result;


            Element test = generateTest(resultTest);
            String fileName = resultTest.getId()+".html";

            String outdir = outDir + File.separator+resultSuite.getId();
            HtmlUtil.saveAsHtml(test,outdir,fileName);

            if (!StringUtils.isEmpty(resultTest.getName())) {
                Element a = DocumentFactory.getInstance().createElement("a");
                a.addAttribute("test-url",resultTest.getId() +".html");
                a.setText(resultTest.getName());
                h3.add(a);
            }
            aside.add(h3);
        }

        if ((resultSuite.getResultTestList()!=null) && resultSuite.getResultTestList().size()!=0) {
            ResultTest resultTest = (ResultTest)resultSuite.getResultTestList().get(0);
            String src = resultTest.getId()+".html";
            Element iframe = body.element("iframe");
            iframe.addAttribute("src",src);
        }
        return root;
    }


    private Element generateTest(ResultTest resultTest) {
        Element root = XmlUtil.getRoot("/template_html/test.xml");

        Element containerDiv = root.element("body").element("section").element("article").element("div");

        List<Element> childList = containerDiv.elements("div");
        if (childList.size()==3) {
            for (Result result : resultTest.getResultClassFailedList()) {
                childList.get(0).add(generateClass((ResultClass) result));
                childList.get(0).addElement("br");
            }
            for (Result result : resultTest.getResultClassSkipList()) {
                childList.get(0).add(generateClass((ResultClass) result));
                childList.get(0).addElement("br");
            }
            for (Result result : resultTest.getResultClassPassList()) {
                childList.get(0).add(generateClass((ResultClass) result));
                childList.get(0).addElement("br");
            }
        }
        return root;
    }


    private Element generateClass(ResultClass resultClass) {
        Element root = XmlUtil.getRoot("/template_html/class.xml");
        Element h3 = root.element("header").element("h3");
        h3.setText(resultClass.getName());
        Element tbody = root.element("table").element("tbody");
        for (Result result : resultClass.getResultMethodList()) {
            tbody.add(generateMethod((ResultMethod) result));
        }
        return root;
     }


     private Element generateMethod(ResultMethod resultMethod) {
        Element root = XmlUtil.getRoot("/template_html/method.xml");

        List<Element> childList = root.elements("td");
        if (childList.size()==5) {
            childList.get(0).setText(resultMethod.getName());
            SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String formatedStartTime = sf.format(resultMethod.getStartTime());
            childList.get(1).setText(formatedStartTime);
            String formatedFinishTime = sf.format(resultMethod.getFinishTime());
            childList.get(2).setText(formatedFinishTime);
            childList.get(3).setText(String.valueOf(resultMethod.getDuration()));
            if(resultMethod.getResultException()!=null && (!StringUtils.isEmpty(resultMethod.getResultException().getMessage()))) {
                childList.get(4).setText(resultMethod.getResultException().getMessage());
            }

        }
         return root;
     }

    public List<ResultSuite> getResultSuiteList() {
        return resultSuiteList;
    }

    public void setResultSuiteList(List<ResultSuite> resultSuiteList) {
        this.resultSuiteList = resultSuiteList;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public EnumTestType getTestType() {
        return testType;
    }

    public void setTestType(EnumTestType testType) {
        this.testType = testType;
    }

    public String getOutDir() {
        return outDir;
    }

    public void setOutDir(String outDir) {
        this.outDir = outDir;
    }
}
