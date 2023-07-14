package com.project.pro.controller;

import com.project.pro.exception.CustomException;
import com.project.pro.exception.ErrorResponse;
import com.project.pro.model.dto.AbstractDTO;
import com.project.pro.model.entity.AbstractEntity;
import com.project.pro.service.impl.AbstractService;
import com.project.pro.utils.Utils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Component
@RestController
@SuppressWarnings("unchecked")
public abstract class AbstractController<E extends AbstractEntity<?, DTO>, DTO extends AbstractDTO<?, E>> extends AbstractService<E, DTO, JpaRepository> {

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
        Type[] genericTypes = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments();
        Class<E> entityClass = (Class) genericTypes[0];
        return toDtoList(getRepository(entityClass).findAll());
    }

    public List<DTO> toDtoList(List<E> list) {
        List retorno = new ArrayList();
        if (Utils.isEmpty(list)) return retorno;
        for (E item : list) {
            retorno.add(((AbstractEntity) item).toDto());
        }
        return retorno;
    }
}
