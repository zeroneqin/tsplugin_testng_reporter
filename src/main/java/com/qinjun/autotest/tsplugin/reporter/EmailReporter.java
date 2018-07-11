package com.qinjun.autotest.tsplugin.reporter;

import com.qinjun.autotest.tsplugin.bean.EnumTestType;
import com.qinjun.autotest.tsplugin.bean.result.Result;
import com.qinjun.autotest.tsplugin.bean.result.ResultSuite;
import com.qinjun.autotest.tsplugin.bean.result.ResultTest;
import com.qinjun.autotest.tsplugin.util.CommonUtil;
import com.qinjun.autotest.tsplugin.util.EmailUtil;
import com.qinjun.autotest.tsplugin.util.HtmlUtil;
import com.qinjun.autotest.tsplugin.util.XmlUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.reporters.jq.ResultsByClass;
import org.testng.xml.XmlSuite;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class EmailReporter implements IReporter {
    private List<ResultSuite> resultSuiteList;
    private EmailUtil emailUtil = new EmailUtil("smtp.163.com",25,"xxxx","xxxx");
    private EnumTestType testType = CommonUtil.getType(System.getProperty("type"));
    private String receiver = System.getProperty("receiver");
    private String cc = System.getProperty("cc");
    private String subject = System.getProperty("subject");

    @Override
    public void generateReport(List<XmlSuite> tngXmlSuiteList, List<ISuite> tngSuiteList, String outputDir) {
        System.out.println("Start to generate email");
        if (resultSuiteList==null) {
            resultSuiteList = new ArrayList<ResultSuite>();
            for (ISuite suite:tngSuiteList) {
                resultSuiteList.add(new ResultSuite(suite));
            }
        }

        Element root = generateMain();

        String path = HtmlUtil.saveAsHtml(root,outputDir,"email_report.html");
        System.out.println("Generated email at:"+path);
        if (!StringUtils.isEmpty(receiver)) {
            ResultSuite first = resultSuiteList.size()>0 ? resultSuiteList.get(0):null;
            emailUtil.setSubject(CommonUtil.getSubject(subject,testType,first));

            emailUtil.setReceiver(receiver);

            if (!StringUtils.isEmpty(cc)) {
                emailUtil.setCc(cc);
            }

            try {
                emailUtil.setBody(FileUtils.readFileToString(new File(path)));
                emailUtil.send();
                System.out.println("Generate and send email end");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("Warning: receiver is null");
        }

    }


    public Element generateMain() {
        Element root = XmlUtil.getRoot("/template_email/main.xml");
        if (root !=null) {
            Element body = root.element("body");
            for (int i=0;i<resultSuiteList.size();i++) {
                ResultSuite resultSuite = resultSuiteList.get(i);
                body.add(generateSuite(resultSuite,i));
            }
        }
        return root;
    }


    public Element generateSuite(ResultSuite resultSuite,int suuiteIndex) {
        Element root = XmlUtil.getRoot("/template_email/suite.xml");
        if (root !=null) {
            Element h1 = root.element("h1");
            h1.setText(resultSuite.getSuiteName());

            Element table = root.element("table");

            Element tbody = table.element("tbody");
            for (Result testResult : resultSuite.getResultTestList()) {
                tbody.add(generateRow((ResultTest) testResult));
            }


            Element tfoot = table.element("tfoot");
            Element tr = tfoot.element("tr");

            List<Element> childList = tr.elements("td");
            if (childList.size()==4) {
                childList.get(0).setText(String.valueOf(resultSuite.getPassedCount()));
                childList.get(1).setText(String.valueOf(resultSuite.getSkippedCount()));
                childList.get(2).setText(String.valueOf(resultSuite.getFailedCount()));
                childList.get(3).setText(String.valueOf(resultSuite.getDuration()));
            }

            Element p = root.element("p");
            Element a = p.element("a");

        }
        return root;
    }

    public Element generateRow(ResultTest resultTest) {
        Element root = XmlUtil.getRoot("/template_email/test.xml");
        if (root!=null) {
            Element th = root.element("th");
            th.setText(resultTest.getName());

            List<Element> childList = root.elements("td");
            if (childList.size()==4) {
                childList.get(0).setText(String.valueOf(resultTest.getPassedCount()));
                childList.get(1).setText(String.valueOf(resultTest.getSkippedCount()));
                childList.get(2).setText(String.valueOf(resultTest.getFailedCount()));
                childList.get(3).setText(String.valueOf(resultTest.getDuration()));
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

    public EmailUtil getEmailUtil() {
        return emailUtil;
    }

    public void setEmailUtil(EmailUtil emailUtil) {
        this.emailUtil = emailUtil;
    }

    public EnumTestType getTestType() {
        return testType;
    }

    public void setTestType(EnumTestType testType) {
        this.testType = testType;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
