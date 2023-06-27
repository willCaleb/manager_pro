package com.project.pro.config;


import com.project.pro.exception.CustomException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.support.Repositories;

@Configuration
public class ContextImpl  implements ApplicationContextAware, IContext{
    private ApplicationContext context;

    private Repositories repositories;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
        this.repositories = new Repositories(context);
    }

    public <S> S getBean(Class<S> clazz) {
        try {
            return context.getAutowireCapableBeanFactory().getBean(clazz);
        }catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }
}
