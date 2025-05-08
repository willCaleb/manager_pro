package com.project.pro.controller;

import com.project.pro.model.dto.AgendaDTO;
import com.project.pro.model.entity.Agenda;
import com.project.pro.pattern.OperationsParam;
import com.project.pro.pattern.OperationsPath;
import com.project.pro.service.IAgendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AgendaController.PATH)
@RequiredArgsConstructor
public class AgendaController extends AbstractController<Agenda, AgendaDTO>{

    public static final String PATH = "/agenda";

    private final IAgendaService agendaService;

    @PostMapping
    public AgendaDTO incluir(@RequestBody AgendaDTO agendaDTO) {
        Agenda agenda = agendaDTO.toEntity();
        return agendaService.incluir(agenda).toDto();
    }

    @PutMapping(OperationsPath.ID)
    public AgendaDTO editar(@PathVariable(OperationsParam.ID) Integer idAgenda, @RequestBody AgendaDTO agendaDTO) {
        return agendaService.editar(idAgenda, agendaDTO.toEntity()).toDto();
    }
}
