package com.project.pro.config;


import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IContext {

    IContext context = new ContextImpl();

    <T> T getBean(Class<T> clazz);

    static IContext context() {
        return context;
    }

}
