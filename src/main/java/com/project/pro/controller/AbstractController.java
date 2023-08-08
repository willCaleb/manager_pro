package com.project.pro.controller;

import com.project.pro.exception.CustomException;
import com.project.pro.exception.ErrorResponse;
import com.project.pro.model.beans.PageableBean;
import com.project.pro.model.dto.AbstractDTO;
import com.project.pro.model.entity.AbstractEntity;
import com.project.pro.service.impl.AbstractService;
import com.project.pro.service.impl.GenericRepository;
import com.project.pro.utils.Utils;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RestController
//@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public abstract class AbstractController<E extends AbstractEntity<?, DTO>, DTO extends AbstractDTO<?, E>> extends AbstractService<E, DTO, JpaRepository> {

    @Autowired
    private GenericRepository<E> genericRepository;

    private void setGenericRepositoryEntityClass() {
        Type[] genericTypes = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments();
        Class<E> entityClass = (Class) genericTypes[0];
        genericRepository.setEntityClass(entityClass);
    }


    @ExceptionHandler(value = CustomException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleCustomException(CustomException ex) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    public List<E> toEntityList(List<DTO> list) {
        List retorno = new ArrayList();
        if (Utils.isEmpty(list)) return retorno;

        for (DTO item : list) {
            retorno.add(((AbstractDTO) item).toEntity());
        }
        return retorno;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public DTO findById(@PathVariable("id") Integer id) {
        Type[] genericTypes = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments();
        Class<E> entityClass = (Class) genericTypes[0];

        E retorno = (E) getRepository(entityClass).findById(id).orElseGet(() -> {
            throw new CustomException("Não foi encontrado " + entityClass.getSimpleName() + " com o id " + id);
        });
        return retorno.toDto();
    }

    @GetMapping("/list")
    @ResponseBody
    public List<DTO> findAll() {

        return toDtoList(findAllEntity());
    }


    public List<DTO> toDtoList(List<E> list) {
        List retorno = new ArrayList();
        if (Utils.isEmpty(list)) return retorno;
        for (E item : list) {
            retorno.add(((AbstractEntity) item).toDto());
        }
        return retorno;
    }

    private List<E> findAllEntity() {
        Type[] genericTypes = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments();
        Class<E> entityClass = (Class) genericTypes[0];

        return getRepository(entityClass).findAll();
    }


    @GetMapping("/filter")
    public Page<DTO> getFiltered(@RequestParam(required = false) Map<String, Object> filters, Pageable pageable) {

        List<E> entidades = new ArrayList<>();
//        if (MapUtils.isNotNullOrNotEmpty(filters)) {
//            setGenericRepositoryEntityClass();
//            entidades = genericRepository.getFilteredResult(filters);
//        }else {
            entidades = findAllEntity();
//        }
//        Specification.where(filters);

        List<DTO> dtoList = toDtoList(entidades);

        int size = filters.containsKey("size") ? Integer.valueOf((String)filters.get("size")) : 10;
        int page = filters.containsKey("page") ? Integer.valueOf((String)filters.get("page")) : 0;


        PageableBean<DTO> pageableBean = new PageableBean<>();
        pageableBean.setPage(page);
        pageableBean.setContent(dtoList);
        pageableBean.setPageSize(size);

//        getRepository().findAll(filters);
//        if (filters.containsKey("sort")) {
//
//            String[] parts = getOrder((String) filters.get("sort"));
//
//            String fieldToSort = parts[0];
//
//            String direction = "";
//
//            if (NumericUtils.isGreater(parts.length, 1)) {
//                direction = parts[1];
//            }
//
//            Sort byId = Sort.by(getDirection(direction), fieldToSort);
//
//            Pageable pageable = pageableBean.getPageable();
//
//            System.out.println();
//        }

        return pageableBean.getPaged();
    }

    private Specification<E> getSpecification(Map filters) {
        return new Specification<E>() {
            @Override
            public Predicate toPredicate(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return root.isNotNull();
            }
        };
    }

    private String[] getOrder(String value){
        if (value.contains("::")) {
            return value.split("::");
        }
        return new String[]{value};
    }

    private Sort.Direction getDirection(String direction) {
        if (StringUtil.isNullOrEmpty(direction ) || "asc".equals(direction)) {
            return Sort.Direction.ASC;
        }
        return Sort.Direction.DESC;
    }

}





























