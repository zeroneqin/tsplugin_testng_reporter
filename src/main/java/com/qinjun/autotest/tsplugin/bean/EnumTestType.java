package com.qinjun.autotest.tsplugin.bean;

public enum EnumTestType {
    UT("UT"),BVT("BVT"),FVT("FVT");

    private String typeName;

    EnumTestType(String typeName) {this.typeName=typeName;}

    public String getName() {return this.typeName;}
}
