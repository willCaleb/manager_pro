package com.project.pro.service.impl;

import com.project.pro.model.dto.ServicoProfissionalDTO;
import com.project.pro.model.entity.Profissional;
import com.project.pro.model.entity.Servico;
import com.project.pro.model.entity.ServicoProfissional;
import com.project.pro.repository.ServicoProfissionalRepository;
import com.project.pro.service.IServicoProfissionalService;
import com.project.pro.validator.ValidadorServicoProfissional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServicoProfissionalServiceImpl
        extends AbstractService<ServicoProfissional, ServicoProfissionalDTO, ServicoProfissionalRepository>
        implements IServicoProfissionalService{

    private final ServicoProfissionalRepository servicoProfissionalRepository;
    private final ValidadorServicoProfissional validadorServicoProfissional = new ValidadorServicoProfissional();

    @Override
    public ServicoProfissional incluir(ServicoProfissional servicoProfissional) {
        validadorServicoProfissional.validarInsert(servicoProfissional);
        return (ServicoProfissional) getRepository().save(servicoProfissional);
    }

    @Override
    public void excluir(Integer idServicoProfissional) {

        ServicoProfissional servicoProfissional = findAndValidate(idServicoProfissional);
        getRepository().delete(servicoProfissional);
    }

    @Override
    public ServicoProfissional findByProfissionalAndServico(Profissional profissional, Servico servico) {
        return servicoProfissionalRepository.findByProfissionalAndServico(profissional, servico);
    }
}
