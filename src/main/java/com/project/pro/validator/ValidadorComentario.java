package com.project.pro.validator;

import com.project.pro.model.entity.Comentario;

public class ValidadorComentario implements IValidador<Comentario> {
    @Override
    public void validarCamposObrigatorios(Comentario comentario) {
        ValidateFields validate = new ValidateFields();
        validate.add(comentario.getRemetente(), "Remetente");
        validate.add(comentario.getDestinatario(), "Destinatario");
        validate.add(comentario.getComentario(), "Comentário");
        validate.validate();
    }

    @Override
    public void validarTamanhoCampo(Comentario comentario) {
        ValidateFieldSize validate = new ValidateFieldSize();
        validate.validarTamanhoMaximo(comentario.getComentario(), 1000);
    }

    @Override
    public void validarInsert(Comentario comentario) {
        validarCamposObrigatorios(comentario);
        validarTamanhoCampo(comentario);
    }
}
