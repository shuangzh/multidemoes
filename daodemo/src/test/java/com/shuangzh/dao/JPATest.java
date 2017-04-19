package com.shuangzh.dao;

import com.shuangzh.dao.jpa.Repository.CIAttributeRepo;
import com.shuangzh.dao.jpa.Repository.CIDefinitionRepo;
import com.shuangzh.dao.jpa.Repository.CIRelationRepo;
import com.shuangzh.dao.jpa.Services.CIService;
import com.shuangzh.dao.jpa.domain.CIAttribute;
import com.shuangzh.dao.jpa.domain.CIDefinition;
import com.shuangzh.dao.jpa.domain.CIRelation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Set;

/**
 * Created by admin on 2017/4/2.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
public class JPATest {
    private static Logger logger = LoggerFactory.getLogger(AppTest.class);

    @Autowired
    private CIDefinitionRepo ciDefinitionRepo;

    @Autowired
    private CIAttributeRepo ciAttributeRepo;

    @Autowired
    private CIRelationRepo ciRelationRepo;

    @Autowired
    private CIService ciService;

    @Test
    public void baseTest() {
        CIDefinition ciDefinition = new CIDefinition();
        ciDefinition.setName("ConfigItemDefine1");
        ciDefinition.setDescription("第一个CI配置定义");


        ciDefinitionRepo.save(ciDefinition);

        CIDefinition cd2 = new CIDefinition();
        cd2.setName("Config2");
        cd2.setDescription("第二个CI配置");

        ciDefinitionRepo.save(cd2);
        cd2.setParent(ciDefinition);
        ciDefinitionRepo.save(cd2);
        Iterable<CIDefinition> list = ciDefinitionRepo.findAll();
        int c=0;
        for (CIDefinition i : list){
            CIDefinition pci=i.getParent();
            Set<CIDefinition> cci=i.getChildren();
            if(cci !=null)
            {
                for(CIDefinition ccid:cci)
                    System.out.println(ccid.getName());
            }
            c++;
        }
    }

    @Test
    public void baseTest2()
    {
        CIAttribute ciAttribute= new CIAttribute();
        ciAttribute.setName("name");
        ciAttribute.setDescription("配置项名字");
        ciAttribute.setRequired(true);
        ciAttribute.setValueType(CIAttribute.ValueType.STRING);

        ciAttributeRepo.save(ciAttribute);

        CIDefinition ciDefinition = ciDefinitionRepo.findOne(31);

        ciAttribute.setCiDefinition(ciDefinition);

        ciAttributeRepo.save(ciAttribute);

        ciDefinition=ciDefinitionRepo.findOne(31);
    }

    @Test
    public void bassTest3() {
        CIDefinition ci1=new CIDefinition();
        ci1.setName("ci1");
        ci1.setDescription("ci1desc");

        CIDefinition ci2=new CIDefinition();
        ci2.setName("ci2");
        ci2.setDescription("ci2desc");

        ciDefinitionRepo.save(ci1);
        ciDefinitionRepo.save(ci2);

        CIRelation cl1=new CIRelation();
        cl1.setAction(CIRelation.Action.CLASSIFIED);
        cl1.setDescription("softer install in mechain");
        cl1.setRequired(true);
        cl1.setStartpoint(ci1);
        cl1.setEndpoint(ci2);

        ciRelationRepo.save(cl1);

        ci1=ciDefinitionRepo.findOne(ci1.getId());
        ci2=ciDefinitionRepo.findOne(ci2.getId());



    }

    @Test
    public void serviceTest()
    {
        CIDefinition ciDefinition01=ciService.addCI("CI01", "CI01 Descriptin", null);
        CIDefinition ciDefinition02=ciService.addCI("CI02", "CI02 Descriptin", null);
        CIDefinition ci01= ciService.addRelation(ciDefinition01, ciDefinition02, "new Relations", CIRelation.Action.INSTALLED, true);
        CIDefinition ci02= ciDefinitionRepo.findOne(ciDefinition02.getId());



    }

}
