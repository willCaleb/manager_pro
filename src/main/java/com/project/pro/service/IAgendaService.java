package com.project.pro.service;

import com.project.pro.model.beans.AgendaBean;
import com.project.pro.model.entity.Agenda;

import java.util.Date;
import java.util.List;

public interface IAgendaService {

    Agenda incluir(Agenda agenda);

    Agenda editar(Integer idAgenda, Agenda agenda);

    void verificarDisponibilidadeHorario(Date horaInicio, Date horaFim, Integer idProfissional);

    void verificarDisponibilidadeHorario(Agenda agenda);

    List<AgendaBean> buscarHorariosDisponiveis(Integer idProfissional, Integer idServico);

}
