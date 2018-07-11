package com.qinjun.autotest.tsplugin.bean.result;

import org.apache.commons.lang3.StringUtils;
import org.testng.ITestContext;
import org.testng.ITestResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ResultTest extends Result{
    private static long counter=0L;
    private long id;
    private String name;
    private int failedCount;
    private int skippedCount;
    private int passedCount;
    private List<Result> resultClassFailedList= new ArrayList<Result>();
    private List<Result> resultClassSkipList = new ArrayList<Result>();
    private List<Result> resultClassPassList = new ArrayList<Result>();

    public ResultTest(ITestContext testContext) {
        getResultClass(resultClassFailedList,testContext.getFailedTests().getAllResults());
        getResultClass(resultClassPassList,testContext.getPassedTests().getAllResults());
        getResultClass(resultClassSkipList,testContext.getSkippedTests().getAllResults());

        failedCount = testContext.getFailedTests().getAllResults().size();
        passedCount = testContext.getPassedTests().getAllResults().size();
        skippedCount = testContext.getSkippedTests().getAllResults().size();

        startTime = testContext.getStartDate();
        finishTime = testContext.getEndDate();
        duration = finishTime.getTime() - startTime.getTime();
        id=startTime.getTime()+counter;
        counter+=1L;
        name= StringUtils.isEmpty(testContext.getName()) ? "Test-"+id:testContext.getName();



    }


    private void getResultClass(List<Result> classResultList, Set<ITestResult> results) {
        for (ITestResult result: results) {
            String name = result.getTestClass().getName();
            ResultClass resultClass = findClass(name,classResultList);
            if (resultClass!=null) {
                ResultMethod resultMethod = new ResultMethod(result,resultClass);
                resultClass.getResultMethodList().add(resultMethod);
            }
            else {
                classResultList.add(new ResultClass(name,result));
            }
        }
    }

    private ResultClass findClass(String name, List<Result> classResultList) {
        ResultClass result = null;
        for (Result classResult : classResultList) {
            ResultClass r = (ResultClass) classResult;
            if (r.getName().equals(name)) {
                result = r;
                break;
            }
        }
        return result;
    }

    public static long getCounter() {
        return counter;
    }

    public static void setCounter(long counter) {
        ResultTest.counter = counter;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<Result> getResultClassFailedList() {
        return resultClassFailedList;
    }

    public void setResultClassFailedList(List<Result> resultClassFailedList) {
        this.resultClassFailedList = resultClassFailedList;
    }

    public List<Result> getResultClassSkipList() {
        return resultClassSkipList;
    }

    public void setResultClassSkipList(List<Result> resultClassSkipList) {
        this.resultClassSkipList = resultClassSkipList;
    }

    public List<Result> getResultClassPassList() {
        return resultClassPassList;
    }

    public void setResultClassPassList(List<Result> resultClassPassList) {
        this.resultClassPassList = resultClassPassList;
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
        sb.append("\t");
        sb.append("failedCount:");
        sb.append(failedCount);
        sb.append("\t");
        sb.append("skippedCount:");
        sb.append(skippedCount);
        sb.append("\t");
        sb.append("passedCount:");
        sb.append(passedCount);
        sb.append("\n");
        sb.append("failedResult:\n");
        for (Result result:resultClassFailedList) {
            sb.append(result.toString());
        }
        sb.append("skippedResults:\n");
        for (Result result: resultClassSkipList) {
            sb.append(result.toString());
        }
        sb.append("passedResults:\n");
        for (Result result:resultClassPassList) {
            sb.append(result.toString());
        }
        return sb.toString();
    }
}
