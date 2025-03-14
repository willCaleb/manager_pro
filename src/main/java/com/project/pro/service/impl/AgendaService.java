package com.project.pro.service.impl;

import com.project.pro.enums.EnumCustomException;
import com.project.pro.enums.EnumStatusAgenda;
import com.project.pro.exception.CustomRuntimeException;
import com.project.pro.model.beans.AgendaBean;
import com.project.pro.model.dto.AgendaDTO;
import com.project.pro.model.entity.Agenda;
import com.project.pro.model.entity.Profissional;
import com.project.pro.repository.AgendaRepository;
import com.project.pro.service.IAgendaService;
import com.project.pro.utils.DateUtils;
import com.project.pro.utils.ListUtils;
import com.project.pro.utils.Utils;
import com.project.pro.validator.ValidadorAgenda;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AgendaService extends AbstractService<Agenda, AgendaDTO, AgendaRepository> implements IAgendaService{

    private ValidadorAgenda validadorAgenda = new ValidadorAgenda();
    private final AgendaRepository agendaRepository;
    private final ProfissionalService profissionalService;

    @PostConstruct
    private void setValidadorAgendaRepository() {
        validadorAgenda.setAgendaRepository(agendaRepository);
    }

    @Override
    public Agenda incluir(Agenda agenda) {
        validadorAgenda.validarInsert(agenda);

        onPrepareInsert(agenda);
        return agendaRepository.save(agenda);
    }

    private void onPrepareInsert(Agenda agenda) {
        verificarDisponibilidadeHorario(agenda);

        if (ListUtils.isNotNullOrEmpty(agenda.getItens())) {
            agenda.getItens().forEach(item -> item.getPedido().setProfissional(agenda.getProfissional()));
        }
        agenda.setStatus(EnumStatusAgenda.AGUARDANDO_CONFIRMACAO);
        agenda.setDataInclusao(DateUtils.getDate());
    }

    @Override
    public Agenda editar(Integer idAgenda, Agenda agenda) {

        Agenda agendaManaged = findAndValidate(idAgenda);

        if (!Utils.equals(agendaManaged.getDataInicio(), agenda.getDataInicio()) || !Utils.equals(agendaManaged.getDataFim(), agenda.getDataFim())) {
            verificarDisponibilidadeHorario(agenda);

            agendaManaged.setDataInicio(agenda.getDataInicio());
            agendaManaged.setDataFim(agenda.getDataFim());
            agendaManaged.setStatus(agenda.getStatus());
        }
        return agendaRepository.save(agendaManaged);
    }

    @Override
    public void verificarDisponibilidadeHorario(Date horaInicio, Date horaFim, Integer idProfissional) {

        Profissional profissional = profissionalService.findAndValidate(idProfissional);

        List<Agenda> agenda = agendaRepository.buscarAgendaEntreHorarios(horaInicio, horaFim, profissional.getId());

        if (Utils.isNotEmpty(agenda)) {
            throw new CustomRuntimeException(EnumCustomException.AGENDA_HORARIO_INDISPONIVEL, profissional.getPessoa().getNome());
        }
    }

    @Override
    public void verificarDisponibilidadeHorario(Agenda agenda) {
        verificarDisponibilidadeHorario(agenda.getDataInicio(), agenda.getDataFim(), agenda.getProfissional().getId());
    }

    @Override
    public List<AgendaBean> buscarHorariosDisponiveis(Integer idProfissional, Integer idServico) {
        return null;
    }
}
