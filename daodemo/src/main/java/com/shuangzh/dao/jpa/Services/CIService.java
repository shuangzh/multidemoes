package com.shuangzh.dao.jpa.Services;

import com.shuangzh.dao.controllers.RestResponse;
import com.shuangzh.dao.jpa.Repository.CIDefinitionRepo;
import com.shuangzh.dao.jpa.domain.CIAttribute;
import com.shuangzh.dao.jpa.domain.CIDefinition;
import com.shuangzh.dao.jpa.domain.CIRelation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by admin on 2017/4/4.
 */
@Service
@SuppressWarnings("SpringJavaAutowiringInspection")
@Transactional(value = "transactionManager")
public class CIService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    CIDefinitionRepo cirepo;

    public List<CIDefinition> getAllCIDefinition() {
        List<CIDefinition> list = new ArrayList<CIDefinition>();
        Iterable<CIDefinition> it = cirepo.findAll();
        for (CIDefinition i : it) {
            list.add(i);
        }
        return list;
    }

    public CIDefinition getCIDefinition(int id) {
        return cirepo.findOne(id);
    }

    public CIDefinition createCIDefinition(String name, String description, int parent) {
        CIDefinition ciDefinition = new CIDefinition();
        ciDefinition.setName(name);
        ciDefinition.setDescription(description);
        CIDefinition p = this.getCIDefinition(parent);
        if (p != null)
            ciDefinition.setParent(p);
        cirepo.save(ciDefinition);
        return ciDefinition;
    }

    public boolean deleteCIDefinition(int id) {
        try {
            cirepo.delete(id);
            logger.info("delete CIDefinition {}", id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean modifyCIDefinition(int id, String name, String description) {
        try {
            CIDefinition ciDefinition = this.getCIDefinition(id);
            if (name != null)
                ciDefinition.setName(name);
            if (description != null)
                ciDefinition.setDescription(description);
            cirepo.save(ciDefinition);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public CIDefinition addCI(String name, String desc, CIDefinition parent) {
        CIDefinition ciDefinition = new CIDefinition();
        ciDefinition.setName(name);
        ciDefinition.setDescription(desc);
        ciDefinition.setParent(parent);
        return cirepo.save(ciDefinition);
    }

    public void removeCI(Integer id) {
        cirepo.delete(id);
    }

    public CIDefinition saveCI(CIDefinition ci) {
        return cirepo.save(ci);
    }


    public CIDefinition addAttribute(CIDefinition ci, String attName, String attDesciption, Boolean required, CIAttribute.ValueType valueType) {
        CIAttribute ciAttribute = new CIAttribute();
        ciAttribute.setName(attName);
        ciAttribute.setDescription(attDesciption);
        ciAttribute.setRequired(required);
        ciAttribute.setValueType(valueType);
        ciAttribute.setCiDefinition(ci);
        if (ci.getAttributes() != null) {
            ci.getAttributes().add(ciAttribute);
        } else {
            HashSet<CIAttribute> set = new HashSet<CIAttribute>();
            set.add(ciAttribute);
            ci.setAttributes(set);
        }
        return cirepo.save(ci);
    }

    public CIDefinition removeAttribute(CIDefinition ci, String attName) {
        if (ci.getAttributes() != null) {
            Set<CIAttribute> set = ci.getAttributes();
            CIAttribute target = null;
            for (CIAttribute t : set) {
                if (attName.equals(t.getName())) {
                    target = t;
                    break;
                }
            }
            set.remove(target);
            return cirepo.save(ci);
        }
        return ci;
    }

    public CIDefinition addRelation(CIDefinition stCI, CIDefinition edCI, String relaDesc, CIRelation.Action action, Boolean required) {
        CIRelation ciRelation = new CIRelation();
        ciRelation.setAction(action);
        ciRelation.setDescription(relaDesc);
        ciRelation.setStartpoint(stCI);
        ciRelation.setEndpoint(edCI);
        if (stCI.getAsStartRelation() != null) {
            stCI.getAsStartRelation().add(ciRelation);
        } else {
            HashSet<CIRelation> set = new HashSet<CIRelation>();
            set.add(ciRelation);
            stCI.setAsStartRelation(set);
        }
        return cirepo.save(stCI);
    }


}
