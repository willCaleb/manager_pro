package com.project.pro.service.impl;

import com.project.pro.exception.CustomException;
import com.project.pro.model.dto.FileUploadDTO;
import com.project.pro.model.dto.ProfissionalDTO;
import com.project.pro.model.entity.*;
import com.project.pro.repository.ProfissionalRepository;
import com.project.pro.service.*;
import com.project.pro.utils.DateUtils;
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
    private final IImagemService imagemService;
    private final IEmailService emailService;
    private final ValidadorProfissional validadorProfissional = new ValidadorProfissional();


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

        enviarEmail(profissional);

        return profissionalRepository.save(profissional);
    }

    private void enviarEmail(Profissional profissional) {
        SendEmail sendEmail = new SendEmail();
        sendEmail.setFrom("pro@gmail.com");
        sendEmail.setSubject("Bem vindo ao ProApp");
        sendEmail.setTo("wilsonperepelecia@gmail.com");
        sendEmail.setText(getEmailText(profissional));
        sendEmail.setSendDate(DateUtils.getDate());

        emailService.sendEmail(sendEmail);
    }

    private String getEmailText(Profissional profissional) {

        StringBuilder builder = new StringBuilder();
        builder.append("Seja bem vindo(a) ")
                .append(profissional.getPessoa().getNome());

        return builder.toString();
    }

    @Override
    @Cacheable("profissional_por_nome")
    public List<Profissional> findAllByNome(String nome) {
        return profissionalRepository.findAllByNome("%" + nome + "%");
    }

    @Override
    public void editar(Integer idProfissional, Profissional profissional) {

        Profissional profissionalManaged = findAndValidate(idProfissional);

        profissional.setId(idProfissional);

        validadorProfissional.validarCamposObrigatorios(profissional);

        if (!Utils.equals(profissionalManaged.getCpf(), profissional.getCpf())) {
            validadorProfissional.validarCpfJaCadastrado(profissional);
        }

        profissionalRepository.save(profissional);

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
    public Imagem incluirImagem(FileUploadDTO file, Integer idProfissional) {

        Profissional profissional = findAndValidate(idProfissional);

        return imagemService.incluir(file, Profissional.class, profissional.getId());

    }

    @Override
    public List<Imagem> listarImagens(Integer idProfissional) {

        Profissional profissional = findAndValidate(idProfissional);

        return imagemService.findAllByEntity(profissional);
    }
}
