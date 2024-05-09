package com.project.pro.service;

import com.project.pro.model.dto.FileUploadDTO;
import com.project.pro.model.dto.ProfissionalDTO;
import com.project.pro.model.entity.Imagem;
import com.project.pro.model.entity.Profissional;
import com.project.pro.repository.ProfissionalRepository;

import java.util.List;

public interface IProfissionalService extends IAbstractService<Profissional, ProfissionalDTO, ProfissionalRepository>{

    Profissional incluir(Profissional profissional);

    void editar(Integer idProfissional, Profissional profissional);

    Profissional incluirServico(Integer idServico, Integer idProfissional);

    List<Profissional> findAllByNome(String nome);

    Imagem incluirImagem(FileUploadDTO file, Integer idProfissional);

    List<Imagem> listarImagens(Integer idProfissional);
}
