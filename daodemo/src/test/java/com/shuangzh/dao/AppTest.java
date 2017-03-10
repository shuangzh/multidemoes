package com.shuangzh.dao;

import com.shuangzh.dao.mybatis.domain.PhoneNumber;
import com.shuangzh.dao.mybatis.domain.Student;
import com.shuangzh.dao.mybatis.mappers.StudentMapper;
import com.shuangzh.dao.mybatis.services.StudentService;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

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

    @Test
    public void SessionFactoryTest() {
        SqlSession sqlSession = sessionFactory.openSession();
        StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
        Student student = studentMapper.findStudentById(11);

        student.setName("zhoushuang");

        student.setPhone(new PhoneNumber("11-02-1380988"));

        int i= studentMapper.insertStudent(student);
        System.out.println("rentun i="+i +"  student id=" + student.getStuId());

        sqlSession.commit();
        sqlSession.close();


        List<Student>  list = studentService.findAllStudents();
        for(Student st:list)
        {
            logger.info("id : {} , name: {}, email: {}, phone:{}", st.getStuId(), st.getName(), st.getEmail(), st.getPhone()==null?"null":st.getPhone().getAsString());
        }

    }


}
