package com.personal.practice.spring.life;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.config.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class LifeTest implements  BeanFactoryPostProcessor, InstantiationAwareBeanPostProcessor, BeanNameAware,BeanFactoryAware , BeanPostProcessor, InitializingBean, DisposableBean {
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("BeanFactoryAware setBeanFactory");
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("BeanFactoryPostProcessor postProcessBeanFactory");
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("BeanNameAware setBeanName:"+name);
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("BeanPostProcessor postProcessBeforeInitialization:"+beanName);
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("BeanPostProcessor postProcessAfterInitialization:"+beanName);
        return null;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean afterPropertiesSet");
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        System.out.println("InstantiationAwareBeanPostProcessor postProcessBeforeInstantiation:"+beanName);
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        System.out.println("InstantiationAwareBeanPostProcessor postProcessAfterInstantiation ："+beanName);
        return true;
    }

    @PostConstruct
    public void init(){
        System.out.println("init");
    }


    @Override
    public void destroy() throws Exception {
        System.out.println("DisposableBean destroy");
    }

    @PreDestroy
    public void preDestory(){
        System.out.println("preDestory");
    }

}
