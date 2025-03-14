package com.project.pro.validator;

import com.project.pro.exception.CustomRuntimeException;
import com.project.pro.utils.NumericUtils;

public class ValidateFieldSize {

    public void validarTamanhoMaximo(String field, Integer tamMax) {
        if (NumericUtils.isGreater(field.length(), tamMax)) {
            throw new CustomRuntimeException("Campo com tamanho maior que o permitido: ", field);
        }
    }

    public void validarTamanhoMinimo(String field, Integer tamMin) {
        if (NumericUtils.isSmaller(field.length(), tamMin)) {
            throw new CustomRuntimeException("Campo com tamanho menor que o permitido: ", field);
        }
    }

    public void  validarTamanhoIgual (String field, Integer tamanho) {
        if (!NumericUtils.isEquals(field.length(), tamanho)) {
            throw new CustomRuntimeException("Campo deve ter tamanho de {0} ", field);
        }
    }

}
