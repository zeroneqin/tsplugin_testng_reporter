package com.qinjun.autotest.tsplugin.bean.result;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.testng.internal.Utils;

public class ResultException {
    String className;
    String message;
    String procedure;
    String fullStacktrace;

    public ResultException(Throwable throwable) {
        className = throwable.getClass().getName();
        if (isFormatMessage(throwable.getMessage())) {
            JSONObject jsonObject = JSON.parseObject(throwable.getMessage());
            message = jsonObject.getString("FAILEDTESTS");
            procedure = jsonObject.getString("REQRESPSTEPS");
        }
        else {
            message = throwable.getMessage();
            procedure = "";
        }
        String[] stackTraces = Utils.stackTrace(throwable,false);
        fullStacktrace = stackTraces[1];
    }

    public boolean isFormatMessage(String msg) {
        boolean result = false;
        try {
            JSONObject jsonObject = JSON.parseObject(msg);
            result= (jsonObject.containsKey("FAILEDTESTS")) && (jsonObject.containsKey("REQRESPSTEPS"));
        }
        catch (Exception e) {
            System.err.println("Get exception:"+e);
        }
        return result;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getProcedure() {
        return procedure;
    }

    public void setProcedure(String procedure) {
        this.procedure = procedure;
    }

    public String getFullStacktrace() {
        return fullStacktrace;
    }

    public void setFullStacktrace(String fullStacktrace) {
        this.fullStacktrace = fullStacktrace;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("className:");
        sb.append(className);
        sb.append("\t");
        sb.append("message:");
        sb.append(message);
        sb.append("\t");
        sb.append("fullStacktrace:");
        sb.append(fullStacktrace);
        sb.append("\n");
        return sb.toString();
    }
}
