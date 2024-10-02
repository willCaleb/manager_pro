package com.project.pro.validator;

import com.project.pro.enums.EnumCustomException;
import com.project.pro.exception.CustomException;
import com.project.pro.model.entity.Usuario;
import com.project.pro.repository.UsuarioRepository;
import com.project.pro.utils.StringUtil;
import com.project.pro.utils.Utils;

public class ValidadorUsuario implements IValidador<Usuario>{

    private UsuarioRepository usuarioRepository;

    public void setUsuarioRepository(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void validarCamposObrigatorios(Usuario usuario) {
        ValidateFields validateFields = new ValidateFields();
        validateFields.add(usuario.getPassword(), "E-mail");
        validateFields.add(usuario.getPassword(), "Senha");
        validateFields.validate();
    }

    @Override
    public void validarInsert(Usuario usuario) {
        validarCamposObrigatorios(usuario);
        validarEmail(usuario);
        validarDuplicidadeEmail(usuario);
    }
    private void validarEmail(Usuario usuario) {
        if(!StringUtil.isValidEmail(usuario.getUsername())) {
            throw new CustomException(EnumCustomException.EMAIL_INVALIDO);
        }
    }

    private void validarDuplicidadeEmail(Usuario usuario) {
        Usuario usuarioManaged = usuarioRepository.findByUsername(usuario.getUsername());

        if(Utils.isNotEmpty(usuarioManaged)) {
            throw new CustomException(EnumCustomException.EMAIL_JA_CADASTRADO);
        }
    }
}
