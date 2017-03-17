package com.shuangzh.dao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.context.ContextLoader;

/**
 * Hello world!
 *
 */

@SpringBootApplication
public class App 
{

    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        SpringApplication.run(App.class, args);
//        ContextLoader.getCurrentWebApplicationContext().getServletContext().setAttribute("project","my boot project");

    }
}
