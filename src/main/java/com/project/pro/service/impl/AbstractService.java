package com.project.pro.service.impl;

//import com.project.pro.config.IContext;

import com.project.pro.config.IContext;
import com.project.pro.exception.CustomException;
import com.project.pro.model.dto.AbstractDTO;
import com.project.pro.model.entity.AbstractEntity;
import com.project.pro.service.IAbstractService;
import com.project.pro.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.support.Repositories;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

@Component
@SuppressWarnings("unchecked")
public abstract class AbstractService<E extends AbstractEntity<?, DTO>, DTO extends AbstractDTO<?, E>, R extends JpaRepository> implements IAbstractService<E, DTO, R>{

    Type[] genericTypes = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments();
    private Class<E> entityClass = (Class<E>) genericTypes[0];

    public IContext getContext() {
        return IContext.context();
    }


    @Autowired
    private ApplicationContext applicationContext;

    private Repositories repositories;


    public <E extends AbstractEntity<?, ?>, ID>JpaRepository getRepository(Class<E> clazz) {
        repositories = new Repositories(applicationContext);
        return (JpaRepository<E, ID>) repositories
                .getRepositoryFor(clazz)
                .orElseThrow(() -> new CustomException("Repositório não encontrado {0} ", clazz.getSimpleName()));
    }

    public void editar(E abstractEntity, Integer idEntityManaged) {
        E entityManaged = findAndValidate(idEntityManaged);
        ObjectUtils.copyAllValues(abstractEntity, entityManaged);
        getRepository().save(entityManaged);
    }

    public <E extends AbstractEntity<?, ?>>JpaSpecificationExecutor<E> getSpecificationRepository(Class<E> clazz) {
        repositories = new Repositories(applicationContext);
        return (JpaSpecificationExecutor<E>) repositories
                .getRepositoryFor(clazz)
                .orElseThrow(() -> new CustomException("Specification repository não encontrado."));
    }

    public <E extends AbstractEntity<?,?>> JpaSpecificationExecutor<E> getSpecificationRepository() {
        Class<E> entityClass = (Class) genericTypes[0];
        return getSpecificationRepository(entityClass);
    }

    public <E extends AbstractEntity<?, ?>, ID extends Object> JpaRepository<E, ID> getRepository() {

        Class<E> entityClass = (Class) genericTypes[0];

        return getRepository(entityClass);
    }

    public E findAndValidate(Integer id) {
        return (E) getRepository()
                .findById(id).orElseGet(() -> {
                    throw new CustomException("Não foi encontrado " + entityClass.getSimpleName() + " com o id " + id);
                });
    }

    public <S extends AbstractService> S getService(Class<S> clazz) {

        S bean = getContext().getBean(clazz);
        return bean;
    }

//    public <S extends AbstractService> S getService() {
//        return ;
//    }

}
