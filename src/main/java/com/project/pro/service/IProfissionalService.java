package com.project.pro.service;

import com.project.pro.model.beans.AgendaBean;
import com.project.pro.model.beans.IncluirProfissionalBean;
import com.project.pro.model.beans.LoginRequest;
import com.project.pro.model.dto.FileUploadDTO;
import com.project.pro.model.dto.ProfissionalDTO;
import com.project.pro.model.entity.Imagem;
import com.project.pro.model.entity.Profissional;
import com.project.pro.repository.ProfissionalRepository;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface IProfissionalService extends IAbstractService<Profissional, ProfissionalDTO, ProfissionalRepository>{

    Profissional incluir(IncluirProfissionalBean profissionalBean);

    void editar(Integer idProfissional, Profissional profissional);

    Profissional incluirServico(Integer idServico, Integer idProfissional);

    List<Profissional> findAllByNome(String nome);

    Imagem incluirImagem(FileUploadDTO file, Integer idProfissional);

    List<Imagem> listarImagens(Integer idProfissional);

    List<AgendaBean> listarAgendas(Integer idProfissional);

    ResponseEntity<?> login(LoginRequest loginRequest);
}
