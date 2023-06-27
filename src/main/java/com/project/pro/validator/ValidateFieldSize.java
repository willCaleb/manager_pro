package com.project.pro.validator;

import com.project.pro.exception.CustomException;
import com.project.pro.utils.NumericUtils;

public class ValidateFieldSize {

    public void validarTamanhoMaximo(String field, Integer tamMax) {
        if (NumericUtils.isGreater(field.length(), tamMax)) {
            throw new CustomException("Campo com tamanho maior que o permitido: ", field);
        }
    }

    public void validarTamanhoMinimo(String field, Integer tamMin) {
        if (NumericUtils.isSmaller(field.length(), tamMin)) {
            throw new CustomException("Campo com tamanho menor que o permitido: ", field);
        }
    }

}
