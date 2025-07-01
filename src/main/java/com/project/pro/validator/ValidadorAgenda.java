package com.project.pro.validator;

import com.project.pro.enums.EnumCustomException;
import com.project.pro.enums.EnumStatusAgenda;
import com.project.pro.exception.CustomRuntimeException;
import com.project.pro.model.entity.Agenda;
import com.project.pro.repository.AgendaRepository;

public class ValidadorAgenda implements IValidador<Agenda>{

    private AgendaRepository agendaRepository;

    public void setAgendaRepository(AgendaRepository agendaRepository) {
        this.agendaRepository = agendaRepository;
    }

    @Override
    public void validarCamposObrigatorios(Agenda agenda) {
        ValidateFields validate = new ValidateFields();

        validate.add(agenda.getDataInicio(), "Data de início");
        validate.add(agenda.getDataFim(), "Data do fim");
        validate.add(agenda.getProfissional(), "Profissional");
//        validate.add(agenda.getItens(), "Itens");
        validate.validate();
    }

    @Override
    public void validarInsert(Agenda agenda) {
        validarCamposObrigatorios(agenda);
    }

    public void validarStatusEdicao(Agenda agenda) {
        if (EnumStatusAgenda.CANCELADO.equals(agenda.getStatus()) || EnumStatusAgenda.FINALIZADO.equals(agenda.getStatus())) {
            throw new CustomRuntimeException(EnumCustomException.AGENDA_STATUS_NAO_PERMITE_ALTERAR);
        }
    }
}
