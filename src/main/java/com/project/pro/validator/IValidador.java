package com.project.pro.validator;

import com.project.pro.model.entity.AbstractEntity;

public interface IValidador<E extends AbstractEntity> {

    void validarCamposObrigatorios(E entity);

    default void validarTamanhoCampo(E entity){}

    default void validarInsert(E entity) {}

    default void validarInsertOuUpdate(E entity){}
}
