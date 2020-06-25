package com.jxb.test;

import com.jxb.test.entity.UserEntity;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author zhaohf
 * @date 2020/6/24 17:16
 */
public class SpringTest {
    public static void main(String[] args) {
        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("spring.xml");
        UserEntity userEntity1 =(UserEntity) applicationContext.getBean("demo");
        UserEntity userEntity2 =(UserEntity) applicationContext.getBean("demo");
        System.out.println(userEntity1);
        System.out.println(userEntity2);
    }
}
