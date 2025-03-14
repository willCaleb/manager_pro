package com.project.pro.config.context;


import com.project.pro.exception.CustomRuntimeException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.repository.support.Repositories;
import org.springframework.stereotype.Component;

@Component
public class ContextImpl implements ApplicationContextAware, IContext{

    private static ApplicationContext context;

    private Repositories repositories;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
//        this.repositories = new Repositories(context);
    }

    public static  <S> S getBean(Class<S> clazz) {
        try {
            S bean = context.getAutowireCapableBeanFactory().getBean(clazz);
            return bean;
        }catch (Exception e) {
            throw new CustomRuntimeException(e.getMessage());
        }
    }
}
