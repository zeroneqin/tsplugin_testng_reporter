package com.qinjun.autotest.tsplugin.bean.result;

import com.qinjun.autotest.tsplugin.util.CommonUtil;
import org.apache.commons.lang3.StringUtils;
import org.testng.ISuite;
import org.testng.ISuiteResult;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ResultSuite extends Result {
    private static long counter=0L;
    private  long id;
    private  String suiteName;
    private  int failedCount;
    private  int skippedCount;
    private  int passedCount;

    private final List<Result> resultTestList = new ArrayList<Result>();

    public ResultSuite(ISuite tngSuite) {
        for (ISuiteResult tngSuiteResult : tngSuite.getResults().values()) {
            ResultTest resultTest = new ResultTest(tngSuiteResult.getTestContext());
            resultTestList.add(resultTest);
        }

        Date[] dates = CommonUtil.getStartEndDate(resultTestList);
        startTime = dates[0];
        finishTime = dates[1];

        int pass=0;
        int fail=0;
        int skip=0;
        long dur=0L;


        for (Result result : resultTestList) {
            ResultTest resultTest = (ResultTest) result;
            pass += resultTest.getPassedCount();
            fail += resultTest.getFailedCount();
            skip += resultTest.getSkippedCount();
            dur += resultTest.getDuration();
        }

        passedCount = pass;
        failedCount = fail;
        skippedCount = skip;
        duration = dur;

        id = startTime.getTime() + counter;
        counter +=1L;

        suiteName = StringUtils.isEmpty(tngSuite.getName()) ? "Suite-"+id : tngSuite.getName();
    }


    public static long getCounter() {
        return counter;
    }

    public static void setCounter(long counter) {
        ResultSuite.counter = counter;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSuiteName() {
        return suiteName;
    }

    public void setSuiteName(String suiteName) {
        this.suiteName = suiteName;
    }

    public int getFailedCount() {
        return failedCount;
    }

    public void setFailedCount(int failedCount) {
        this.failedCount = failedCount;
    }

    public int getSkippedCount() {
        return skippedCount;
    }

    public void setSkippedCount(int skippedCount) {
        this.skippedCount = skippedCount;
    }

    public int getPassedCount() {
        return passedCount;
    }

    public void setPassedCount(int passedCount) {
        this.passedCount = passedCount;
    }

    public List<Result> getResultTestList() {
        return resultTestList;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("name:");
        sb.append(suiteName);
        sb.append("\t");
        sb.append("startTime:");
        sb.append(startTime);
        sb.append("\t");
        sb.append("finishTime:");
        sb.append(finishTime);
        sb.append("\t");
        sb.append("duration:");
        sb.append(duration);
        sb.append("\t");
        sb.append("resultTestList:\n");
        for (Result result:resultTestList) {
            sb.append(result.toString());
        }
        return sb.toString();
    }
}
