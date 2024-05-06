package com.project.pro.controller;

import com.project.pro.exception.CustomException;
import com.project.pro.exception.ErrorResponse;
import com.project.pro.model.dto.AbstractDTO;
import com.project.pro.model.entity.AbstractEntity;
import com.project.pro.pattern.Constants;
import com.project.pro.pattern.OperationsParam;
import com.project.pro.pattern.OperationsPath;
import com.project.pro.pattern.OperationsQueryParam;
import com.project.pro.service.impl.AbstractService;
import com.project.pro.service.impl.GenericRepository;
import com.project.pro.utils.ListUtils;
import com.project.pro.utils.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.Predicate;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RestController
@SuppressWarnings("unchecked")
public abstract class AbstractController<E extends AbstractEntity<?, DTO>, DTO extends AbstractDTO<?, E>> extends AbstractService<E, DTO, JpaRepository> {

    @Autowired
    private GenericRepository<E> genericRepository;

    @PostConstruct
    private void setGenericRepositoryEntityClass() {
        genericRepository.setEntityClass(getEntityClass());
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

    public <D extends AbstractDTO, T extends AbstractEntity> List<T> toEntityList(List<D> dtoList, Class<T> clazz) {
        List<T> entityList = new ArrayList<>();

        for (D dto : dtoList) {
            entityList.add(new ModelMapper().map(dto, clazz));
        }
        return entityList;
    }

    @GetMapping(OperationsPath.ID)
    @ResponseBody
    public DTO findById(@PathVariable("id") Integer id) {

        E retorno = (E) getRepository(getEntityClass()).findById(id).orElseGet(() -> {
            throw new CustomException("Não foi encontrado " + getEntityClass().getSimpleName() + " com o id " + id);
        });
        return retorno.toDto();
    }

    @GetMapping("/list")
    @ResponseBody
    @Transactional
    public List<DTO> findAll() {
        return toDtoList(findAllEntity());
    }

    private Sort sortById(String ordem) {
        if (Constants.ASC.equalsIgnoreCase(ordem)) {
            return Sort.by(Sort.Direction.ASC, OperationsParam.ID);
        }
        return Sort.by(Sort.Direction.DESC, OperationsParam.ID);
    }

    @PutMapping(OperationsPath.ID + "/edit")
    public void editar(@RequestBody DTO abstractDto, @PathVariable(OperationsParam.ID) Integer id) {
        super.editar(abstractDto.toEntity(), id);
    }

    public Class<E> getEntityClass() {
        Type[] genericTypes = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments();
        return (Class) genericTypes[0];
    }

    public List<DTO> toDtoList(List<E> entityList) {
        List retorno = new ArrayList();
        if (Utils.isEmpty(entityList)) return retorno;
        for (E item : entityList) {
            retorno.add(((AbstractEntity) item).toDto());
        }
        return retorno;
    }

    public <I extends AbstractEntity, T extends AbstractDTO> List<T> toDtoList(List<I> entityList, Class<?> clazz) {
        List<T> dtoList = new ArrayList<>();
        entityList.forEach(entity -> dtoList.add((T) entity.toDto()));
        return dtoList;
    }

    private List<E> findAllEntity() {
        return getRepository(getEntityClass()).findAll(sortById(null));
    }

    private Specification<E> getSpecificationEqualOrLike(Map<String, Object> filters) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            for (Map.Entry<String, Object> entry : filters.entrySet()) {
                String chave = entry.getKey();
                Object valor = entry.getValue();

                if (valor != null) {
                    if (String.class.equals(root.get(chave).getJavaType())) {
                        predicates.add(criteriaBuilder.like(root.get(chave), "%" + valor + "%"));
                    } else if (root.get(chave).getJavaType().isEnum()) {
                        Class<Enum> enumType = (Class<Enum>) root.get(chave).getJavaType();
                        Enum enumValue = Enum.valueOf(enumType, valor.toString());
                        predicates.add(criteriaBuilder.equal(root.get(chave), enumValue));
                    } else {
                        predicates.add(criteriaBuilder.equal(root.get(chave), valor));
                    }
                }
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

    private void resolverSort(Map<String, Object> filters) {
        Sort sort;
        if (filters.containsKey(Constants.SORT)) {
            sort = sortById(filters.get(Constants.SORT).toString());
        } else {
            sort = sortById(null);
        }
        filters.put(Constants.SORT, sort);
    }

    @GetMapping("/filter")
    public Page<DTO> getFiltered(@RequestParam(required = false) Map<String, Object> filters, Pageable pageable) {
        if (onlyPageableFilters(filters)) {
            Sort sort = sortById(Utils.nvl(filters.get(Constants.SORT).toString(), null));

            Pageable pageableWithSort = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
            return fromPagedEntityToPagedDTO(getAllPaged(pageableWithSort), pageableWithSort);
        }
        resolverSort(filters);
        Page<E> allPagedAndFiltered = getAllPagedAndFiltered(filters, pageable);
        return fromPagedEntityToPagedDTO(allPagedAndFiltered, pageable);
    }

    private Page<E> getAllPaged(Pageable pageable) {
        return (Page<E>) getRepository(getEntityClass()).findAll(pageable);
    }

    private boolean onlyPageableFilters(Map<String, Object> filters) {
        final List<String> filterOperations = new ArrayList<>();

        ListUtils.stream(OperationsQueryParam.OPERATIONS)
                .filter(filters::containsKey)
                .forEach(operation -> filterOperations.add(operation.toString()));

        return ListUtils.isNotNullOrEmpty(filterOperations)
                && Utils.equals(filters.size(), filterOperations.size());
    }

    private Page<E> getAllPagedAndFiltered(@RequestParam(required = false) Map<String, Object> filters, Pageable pageable) {

        List<String> operations = OperationsQueryParam.OPERATIONS;

        operations.forEach(operation -> {
            if (filters.containsKey(operation)) {
                filters.remove(operation);
            }
        });

        return getSpecificationRepository(getEntityClass())
                .findAll(getSpecificationEqualOrLike(filters), pageable);
    }

    private Page<DTO> fromPagedEntityToPagedDTO(Page<E> page, Pageable pageable) {
        List<E> content = page.getContent();
        List<DTO> dtoList = toDtoList(content);
        return new PageImpl<>(dtoList, pageable, page.getTotalElements());
    }

}





























