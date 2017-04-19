package com.shuangzh.dao.jpa.domain;

import javax.persistence.*;

/**
 * Created by admin on 2017/4/4.
 */
@Entity
@Table(name = "Inst_Attribute")
public class InstAttribute {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "seq_cid")
    private  Integer  InsAttId;
    private  String Value;

    @ManyToOne
    @JoinColumn(name = "inst_id")
    private Inst inst;

    @ManyToOne
    @JoinColumn(name = "cia_id")
    private CIAttribute ciAttribute;


    //*************************************************

    public Integer getInsAttId() {
        return InsAttId;
    }

    public void setInsAttId(Integer insAttId) {
        InsAttId = insAttId;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public Inst getInst() {
        return inst;
    }

    public void setInst(Inst inst) {
        this.inst = inst;
    }

    public CIAttribute getCiAttribute() {
        return ciAttribute;
    }

    public void setCiAttribute(CIAttribute ciAttribute) {
        this.ciAttribute = ciAttribute;
    }
}
