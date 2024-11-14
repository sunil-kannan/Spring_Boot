package com.learning.Spring_Boot.bean_life_cycle;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class BeanLifeCycle implements InitializingBean, DisposableBean {
    public BeanLifeCycle(){
        System.out.println("Inside BeanLifeCycle Constructor");
    }

    /**
     * Both the afterPropertiesSet() and destroy() method is the interface method, which is used to customize the bean nature.
     * It is an old way of doing
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("beanLifeCycle class bean created (called from InitializingBean Interface)");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("beanLifeCycle class bean destroyed (called from DisposableBean Interface)");
    }

    /**
     * PostConstruct and PreDestroy is modern feature you can just use this annotation instead of implementing
     * InitializeBean interface and DisposableBean interface
     */
    @PostConstruct
    public void afterBeanCreation(){
        System.out.println("beanLifeCycle class bean created (called from PostConstruct annotation)");
    }

    @PreDestroy
    public void afterBeanDestroy(){
        System.out.println("beanLifeCycle class bean destroyed (called from PreDestroy annotation)");
    }


}
