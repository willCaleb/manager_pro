package com.project.pro.config.context;


import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IContext {

    IContext context = new ContextImpl();

    static IContext context() {
        return context;
    }

}
