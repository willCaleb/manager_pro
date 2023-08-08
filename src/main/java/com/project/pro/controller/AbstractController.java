package com.project.pro.controller;

import com.project.pro.exception.CustomException;
import com.project.pro.exception.ErrorResponse;
import com.project.pro.model.beans.PageableBean;
import com.project.pro.model.dto.AbstractDTO;
import com.project.pro.model.entity.AbstractEntity;
import com.project.pro.pattern.OperationsQueryParam;
import com.project.pro.service.impl.AbstractService;
import com.project.pro.service.impl.GenericRepository;
import com.project.pro.utils.ListUtils;
import com.project.pro.utils.Utils;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public Class<E> getEntityClass() {
        Type[] genericTypes = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments();
        return (Class) genericTypes[0];
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
        return getRepository(getEntityClass()).findAll();
    }

    public Specification<E> getSpecificationEqualOrLike(Map<String, Object> filters) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            for (Map.Entry<String, Object> entry : filters.entrySet()) {
                String chave = entry.getKey();
                Object valor = entry.getValue();

                if (valor != null) {
                    if (String.class.equals(root.get(chave).getJavaType())) {
                        predicates.add(criteriaBuilder.like(root.get(chave), "%" + valor + "%"));
                    } else {
                        predicates.add(criteriaBuilder.equal(root.get(chave), valor));
                    }
                }
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    @GetMapping("/filter")
    public Page<DTO> getFiltered(@RequestParam(required = false) Map<String, Object> filters, Pageable pageable) {
        if (onlyPageableFilters(filters)) {
            return fromPagedEntityToPagedDTO(getAllPaged(pageable), pageable);
        }
        Page<E> allPagedAndFiltered = getAllPagedAndFiltered(filters, pageable);
        return fromPagedEntityToPagedDTO(allPagedAndFiltered, pageable);
    }

    private Page<E> getAllPaged(Pageable pageable) {
        return (Page<E>) getRepository(getEntityClass()).findAll(pageable);
    }

    private boolean onlyPageableFilters(Map<String, Object> filters) {
        final List<String> filterOpertaions = new ArrayList<>();

        ListUtils.stream(OperationsQueryParam.OPERATIONS)
                .filter(filters::containsKey)
                .forEach(operation -> filterOpertaions.add(operation.toString()));

        return ListUtils.isNotNullOrEmpty(filterOpertaions)
                && Utils.equals(filters.size(), filterOpertaions.size());
    }

    private Page<E> getAllPagedAndFiltered(@RequestParam(required = false) Map<String, Object> filters, Pageable pageable) {

        List<String> operations = OperationsQueryParam.OPERATIONS;

        operations.forEach(operation -> {
            if (filters.containsKey(operation)) {
                filters.remove(operation);
            }
        });

        return getSpecificationRepository(getEntityClass())
                .findAll(
                        getSpecificationEqualOrLike(filters), pageable);
    }

    private Page<DTO> fromPagedEntityToPagedDTO(Page<E> page, Pageable pageable) {
        List<E> content = page.getContent();
        List<DTO> dtoList = toDtoList(content);
        return new PageImpl<>(dtoList, pageable, page.getTotalElements());
    }

    private String[] getOrder(String value) {
        if (value.contains("::")) {
            return value.split("::");
        }
        return new String[]{value};
    }

    private Sort.Direction getDirection(String direction) {
        if (StringUtil.isNullOrEmpty(direction) || "asc".equals(direction)) {
            return Sort.Direction.ASC;
        }
        return Sort.Direction.DESC;
    }

}





























