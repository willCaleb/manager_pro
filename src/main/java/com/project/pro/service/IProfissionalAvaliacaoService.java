package com.project.pro.service;

import com.project.pro.model.dto.ProfissionalAvaliacaoDTO;
import com.project.pro.model.entity.Imagem;
import com.project.pro.model.entity.ProfissionalAvaliacao;
import com.project.pro.repository.ProfissionalAvaliacaoRepository;

import java.util.List;

public interface IProfissionalAvaliacaoService extends IAbstractService<ProfissionalAvaliacao, ProfissionalAvaliacaoDTO, ProfissionalAvaliacaoRepository> {

    ProfissionalAvaliacao incluir(Integer idProfissional, ProfissionalAvaliacao avaliacao);

    void editar(Integer idAvaliacao, ProfissionalAvaliacao avaliacao);

    void excluir(Integer idAvaliacao);

    void excluirImagem(Integer idImagem);

    List<Imagem> findAllImagens(ProfissionalAvaliacao avaliacao);
}
