package com.x.print.infrastructure.utility;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * @author PanLei
 * @version 1.0.0
 * @createTime 2023-03-29
 */
@Configuration
public class BeanManager implements ApplicationContextAware {


    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        if (BeanManager.applicationContext == null) {
            BeanManager.applicationContext = context;
        }
    }

    /**
     * 获取applicationContext的实例applCon
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 通过name,以及Clazz获取指定的Bean
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

    /**
     * 通过clazz获取Bean（推荐使用这种）
     */
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    /**
     * 通过name获取 Bean
     */
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }
}
