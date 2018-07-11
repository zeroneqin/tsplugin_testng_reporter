package com.qinjun.autotest.tsplugin.bean.result;

import com.qinjun.autotest.tsplugin.util.CommonUtil;
import org.testng.ITestResult;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class ResultClass extends Result {
    private List<Result> resultMethodList = new ArrayList<Result>();
    String name;

    public ResultClass(String name,Set<ITestResult> testResultSet) {
        this.name= name;
        for (ITestResult result:testResultSet) {
            if (name.equals(result.getTestClass().getName())) {
                resultMethodList.add(new ResultMethod(result,this));
            }
        }

        Date[] dates = CommonUtil.getStartEndDate(resultMethodList);
        startTime = dates[0];
        finishTime = dates[1];
        duration = finishTime.getTime()-startTime.getTime();
    }

    public ResultClass(String name, ITestResult testResult) {
        this.name=name;
        resultMethodList.add(new ResultMethod(testResult,this));
        Date[] dates = CommonUtil.getStartEndDate(resultMethodList);
        startTime = dates[0];
        finishTime = dates[1];
        duration = finishTime.getTime()-startTime.getTime();

    }

    public List<Result> getResultMethodList() {
        return resultMethodList;
    }

    public void setResultMethodList(List<Result> resultMethodList) {
        this.resultMethodList = resultMethodList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("name:");
        sb.append(name);
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
        sb.append("methods:\n");
        for (Result result :resultMethodList) {
            sb.append(result.toString());
        }
        return sb.toString();
    }
}
