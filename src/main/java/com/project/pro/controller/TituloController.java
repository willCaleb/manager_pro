package com.project.pro.controller;

import com.project.pro.model.dto.TituloDTO;
import com.project.pro.model.entity.Observacao;
import com.project.pro.model.entity.Titulo;
import com.project.pro.pattern.OperationsParam;
import com.project.pro.pattern.OperationsPath;
import com.project.pro.service.IObservacaoService;
import com.project.pro.service.ITituloService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(TituloController.PATH)
public class TituloController extends AbstractController<Titulo, TituloDTO>{

    public static final String PATH = "/titulo";

    private final ITituloService tituloService;

    private final IObservacaoService observacaoService;

    @PostMapping
    public TituloDTO incluir(@RequestBody TituloDTO tituloDTO) {
        return tituloService.incluir(tituloDTO.toEntity()).toDto();
    }

    @Override
    public TituloDTO findById(@PathVariable(OperationsParam.ID) Integer id) {
        TituloDTO tituloDTO = super.findById(id);

        List<Observacao> observacoes = observacaoService.listAllByEntity(tituloDTO.toEntity());
        tituloDTO.setObservacoes(toDtoList(observacoes, TituloDTO.class));
        return tituloDTO;
    }

    @PutMapping(OperationsPath.ID)
    public void editar(@RequestBody TituloDTO tituloDTO, @PathVariable(OperationsParam.ID) Integer idTitulo) {
        tituloService.editar(idTitulo, tituloDTO.toEntity());
    }
}
