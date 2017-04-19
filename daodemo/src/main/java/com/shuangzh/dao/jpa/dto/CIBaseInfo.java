package com.shuangzh.dao.jpa.dto;

import com.shuangzh.dao.jpa.domain.CIDefinition;

/**
 * Created by admin on 2017/4/6.
 */
public class CIBaseInfo {

    private int id;
    private String name;
    private String description;
    private int attrNum;
    private int relaNum;
    private String parent;

    public CIBaseInfo() {
    }

    public CIBaseInfo(CIDefinition ciDefinition) {
        this.id = ciDefinition.getId();
        this.name = ciDefinition.getName();
        this.description = ciDefinition.getDescription();
        if (ciDefinition.getAttributes() != null) {
            this.attrNum = ciDefinition.getAttributes().size();
        } else
            this.attrNum = 0;

        if (ciDefinition.getAsStartRelation() != null)
            this.relaNum = ciDefinition.getAsStartRelation().size();
        else
            this.relaNum = 0;
        if (ciDefinition.getParent() != null) {
            this.parent = ciDefinition.getParent().getName();
        }
    }


    // ******************  setter & getter *******************
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAttrNum() {
        return attrNum;
    }

    public void setAttrNum(int attrNum) {
        this.attrNum = attrNum;
    }

    public int getRelaNum() {
        return relaNum;
    }

    public void setRelaNum(int relaNum) {
        this.relaNum = relaNum;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }
}
