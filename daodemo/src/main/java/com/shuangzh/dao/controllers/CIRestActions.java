package com.shuangzh.dao.controllers;

import com.shuangzh.dao.jpa.Services.CIService;
import com.shuangzh.dao.jpa.domain.CIAttribute;
import com.shuangzh.dao.jpa.domain.CIDefinition;
import com.shuangzh.dao.jpa.dto.AttributeBaseInfo;
import com.shuangzh.dao.jpa.dto.CIBaseInfo;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by admin on 2017/4/6.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@RestController
public class CIRestActions {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    CIService ciService;

    @RequestMapping("/rest/ci/cies")
    public List<CIBaseInfo> getAllCI() {
        List<CIDefinition> list = ciService.getAllCIDefinition();
        List<CIBaseInfo> lbase = new ArrayList<CIBaseInfo>();
        for (CIDefinition i : list) {
            lbase.add(new CIBaseInfo(i));
        }
        return lbase;
    }

    @RequestMapping("/rest/ci/createci")
    public RestResponse newCI(HttpServletRequest request) {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String pid = request.getParameter("parent");
        int pp = -1;
        if (pid != null) ;
        pp = Integer.parseInt(pid);
        try {
            CIDefinition ciDefinition = ciService.createCIDefinition(name, description, pp);
            logger.info("create CIDefiniton {}, name:{}, description:{}", ciDefinition.getId(), ciDefinition.getName(), ciDefinition.getDescription());
            RestResponse restResponse = new RestResponse();
            return restResponse;
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("create CIDefinition name :{}, description :{} failed!", name, description);
            RestResponse restResponse = new RestResponse();
            restResponse.setCode(1);
            restResponse.setMessage(e.getMessage());
            return restResponse;
        }
    }


    @RequestMapping("/rest/ci/deleteci")
    public RestResponse deleteCI(@RequestParam("ciid") Integer id) {
        if (ciService.deleteCIDefinition(id))
            return new RestResponse();
        else {
            RestResponse restResponse = new RestResponse();
            restResponse.setCode(1);
            restResponse.setMessage("failed to delete ci definition");
            return restResponse;
        }
    }

    @RequestMapping("/rest/ci/modifyci")
    public RestResponse modifyCI(@RequestParam("id") Integer id, @RequestParam("name") String name, @RequestParam("description") String description) {
        if (id == null)
            return new RestResponse(1, "failed to modify, id is null");
        if (ciService.modifyCIDefinition(id, name, description))
            return new RestResponse();
        else
            return new RestResponse(1, "failed to modify");
    }

    @RequestMapping("/rest/ci/ciattrs")
    public List<AttributeBaseInfo> getCIAttributes(@Param("ciid") int id) {
        CIDefinition ciDefinition = ciService.getCIDefinition(id);
        List<AttributeBaseInfo> list = new ArrayList<AttributeBaseInfo>();
        while (true) {
            if (ciDefinition == null)
                break;
            Set<CIAttribute> aset = ciDefinition.getAttributes();
            if (aset != null) {
                for (CIAttribute i : aset) {
                    AttributeBaseInfo ab = new AttributeBaseInfo(i);
                    list.add(0, ab);
                }
            }
            ciDefinition = ciDefinition.getParent();
        }
        return list;
    }

}
