package com.project.pro.service.impl;

import com.project.pro.model.beans.ImgurReturn;
import com.project.pro.model.dto.PessoaDTO;
import com.project.pro.model.entity.Endereco;
import com.project.pro.model.entity.Pessoa;
import com.project.pro.repository.PessoaRepository;
import com.project.pro.service.IEnderecoService;
import com.project.pro.service.IImgurService;
import com.project.pro.service.IPessoaService;
import com.project.pro.utils.PasswordUtils;
import com.project.pro.utils.Utils;
import com.project.pro.validator.ValidadorPessoa;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Calendar;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PessoaService extends AbstractService<Pessoa, PessoaDTO, PessoaRepository> implements IPessoaService {

    private final PessoaRepository pessoaRepository;

    private ValidadorPessoa validadorPessoa = new ValidadorPessoa();

    private final IEnderecoService enderecoService;

    private final IImgurService imgurService;

    @PostConstruct
    private void setValidadorRepository() {
        validadorPessoa.setPessoaRepository(pessoaRepository);
    }

    @Transactional
    public Pessoa incluir(Pessoa pessoa) {
        pessoa.setDataInclusao(Calendar.getInstance().getTime());
        String salt = PasswordUtils.getSalt(30);
//        pessoa.setSenha(PasswordUtils.generateSecurePassword(pessoa.getSenha(), salt));

        incluirImagemPerfil(pessoa);

        validadorPessoa.validarInsert(pessoa);

        pessoaRepository.save(pessoa);

        pessoa.setEnderecos(enderecoService.incluir(pessoa.getEnderecos(), pessoa));

        return pessoa;
    }

    private void incluirImagemPerfil(Pessoa pessoa) {
        if (Utils.isNotEmpty(pessoa.getImagemPerfil())) {
            ImgurReturn IReturn = imgurService.upload(pessoa.getFile());
            pessoa.setImagemPerfil(IReturn.getData().getLink());
        }
    }

    @Transactional
    @Modifying
    public void excluir(Integer idPessoa) {
        Pessoa pessoa = pessoaRepository.getById(idPessoa);

        pessoaRepository.delete(pessoa);
    }

}
