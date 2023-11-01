package com.project.pro.service.impl;

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
import com.project.pro.validator.ValidadorEndereco;
import com.project.pro.validator.ValidadorPessoa;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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

    private ValidadorEndereco validadorEndereco = new ValidadorEndereco();

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

        resolverEnderecoPrincipal(pessoa);

        pessoa.setEnderecos(enderecoService.incluir(enderecos, pessoa));

        return pessoa;
    }

    private void resolverEnderecoPrincipal(Pessoa pessoa) {
        if (ListUtils.isNotNullOrEmpty(pessoa.getEnderecos())) {
            validadorEndereco.validarUnicoPrincipal(pessoa.getEnderecos());
            if (NumericUtils.isSmaller(getEnderecoPrincipais(pessoa).size(), 1)) {
                definirPrimeiroEnderecoPrincipal(pessoa.getEnderecos());
            }
        }
    }

    private List<Endereco> getEnderecoPrincipais(Pessoa pessoa) {
        return pessoa.getEnderecos()
                .stream()
                .filter(Endereco::isPrincipal)
                .collect(Collectors.toList());
    }

    private void definirPrimeiroEnderecoPrincipal(List<Endereco> enderecos) {
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

    @Override
    public Endereco getEnderecoPrincipal(Pessoa pessoa) {
        return pessoa.getEnderecos()
                .stream()
                .filter(Endereco::isPrincipal)
                .findFirst()
                .orElse(
                        pessoa.getEnderecos()
                                .stream()
                                .findFirst()
                                .orElse(null));
    }

    @Override
    public Pessoa incluirImagem(Integer idPessoa, MultipartFile file) {

        Pessoa pessoa = findAndValidate(idPessoa);

        ImgurReturn imgurReturn = imgurService.upload(file);
        String link = imgurReturn.getData().getLink();
        pessoa.setImagemPerfil(link);
        return pessoaRepository.save(pessoa);
    }

}
