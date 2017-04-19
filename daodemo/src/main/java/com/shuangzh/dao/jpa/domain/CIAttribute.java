package com.shuangzh.dao.jpa.domain;

import javax.persistence.*;

/**
 * Created by admin on 2017/4/3.
 */
@Entity
@Table(name = "CI_ATTR_Definition")
public class CIAttribute {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "seq_cid")
    @Column(name = "attr_id")
    private Integer attrId;

    private String name;

    private String description;

    private boolean required;

    private ValueType valueType;

    @ManyToOne
    @JoinColumn(name = "ci_id")
    private CIDefinition ciDefinition;

    public enum ValueType{
        INT,
        STRING,
        NUMBER
    }


    //****************************************************************

    public Integer getAttrId() {
        return attrId;
    }

    public void setAttrId(Integer attrId) {
        this.attrId = attrId;
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

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public ValueType getValueType() {
        return valueType;
    }

    public void setValueType(ValueType valueType) {
        this.valueType = valueType;
    }

    public CIDefinition getCiDefinition() {
        return ciDefinition;
    }

    public void setCiDefinition(CIDefinition ciDefinition) {
        this.ciDefinition = ciDefinition;
    }
}
