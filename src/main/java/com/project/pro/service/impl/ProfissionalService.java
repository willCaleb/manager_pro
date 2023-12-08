package com.project.pro.service.impl;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.project.pro.exception.CustomException;
import com.project.pro.model.dto.ProfissionalDTO;
import com.project.pro.model.entity.Imagem;
import com.project.pro.model.entity.Profissional;
import com.project.pro.model.entity.Servico;
import com.project.pro.model.entity.ServicoProfissional;
import com.project.pro.repository.ProfissionalRepository;
import com.project.pro.service.*;
import com.project.pro.utils.Utils;
import com.project.pro.validator.ValidadorProfissional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfissionalService extends AbstractService<Profissional, ProfissionalDTO, ProfissionalRepository> implements IProfissionalService{

    private final IPessoaService pessoaService;
    private final IServicoService servicoService;
    private final IServicoProfissionalService servicoProfissionalService;
    private final ProfissionalRepository profissionalRepository;
    private final ImagemService imagemService;
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
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Profissional incluir(Profissional profissional) {

        validadorProfissional.validarInsert(profissional);

        profissional.setPessoa(pessoaService.incluir(profissional.getPessoa()));

        return profissionalRepository.save(profissional);
    }

    @Override
    @Cacheable("profissional_por_nome")
    public List<Profissional> findAllByNome(String nome) {
        return profissionalRepository.findAllByNome("%" + nome + "%");
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

    @Override
    public Imagem incluirImagem(MultipartFile file, Integer idProfissional) {

        Profissional profissional = findAndValidate(idProfissional);

        return imagemService.incluir(file, Profissional.class, profissional.getId());

    }

    @Override
    public List<Imagem> listarImagens(Integer idProfissional) {

        Profissional profissional = findAndValidate(idProfissional);

        return imagemService.findAllByEntity(profissional);
    }
}
