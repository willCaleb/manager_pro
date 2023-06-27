package com.project.pro.service;

import com.project.pro.model.dto.ServicoDTO;
import com.project.pro.model.entity.Servico;
import com.project.pro.repository.ServicoRepository;

public interface IServicoService extends IAbstractService<Servico, ServicoDTO, ServicoRepository>{

     Servico incluir(Servico servico);

     void editar(Integer idServico, Servico servico);
}
