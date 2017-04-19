package com.shuangzh.dao.jpa.domain;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by admin on 2017/4/4.
 */
@Entity
public class Inst {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "seq_cid")
    @Column(name = "inst_id")
    private Integer instId;

    private String  name;

    private String description;


    @ManyToOne
    @JoinColumn(name = "ci_id")
    private CIDefinition ciDefinition;

    @OneToMany(mappedBy = "inst")
    private Set<InstAttribute> instAttributes;

    @OneToMany(mappedBy = "startInst")
    private Set<InstRelation> instRelationsAsStart;

    @OneToMany(mappedBy = "endInst")
    private Set<InstRelation> instRelationsAsEnd;



    //******************************************************


    public Integer getInstId() {
        return instId;
    }

    public void setInstId(Integer instId) {
        this.instId = instId;
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

    public CIDefinition getCiDefinition() {
        return ciDefinition;
    }

    public void setCiDefinition(CIDefinition ciDefinition) {
        this.ciDefinition = ciDefinition;
    }

    public Set<InstAttribute> getInstAttributes() {
        return instAttributes;
    }

    public void setInstAttributes(Set<InstAttribute> instAttributes) {
        this.instAttributes = instAttributes;
    }

    public Set<InstRelation> getInstRelationsAsStart() {
        return instRelationsAsStart;
    }

    public void setInstRelationsAsStart(Set<InstRelation> instRelationsAsStart) {
        this.instRelationsAsStart = instRelationsAsStart;
    }

    public Set<InstRelation> getInstRelationsAsEnd() {
        return instRelationsAsEnd;
    }

    public void setInstRelationsAsEnd(Set<InstRelation> instRelationsAsEnd) {
        this.instRelationsAsEnd = instRelationsAsEnd;
    }
}
