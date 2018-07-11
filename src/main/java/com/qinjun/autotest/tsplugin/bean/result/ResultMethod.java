package com.qinjun.autotest.tsplugin.bean.result;

import com.qinjun.autotest.tsplugin.bean.EnumTestStatus;
import com.qinjun.autotest.tsplugin.util.CommonUtil;
import org.testng.ITestResult;

import java.util.Date;

public class ResultMethod extends Result{
    private  String name;
    private  boolean isTest;
    private boolean isConfig;
    private EnumTestStatus status;
    private ResultException resultException;
    private String description;
    private ResultClass parentResult;

    public ResultMethod(ITestResult result,ResultClass resultClass) {
        parentResult = resultClass;
        startTime = new Date(result.getStartMillis());
        finishTime = new Date(result.getEndMillis());
        duration = finishTime.getTime() - startTime.getTime();
        isConfig = (!result.getMethod().isTest());
        isTest=result.getMethod().isTest();
        name=result.getMethod().getMethodName();
        description = result.getMethod().getDescription();
        status = CommonUtil.getStatus(result.getStatus());
        if (result.getThrowable() !=null) {
            resultException = new ResultException(result.getThrowable());
        }
        else {
            resultException = null;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isTest() {
        return isTest;
    }

    public void setTest(boolean test) {
        isTest = test;
    }

    public boolean isConfig() {
        return isConfig;
    }

    public void setConfig(boolean config) {
        isConfig = config;
    }

    public EnumTestStatus getStatus() {
        return status;
    }

    public void setStatus(EnumTestStatus status) {
        this.status = status;
    }

    public ResultException getResultException() {
        return resultException;
    }

    public void setResultException(ResultException resultException) {
        this.resultException = resultException;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ResultClass getParentResult() {
        return parentResult;
    }

    public void setParentResult(ResultClass parentResult) {
        this.parentResult = parentResult;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("name:");
        sb.append(name);
        sb.append("\t");
        sb.append("isTest:");
        sb.append(isTest);
        sb.append("\t");
        sb.append("status:");
        sb.append(status);
        sb.append("\t");
        sb.append("description:");
        sb.append(description);
        sb.append("\t");
        sb.append("resultException:");
        sb.append(resultException);
        sb.append("\t");
        sb.append("startTime:");
        sb.append(startTime);
        sb.append("\t");
        sb.append("finishTime:");
        sb.append(finishTime);
        sb.append("\t");
        sb.append("duration:");
        sb.append(duration);
        sb.append("\n");
        return sb.toString();
    }
}
