package org.service.impl;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author yu
 * @date 2020/5/9
 */
public class EmpServiceImplTest {

    @Test
    public void getAllEmp() {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        EmpServiceImpl service = context.getBean("empService", EmpServiceImpl.class);
        service.getAllEmp();
    }
}