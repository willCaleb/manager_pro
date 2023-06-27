package com.project.pro.controller;

import com.project.pro.model.dto.ProfissionalDTO;
import com.project.pro.model.entity.Profissional;
import com.project.pro.pattern.OperationsParam;
import com.project.pro.pattern.OperationsPath;
import com.project.pro.service.impl.ProfissionalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profissional")
@RequiredArgsConstructor
public class ProfissionalController extends AbstractController<Profissional,ProfissionalDTO> {

    private final ProfissionalService profissionalService;

    @PostMapping
    public ProfissionalDTO incluir(@RequestBody ProfissionalDTO profissionalDTO) {
        return profissionalService.incluir(profissionalDTO.toEntity()).toDto();
    }

    @PutMapping(OperationsPath.ID)
    public void editar(@RequestBody ProfissionalDTO profissionalDTO, @PathVariable(OperationsParam.ID) Integer id){
        profissionalService.editar(id, profissionalDTO.toEntity());
    }

    @PutMapping(OperationsPath.ID + "/servico/{idServico}")
    public ProfissionalDTO incluirServico(@PathVariable(OperationsParam.ID) Integer idProfissional, @PathVariable("idServico") Integer idServico) {
        return profissionalService.incluirServico(idServico, idProfissional).toDto();
    }

}
