package com.project.pro.service.impl;

import com.project.pro.model.dto.ProfissionalImagemDTO;
import com.project.pro.model.entity.ProfissionalImagem;
import com.project.pro.repository.ProfissionalImagemRepository;
import com.project.pro.service.ProfissionalImagemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfissionalImagemServiceImpl extends AbstractService<ProfissionalImagem, ProfissionalImagemDTO, ProfissionalImagemRepository>
        implements ProfissionalImagemService{

    private final ProfissionalImagemRepository profissionalImagemRepository;

    @Override
    public ProfissionalImagem incluir(ProfissionalImagem imagem) {
        return profissionalImagemRepository.save(imagem);
    }
}
