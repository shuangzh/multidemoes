package com.shuangzh.dao.jpa.domain;

import org.apache.ibatis.annotations.Many;

import javax.persistence.*;

/**
 * Created by admin on 2017/4/3.
 */
@Entity
@Table(name = "CI_Relation")
public class CIRelation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "seq_cid")
    private Integer rela_id;

    private boolean required;

    private Action action;

    private  String description;

    @ManyToOne
    @JoinColumn(name = "sp_id")
    private CIDefinition startpoint;

    @ManyToOne
    @JoinColumn(name = "ep_id")
    private CIDefinition endpoint;

    public enum Action{
        INSTALLED,
        CLASSIFIED,
        BELONGTO
    }


    //****************************************************************

    public Integer getRela_id() {
        return rela_id;
    }

    public void setRela_id(Integer rela_id) {
        this.rela_id = rela_id;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CIDefinition getStartpoint() {
        return startpoint;
    }

    public void setStartpoint(CIDefinition startpoint) {
        this.startpoint = startpoint;
    }

    public CIDefinition getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(CIDefinition endpoint) {
        this.endpoint = endpoint;
    }
}
