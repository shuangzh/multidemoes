package com.shuangzh.dao.jpa.dto;

import com.shuangzh.dao.jpa.domain.CIAttribute;

/**
 * Created by admin on 2017/4/7.
 */
public class AttributeBaseInfo {


    private int attrId;
    private String name;
    private String description;
    private boolean required;
    private String valueType;
    private String belongTo;


    public AttributeBaseInfo(CIAttribute ciAttribute)
    {
        attrId= ciAttribute.getAttrId();
        name = ciAttribute.getName();
        description = ciAttribute.getDescription();
        valueType = ciAttribute.getValueType().toString();
        belongTo = ciAttribute.getCiDefinition().getName();
    }


    //********************************************


}
