package com.qinjun.autotest.tsplugin.util;


import com.qinjun.autotest.tsplugin.bean.EnumTestStatus;
import com.qinjun.autotest.tsplugin.bean.EnumTestType;
import com.qinjun.autotest.tsplugin.bean.result.Result;
import com.qinjun.autotest.tsplugin.bean.result.ResultSuite;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CommonUtil {
    public static EnumTestStatus getStatus(int testStatus) {
        switch(testStatus) {
            case 1:
                return EnumTestStatus.SUCCESS;
            case 2:
                return EnumTestStatus.FAILURE;
            case 3:
                return EnumTestStatus.SKIP;
            case 4:
                return EnumTestStatus.SUCCESS_PERCENTAGE_FAILURE;
        }
        return null;
    }


    public static EnumTestType getType(String name) {
        if (name!=null) {
            if (EnumTestType.UT.getName().equals(name.toUpperCase())) {
                return EnumTestType.UT;
            }
            else if (EnumTestType.BVT.getName().equals(name.toUpperCase())) {
                return EnumTestType.BVT;
            }
            else if (EnumTestType.FVT.getName().equals(name.toUpperCase())) {
                return EnumTestType.FVT;
            }
        }
        return EnumTestType.BVT;
    }

    public static String getSubject(String subject, EnumTestType testType, ResultSuite resultSuite) {
        StringBuilder sb = new StringBuilder();
        if (!StringUtils.isEmpty(subject)) {
            sb.append(subject);
            sb.append(" ");
        }
        else if ((resultSuite !=null) && StringUtils.isEmpty(resultSuite.getSuiteName()))
         {
            sb.append(resultSuite.getSuiteName());
            sb.append(" ");
        }
        sb.append("Test Report For");
        sb.append(testType);
        sb.append("--");
        SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String formatedDate = sf.format(new Date());
        sb.append(formatedDate);
        return sb.toString();
    }

    public static Date[] getStartEndDate(List<Result> resultList) {
        Date[] dates = {new Date(),new Date()};
        Date minStartDate = dates[0];
        Date maxFinishDate = dates[1];

        for (Result result: resultList) {
            Date startDate = result.getStartTime();
            Date finshDate = result.getFinishTime();
            if (minStartDate.after(startDate)) {
                minStartDate = startDate;
            }

            if (maxFinishDate==null || maxFinishDate.before(finshDate)) {
                maxFinishDate = finshDate !=null ? finshDate : startDate;
            }
        }
        return dates;
    }

}
