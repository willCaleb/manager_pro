package com.project.pro.validator;

import com.project.pro.enums.EnumCustomException;
import com.project.pro.exception.CustomException;
import com.project.pro.model.entity.Agenda;
import com.project.pro.repository.AgendaRepository;
import com.project.pro.utils.ListUtils;

import java.util.List;

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
        validarHorarioDisponivel(agenda);
    }


    public void validarHorarioDisponivel(Agenda agenda) {

        if(agenda.getProfissional().isMultiploAgendamento()) return;
        List<Agenda> agendasNoMesmoHorario = agendaRepository.buscarAgendaEntreHorarios(agenda.getDataInicio(), agenda.getDataFim(), agenda.getProfissional().getId());

        if (ListUtils.isNotNullOrEmpty(agendasNoMesmoHorario)) {
            throw new CustomException(EnumCustomException.AGENDA_HORARIO_INDISPONIVEL, agenda.getProfissional().getPessoa().getNome());
        }
    }
}
