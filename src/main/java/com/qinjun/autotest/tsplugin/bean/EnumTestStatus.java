package com.qinjun.autotest.tsplugin.bean;

public enum EnumTestStatus {
    SUCCESS("PASS"),FAILURE("FAIL"),SKIP("SKIP"),SUCCESS_PERCENTAGE_FAILURE("SUCCESS_PERCENTAGE_FAILURE");

    private String statusName;

    EnumTestStatus(String statusName) {
        this.statusName = statusName;
    }

    public String getName() {
        return this.statusName;
    }

}
