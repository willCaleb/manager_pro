package com.project.pro.service.impl;

//import com.project.pro.config.context.IContext;

import com.project.pro.config.context.IContext;
import com.project.pro.context.Context;
import com.project.pro.enums.EnumCustomException;
import com.project.pro.exception.CustomException;
import com.project.pro.model.dto.AbstractDTO;
import com.project.pro.model.entity.AbstractEntity;
import com.project.pro.model.entity.Cliente;
import com.project.pro.model.entity.Profissional;
import com.project.pro.model.entity.Usuario;
import com.project.pro.repository.ClienteRepository;
import com.project.pro.repository.UsuarioRepository;
import com.project.pro.service.IAbstractService;
import com.project.pro.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.support.Repositories;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@SuppressWarnings("unchecked")
public abstract class AbstractService<E extends AbstractEntity<?, DTO>, DTO extends AbstractDTO<?, E>, R extends JpaRepository> implements IAbstractService<E, DTO, R>{

    Type[] genericTypes = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments();
    private final Class<E> entityClass = (Class<E>) genericTypes[0];

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ApplicationContext applicationContext;

    private Repositories repositories;

    public IContext getContext() {
        return IContext.context();
    }

    public Cliente getCliente() {
        return clienteRepository.findByUsuario(getUsuario());
    }

    public Usuario getUsuario() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return usuarioRepository.findByUsername(((UserDetails) principal).getUsername());
        }
        throw new CustomException(EnumCustomException.USUARIO_NAO_ENCONTRADO);
    }

    public Profissional getProfissional() {
        return Context.CURRENT_PROFISSIONAL.get();
    }

    public <R extends AbstractEntity<?, ?>, ID>JpaRepository getRepository(Class<R> clazz) {
        repositories = new Repositories(applicationContext);
        return (JpaRepository<R, ID>) repositories
                .getRepositoryFor(clazz)
                .orElseThrow(() -> new CustomException("Repositório não encontrado {0} ", clazz.getSimpleName()));
    }

    public void editar(E abstractEntity, Integer idEntityManaged) {
        E entityManaged = findAndValidate(idEntityManaged);
        ObjectUtils.copyAllValuesWithoutId(abstractEntity, entityManaged);
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
        return getRepository(entityClass);
    }

    public E findAndValidate(Integer id) {
        return (E) getRepository()
                .findById(id).orElseThrow(() -> new CustomException("Não foi encontrado " + entityClass.getSimpleName() + " com o id " + id));
    }

    public <S extends AbstractService> S getService(Class<S> clazz) {
        return getContext().getBean(clazz);
    }

    public static <E extends AbstractEntity> List<E> reverseEntityList(List<E> list) {
        return list
                .stream()
                .sorted(Comparator.comparing(E::getId).reversed())
                .collect(Collectors.toList());
    }


}
