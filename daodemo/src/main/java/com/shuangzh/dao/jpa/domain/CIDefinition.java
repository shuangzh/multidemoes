package com.shuangzh.dao.jpa.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Created by admin on 2017/4/2.
 */
@Entity
@Table(name = "CI_Definition")
public class CIDefinition implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "seq_cid")
    private Integer id;

    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private CIDefinition parent;

    @OneToMany(mappedBy = "parent",fetch = FetchType.EAGER)
    private Set<CIDefinition> children;

    @OneToMany(mappedBy = "ciDefinition", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<CIAttribute> attributes;

    @OneToMany(mappedBy = "startpoint",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<CIRelation>  asStartRelation;

    @OneToMany(targetEntity = CIRelation.class,mappedBy = "endpoint",fetch = FetchType.EAGER)
    private Set<CIRelation> asEndRelation;

    //****************************************************************

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public CIDefinition getParent() {
        return parent;
    }

    public void setParent(CIDefinition parent) {
        this.parent = parent;
    }

    public Set<CIDefinition> getChildren() {
        return children;
    }

    public void setChildren(Set<CIDefinition> children) {
        this.children = children;
    }

    public Set<CIAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<CIAttribute> attributes) {
        this.attributes = attributes;
    }

    public Set<CIRelation> getAsStartRelation() {
        return asStartRelation;
    }

    public void setAsStartRelation(Set<CIRelation> asStartRelation) {
        this.asStartRelation = asStartRelation;
    }

    public Set<CIRelation> getAsEndRelation() {
        return asEndRelation;
    }

    public void setAsEndRelation(Set<CIRelation> asEndRelation) {
        this.asEndRelation = asEndRelation;
    }
}
