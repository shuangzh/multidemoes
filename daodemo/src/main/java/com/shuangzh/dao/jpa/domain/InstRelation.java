package com.shuangzh.dao.jpa.domain;

import javax.persistence.*;

/**
 * Created by admin on 2017/4/4.
 */

@Entity
@Table(name = "Inst_Relation")
public class InstRelation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "seq_cid")
    private Integer instRelaId;

    @ManyToOne
    @JoinColumn(name = "rela_id")
    private CIRelation ciRelation;

    @ManyToOne
    @JoinColumn(name = "startInst_id")
    private Inst  startInst;

    @ManyToOne
    @JoinColumn(name = "endInst_id")
    private Inst endInst;

    //****************************************************************************


    public Integer getInstRelaId() {
        return instRelaId;
    }

    public void setInstRelaId(Integer instRelaId) {
        this.instRelaId = instRelaId;
    }

    public CIRelation getCiRelation() {
        return ciRelation;
    }

    public void setCiRelation(CIRelation ciRelation) {
        this.ciRelation = ciRelation;
    }

    public Inst getStartInst() {
        return startInst;
    }

    public void setStartInst(Inst startInst) {
        this.startInst = startInst;
    }

    public Inst getEndInst() {
        return endInst;
    }

    public void setEndInst(Inst endInst) {
        this.endInst = endInst;
    }
}
