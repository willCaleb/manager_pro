package com.project.pro.service.impl;

import com.project.pro.model.dto.PessoaDTO;
import com.project.pro.model.entity.Pessoa;
import com.project.pro.repository.PessoaRepository;
import com.project.pro.service.IPessoaService;
import com.project.pro.utils.PasswordUtils;
import com.project.pro.validator.ValidadorPessoa;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Calendar;

@Service
@RequiredArgsConstructor
public class PessoaService extends AbstractService<Pessoa, PessoaDTO, PessoaRepository> implements IPessoaService {

    private final PessoaRepository pessoaRepository;

    private ValidadorPessoa validadorPessoa = new ValidadorPessoa();

    private final EnderecoService enderecoService;

    @PostConstruct
    private void setValidadorRepository() {
        validadorPessoa.setPessoaRepository(pessoaRepository);
    }

    public Pessoa incluir(Pessoa pessoa) {
        pessoa.setDataInclusao(Calendar.getInstance().getTime());
        String salt = PasswordUtils.getSalt(30);
//        pessoa.setSenha(PasswordUtils.generateSecurePassword(pessoa.getSenha(), salt));

        pessoa.setEnderecos(enderecoService.incluir(pessoa.getEnderecos()));

        validadorPessoa.validarInsert(pessoa);

        return pessoaRepository.save(pessoa);
    }

    @Transactional
    @Modifying
    public void excluir(Integer idPessoa) {
        Pessoa pessoa = pessoaRepository.getById(idPessoa);

        pessoaRepository.delete(pessoa);
    }

}
