package com.project.pro.validator;

import com.project.pro.enums.EnumCustomException;
import com.project.pro.exception.CustomRuntimeException;
import com.project.pro.model.entity.Categoria;
import com.project.pro.service.impl.CategoriaService;
import com.project.pro.utils.Utils;

public class ValidadorCategoria implements IValidador<Categoria> {

    private CategoriaService categoriaService;

    public void setCategoriaService(CategoriaService service) {
        this.categoriaService = service;
    }
    @Override
    public void validarCamposObrigatorios(Categoria categoria) {
        ValidateFields validate = new ValidateFields();
        validate.add(categoria.getNome(), "Nome");
        validate.add(categoria.getCategoriaPrincipal(), "Categoria principal");
        validate.validate();
    }

    @Override
    public void validarInsert(Categoria categoria) {
        validarCamposObrigatorios(categoria);
    }

    @Override
    public void validarInsertOuUpdate(Categoria categoria) {
        validarCategoriaPrincipal(categoria);
    }

    private void validarCategoriaPrincipal(Categoria categoria) {
        if (Utils.isNotEmpty(categoria.getCategoriaSuperior())) {
            Categoria categoriaSuperior = categoriaService.findAndValidate(categoria.getCategoriaSuperior().getId());
            validarCategoriaPrincipalIgual(categoria, categoriaSuperior);
        }
    }

    private void validarCategoriaPrincipalIgual(Categoria categoria, Categoria categoriaSuperior) {
        if (!Utils.equals(categoria.getCategoriaPrincipal(), categoriaSuperior.getCategoriaPrincipal())) {
            throw new CustomRuntimeException(EnumCustomException.CATEGORIA_CATEGORIA_PRINCIPAL_DIFERENTE);
        }
    }
}
