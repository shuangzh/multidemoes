package com.shuangzh.dao;

import com.shuangzh.dao.mybatis.domain.Course;
import com.shuangzh.dao.mybatis.domain.PhoneNumber;
import com.shuangzh.dao.mybatis.domain.Student;
import com.shuangzh.dao.mybatis.domain.Tutor;
import com.shuangzh.dao.mybatis.mappers.StudentMapper;
import com.shuangzh.dao.mybatis.services.StudentService;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

/**
 * Unit test for simple App.
 */


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
public class AppTest {
    private static Logger logger = LoggerFactory.getLogger(AppTest.class);

    @Autowired
    @Qualifier("apifactory")
    SqlSessionFactory sessionFactory;

    @Autowired
    StudentService studentService;

    @Autowired
    SqlSessionTemplate sqlSessionTemplate;
//
//    @Test
//    public void SessionFactoryTest() {
//        SqlSession sqlSession = sessionFactory.openSession();
//        StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
//        Student student = studentMapper.findStudentById(11);
//
//        student.setName("zhoushuang");
//
//        student.setPhone(new PhoneNumber("11-02-1380988"));
//
//        int i= studentMapper.insertStudent(student);
//        System.out.println("rentun i="+i +"  student id=" + student.getStudId());
//
//        sqlSession.commit();
//
//
//        Student st1 = sqlSession.selectOne("com.shuangzh.dao.mybatis.mappers.StudentMapper.selectStudentWithAddress", 13);
//        st1 = sqlSession.selectOne("com.shuangzh.dao.mybatis.mappers.StudentMapper.selectStudentWithAddress1", 11);
//
//        st1 = sqlSession.selectOne("com.shuangzh.dao.mybatis.mappers.StudentMapper.selectStudentWithAddress2", 25);
//
//        Tutor tutor1=sqlSession.selectOne("com.shuangzh.dao.mybatis.mappers.StudentMapper.findTutorById", 1);
//
//        tutor1=sqlSession.selectOne("com.shuangzh.dao.mybatis.mappers.StudentMapper.findTutorById1", 2);
//
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("tutorId", 1);
//        map.put("courseName", "%Java%");
//        List<Course> ls = sqlSession.selectList("com.shuangzh.dao.mybatis.mappers.StudentMapper.searchCourses",map);
//
//
//
//        List<Course> cls=sqlSession.selectList("com.shuangzh.dao.mybatis.mappers.StudentMapper.findAllCourses");
//
//
//        sqlSession.close();
//
//        List<Student>  list = studentService.findAllStudents();
//        for(Student st:list)
//        {
//            logger.info("id : {} , name: {}, email: {}, phone:{}", st.getStudId(), st.getName(), st.getEmail(), st.getPhone()==null?"null":st.getPhone().getAsString());
//        }
//
//    }


    @Test
    public void StudentMapperTest() {

        Student student1 = sqlSessionTemplate.selectOne("sql.StudentMapper.selectStudentById", 1);

        Student student2 = sqlSessionTemplate.selectOne("sql.StudentMapper.selectStudentByName", "zhou01");

        List<Student> students=sqlSessionTemplate.selectList("sql.StudentMapper.getAllStudents");


        HashMap params=new HashMap();
        params.put("studId",4);
        params.put("name","zhou03");
        params.put("email","Jon@com.cn");
        int i=sqlSessionTemplate.update("sql.StudentMapper.updateStudent", params);

        params.clear();
        params.put("name","zhou03");
        params.put("email","new@com.cn");
        i=sqlSessionTemplate.update("sql.StudentMapper.updateStudent", params);

        params.clear();
        HashSet<Integer> studIds=new HashSet<Integer>();
        studIds.add(11);
        studIds.add(12);
        studIds.add(13);
        params.put("studIds", studIds);
        params.put("name", "newName");
        i=sqlSessionTemplate.update("sql.StudentMapper.updateStudent", params);

        params.clear();
        HashSet<String> names = new HashSet<String>();
        names.add("newName");
        names.add("zhoushuang");

        params.put("names", names);
        params.put("phone","13809884175");
        i=sqlSessionTemplate.update("sql.StudentMapper.updateStudent", params);

        params.clear();
        params.put("studId",33);
        Student s1 = sqlSessionTemplate.selectOne("sql.StudentMapper.selectStudentWithAddress", params);

        params.clear();
        params.put("name","zhoushuang");
        List<Student> list1 = sqlSessionTemplate.selectList("sql.StudentMapper.selectStudentWithAddress", params);

        s1.setName("insert33");
        PhoneNumber phoneNumber=s1.getPhone();
        phoneNumber.setCountryConde("1");
        phoneNumber.setNumber("123");
        phoneNumber.setStateCode("2");

        i=sqlSessionTemplate.insert("sql.StudentMapper.insertStudent", s1);

    }

}
