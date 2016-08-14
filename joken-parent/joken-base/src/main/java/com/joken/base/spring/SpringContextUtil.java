package com.joken.base.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 
 * 获取spring容器，以访问容器中定义的其他bean
 * 
 * @author eric
 */
public class SpringContextUtil implements ApplicationContextAware {

    /**
     * Spring应用上下文环境
     */
    private static ApplicationContext applicationContext;

    /**
     * 实现ApplicationContextAware接口的回调方法，设置上下文环境
     * 
     * @param context
     *            应用上下文
     * @throws BeansException
     */
    public final void setApplicationContext(final ApplicationContext context) {
        SpringContextUtil.applicationContext = context;
    }

    /**
     * @return ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 获取spring bean对象 这里重写了bean方法，起主要作用
     * 
     * @param name
     *            bean定义名称
     * @return Object 一个以所给名字注册的bean的实例
     * @throws BeansException
     */
    public static Object getBean(final String name) {
        return applicationContext.getBean(name);
    }

}
