package com.project.pro.service.impl;

import com.project.pro.exception.CustomException;
import com.project.pro.model.beans.ImgurReturn;
import com.project.pro.model.dto.PessoaDTO;
import com.project.pro.model.entity.Endereco;
import com.project.pro.model.entity.Pessoa;
import com.project.pro.repository.PessoaRepository;
import com.project.pro.service.IEnderecoService;
import com.project.pro.service.IImgurService;
import com.project.pro.service.IPessoaService;
import com.project.pro.utils.ListUtils;
import com.project.pro.utils.NumericUtils;
import com.project.pro.utils.PasswordUtils;
import com.project.pro.utils.Utils;
import com.project.pro.validator.ValidadorPessoa;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Pessoa incluir(Pessoa pessoa) {
        pessoa.setDataInclusao(Calendar.getInstance().getTime());
        String salt = PasswordUtils.getSalt(30);
//        pessoa.setSenha(PasswordUtils.generateSecurePassword(pessoa.getSenha(), salt));

        incluirImagemPerfil(pessoa);

        validadorPessoa.validarInsert(pessoa);

        List<Endereco> enderecos = pessoa.getEnderecos();

        pessoa.setEnderecos(new ArrayList<>());

        pessoaRepository.save(pessoa);

        resolverPrincipal(pessoa);

        pessoa.setEnderecos(enderecoService.incluir(enderecos, pessoa));

        return pessoa;
    }

    private void resolverPrincipal(Pessoa pessoa) {
        if (ListUtils.isNotNullOrEmpty(pessoa.getEnderecos())) {

            if (NumericUtils.isGreater(getPrincipais(pessoa).size(), 1)) {
                throw new CustomException("Apenas um endereço deve ser marcado como principal.");
            }
            if (NumericUtils.isSmaller(getPrincipais(pessoa).size(), 1)) {
                definirPrimeiroPrincipal(pessoa.getEnderecos());
            }
        }
    }

    private List<Endereco> getPrincipais(Pessoa pessoa) {
        return pessoa.getEnderecos()
                .stream()
                .filter(Endereco::isPrincipal)
                .collect(Collectors.toList());
    }

    private void definirPrimeiroPrincipal(List<Endereco> enderecos) {
        enderecos.stream()
                .findFirst()
                .ifPresent(endereco -> endereco.setPrincipal(Boolean.TRUE));
    }

    @Override
    public List<Pessoa> findAll() {
        return pessoaRepository.findAll();
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
