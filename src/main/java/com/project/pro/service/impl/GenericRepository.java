package com.project.pro.service.impl;


import com.project.pro.model.entity.AbstractEntity;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Setter
@Service
public class GenericRepository<E extends AbstractEntity> {

    @PersistenceContext
    private EntityManager em;

//    @Autowired
    private Class<E> entityClass;

    private CriteriaBuilder getCriteriaBuilder() {
        return em.getCriteriaBuilder();
    }

//    @SuppressWarnings("unchecked")
//    private Class<E> getEntityClass() {
//        Type[] genericTypes = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments();
//        return  (Class) genericTypes[0];
//    }

//    @SuppressWarnings("unchecked")
//    private Class<E> getEntityClass() {
//        Type genericSuperclass = getClass().getGenericSuperclass();
//        if (genericSuperclass instanceof ParameterizedType) {
//            ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
//            Type[] typeArguments = parameterizedType.getActualTypeArguments();
//            if (typeArguments != null && typeArguments.length > 0) {
//                Type typeArgument = typeArguments[0];
//                if (typeArgument instanceof Class) {
//                    return (Class<E>) typeArgument;
//                }
//            }
//        }
//        throw new IllegalArgumentException("Unable to determine the entity class.");
//    }

    private CriteriaQuery<E> getCriteriaQuery() {
        return getCriteriaBuilder()
                .createQuery(entityClass);
    }

    private Root<E> getRoot() {
        return getCriteriaQuery()
                .from(entityClass);
    }

    private TypedQuery<E> getTypedQuery() {
        return em.createQuery(getCriteriaQuery());
    }

    private List<E> getResults() {
        return getTypedQuery().getResultList();
    }

    @Transactional
    public List<E> getFilteredResult(String field, Map<String, Object> filters) {
        getCriteriaQuery().where(predicateWhere(field, filters));
        return getTypedQuery().getResultList();
    }

    @Transactional
    public List<E> getFilteredResult(Map<String, Object> filters) {
        getCriteriaQuery().where(predicateLike(filters).toArray(new Predicate[0]));
        return getResults();
    }

    private List<Predicate> predicateWhere(Map<String, Object> filters) {

        List<Predicate> predicates = new ArrayList<>();

        filters.forEach((key, value) -> predicates.add((Predicate) getCriteriaQuery()
                .where(getCriteriaBuilder()
                        .equal(getRoot().
                                get(key), value))));

        return predicates;
    }

    private Predicate predicateWhere(String field, Map<String, Object> filters) {
        return (Predicate) getCriteriaQuery()
                .where(getCriteriaBuilder()
                        .equal(getRoot().
                                get(field), filters.get(field)));
    }

    public Predicate predicateLike(String field, Map<String, Object> filters) {
        return (Predicate) getCriteriaQuery()
                .where(getCriteriaBuilder()
                        .like(getRoot()
                                .get(field), (String) filters.get("%" + field + "%")));
    }

    public List<Predicate> predicateLike(Map<String, Object> filters) {

        List<Predicate> predicates = new ArrayList<>();
        filters.forEach((key, value) -> predicates.add((Predicate) getCriteriaQuery()
                .where(getCriteriaBuilder()
                        .like(getRoot()
                                .get(key), "%" + value + "%"))));

        return predicates;
    }
}
