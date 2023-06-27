package com.project.pro.service.impl;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.project.pro.exception.CustomException;
import com.project.pro.model.dto.ProfissionalDTO;
import com.project.pro.model.entity.Profissional;
import com.project.pro.model.entity.Servico;
import com.project.pro.model.entity.ServicoProfissional;
import com.project.pro.repository.ProfissionalRepository;
import com.project.pro.service.IPessoaService;
import com.project.pro.service.IProfissionalService;
import com.project.pro.service.IServicoProfissionalService;
import com.project.pro.service.IServicoService;
import com.project.pro.utils.NumericUtils;
import com.project.pro.utils.Utils;
import com.project.pro.validator.ValidadorProfissional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ProfissionalService extends AbstractService<Profissional, ProfissionalDTO, ProfissionalRepository> implements IProfissionalService{

    private final IPessoaService pessoaService;
    private final IServicoService servicoService;
    private final IServicoProfissionalService servicoProfissionalService;
    private final ProfissionalRepository profissionalRepository;
    private final ValidadorProfissional validadorProfissional = new ValidadorProfissional();

    private final FirebaseMessaging firebaseMessaging;

    @PostConstruct
    private void setProfissionalRepository() {
        validadorProfissional.setRepository(this.profissionalRepository);
    }

    @PostConstruct
    private void setPessoaService() {
        validadorProfissional.setPessoaService(pessoaService);
    }

    @Override
    public Profissional incluir(Profissional profissional) {

        validadorProfissional.validarInsert(profissional);

        return profissionalRepository.save(profissional);
    }

    @Override
    public void editar(Integer idProfissional, Profissional profissional) {

        profissional.setId(idProfissional);

        Message msg = Message.builder()
                .setTopic("topico")
                .putData("nome", "nome criado")
                .build();

        try {
            String id = firebaseMessaging.send(msg);
            System.out.println(id);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Profissional incluirServico(Integer idServico, Integer idProfissional) {

        Profissional profissional = findAndValidate(idProfissional);

        Servico servico = servicoService.findAndValidate(idServico);

        ServicoProfissional servicoJaCadastrado = servicoProfissionalService.findByProfissionalAndServico(profissional, servico);

        if(Utils.isNotEmpty(servicoJaCadastrado)) {
            throw new CustomException("O profissional " + profissional.getPessoa().getNome() + " já possui esse serviço");
        }

        ServicoProfissional servicoProfissional = new ServicoProfissional();

        servicoProfissional.setProfissional(profissional);

        servicoProfissional.setServico(servico);

        profissional.getServicos().add(servicoProfissional);

        return profissionalRepository.save(profissional);
    }


}
