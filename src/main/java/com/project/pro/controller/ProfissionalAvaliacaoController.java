package com.project.pro.controller;

import com.project.pro.model.dto.ImagemDTO;
import com.project.pro.model.dto.ProfissionalAvaliacaoDTO;
import com.project.pro.model.entity.AbstractEntity;
import com.project.pro.model.entity.Imagem;
import com.project.pro.model.entity.ProfissionalAvaliacao;
import com.project.pro.pattern.OperationsParam;
import com.project.pro.pattern.OperationsPath;
import com.project.pro.service.IProfissionalAvaliacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(ProfissionalAvaliacaoController.PATH)
@RequiredArgsConstructor
public class ProfissionalAvaliacaoController extends AbstractController<ProfissionalAvaliacao, ProfissionalAvaliacaoDTO> {

    public static final String PATH = ProfissionalController.PATH + OperationsPath.PARENT_ID + "/avaliacao";

    private final IProfissionalAvaliacaoService profissionalAvaliacaoService;

    @PostMapping
    public ProfissionalAvaliacaoDTO incluir(
            @PathVariable(OperationsParam.PARENT_ID) Integer idProfissional,
            @RequestBody ProfissionalAvaliacaoDTO profissionalAvaliacaoDTO) {

        return profissionalAvaliacaoService.incluir(idProfissional, profissionalAvaliacaoDTO.toEntity()).toDto();
    }

    @DeleteMapping(OperationsPath.ID)
    public void excluir(@PathVariable(OperationsParam.ID) Integer idAvaliacao) {
        profissionalAvaliacaoService.excluir(idAvaliacao);
    }

    @PutMapping(OperationsPath.ID)
    public void editar(@PathVariable(OperationsPath.ID) Integer idAvaliacao, @RequestBody ProfissionalAvaliacaoDTO profissionalAvaliacaoDTO) {
        profissionalAvaliacaoService.editar(idAvaliacao, profissionalAvaliacaoDTO.toEntity());
    }

    @DeleteMapping("/imagem/{idImagem}")
    public void excluirImagem(@PathVariable("idImagem") Integer idImagem) {
        profissionalAvaliacaoService.excluirImagem(idImagem);
    }

    @Override
    public ProfissionalAvaliacaoDTO findById(@PathVariable(OperationsParam.ID) Integer id) {
        ProfissionalAvaliacaoDTO avaliacaoDTO = super.findById(id);

        ProfissionalAvaliacao avaliacao = avaliacaoDTO.toEntity();

        List<Imagem> allImagens = profissionalAvaliacaoService.findAllImagens(avaliacaoDTO.toEntity());

        avaliacao.setImagens(allImagens);

        return avaliacao.toDto();
    }
}
