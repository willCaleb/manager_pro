package com.project.pro.service.impl;

import com.project.pro.config.security.JwtTokenProvider;
import com.project.pro.enums.EnumCustomException;
import com.project.pro.enums.Role;
import com.project.pro.exception.CustomException;
import com.project.pro.model.beans.AgendaBean;
import com.project.pro.model.beans.IncluirProfissionalBean;
import com.project.pro.model.beans.JwtAuthenticationResponse;
import com.project.pro.model.beans.LoginRequest;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;


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
    public Profissional incluir(IncluirProfissionalBean profissionalBean) {

        Profissional profissional = new Profissional();

        profissional.setCpf(profissionalBean.getCpfCnpj());
        profissional.setEmail(profissionalBean.getEmail());
        profissional.setMultiploAgendamento(Boolean.FALSE);

        Usuario usuario = usuarioService.findByUsername(profissionalBean.getEmail());

        if (Utils.isNotEmpty(usuario)) {
            profissional.setUsuario(usuario);
        }else {
            Usuario newUsuario = new Usuario();
            newUsuario.setUsername(profissionalBean.getEmail().toLowerCase());
            newUsuario.setPassword(passwordEncoder.encode(profissionalBean.getSenha()));
            Usuario incluir = usuarioService.incluir(newUsuario);
            profissional.setUsuario(incluir);
        }

        validadorProfissional.validarInsert(profissional);

//        enviarEmail(profissional);

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

    @Override
    public List<AgendaBean> listarAgendas(Integer idProfissional) {
        return null;
    }

    @Override
    public ResponseEntity<?> login(LoginRequest loginRequest) {

        Profissional profissional = buscarPorUsuario(loginRequest.getUsername());
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername().toLowerCase(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = tokenProvider.generateToken(authentication, Role.PROFISSIONAL);
            return ResponseEntity.ok(new JwtAuthenticationResponse(token));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
        }
    }

    private Profissional buscarPorUsuario(String username) {
        Usuario usuario = usuarioService.findByUsername(username);

        if (Utils.isEmpty(usuario)) {
            throw new CustomException(EnumCustomException.USUARIO_NAO_ENCONTRADO);
        }
        return profissionalRepository.findByUsuario(usuario).orElseThrow(() -> new CustomException(EnumCustomException.PROFISSIONAL_NAO_ENCONTRADO));
    }
}
