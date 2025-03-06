package com.project.pro.service.impl;

import com.project.pro.model.dto.ProfissionalAvaliacaoDTO;
import com.project.pro.model.entity.Cliente;
import com.project.pro.model.entity.Imagem;
import com.project.pro.model.entity.Profissional;
import com.project.pro.model.entity.ProfissionalAvaliacao;
import com.project.pro.repository.ProfissionalAvaliacaoRepository;
import com.project.pro.service.IClienteService;
import com.project.pro.service.IImagemService;
import com.project.pro.service.IProfissionalAvaliacaoService;
import com.project.pro.service.IProfissionalService;
import com.project.pro.utils.DateUtils;
import com.project.pro.utils.ListUtils;
import com.project.pro.utils.Utils;
import com.project.pro.validator.ValidadorProfissionalAvaliacao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfissionalAvaliacaoService
        extends AbstractService<ProfissionalAvaliacao, ProfissionalAvaliacaoDTO, ProfissionalAvaliacaoRepository>
        implements IProfissionalAvaliacaoService {

    private final ProfissionalAvaliacaoRepository profissionalAvaliacaoRepository;

    private final IProfissionalService profissionalService;

    private final IImagemService imagemService;

    private final ValidadorProfissionalAvaliacao validadorProfissionalAvaliacao = new ValidadorProfissionalAvaliacao();

    @PostConstruct
    private void setProfissionalAvaliacaoRepository() {
        validadorProfissionalAvaliacao.setProfissionalAvaliacaoRepository(profissionalAvaliacaoRepository);
    }

    @Override
    public ProfissionalAvaliacao incluir(Integer idProfissional, ProfissionalAvaliacao avaliacao) {

        Profissional profissionalManaged = profissionalService.findAndValidate(idProfissional);

        Cliente cliente = getCliente();

        avaliacao.setProfissional(profissionalManaged);

        avaliacao.setCliente(cliente);

        validadorProfissionalAvaliacao.validarInsert(avaliacao);

        avaliacao.setDataInclusao(DateUtils.getDate());

        ProfissionalAvaliacao avaliacaoSalva = save(avaliacao);

        incluirImagens(avaliacaoSalva);

        return avaliacaoSalva;
    }

    @Override
    public void editar(Integer idAvaliacao, ProfissionalAvaliacao avaliacao) {
        ProfissionalAvaliacao avaliacaoManaged = findAndValidate(idAvaliacao);

        avaliacaoManaged.setNota(avaliacao.getNota());
        avaliacaoManaged.setEditado(Boolean.TRUE);
        avaliacaoManaged.setObservacao(Utils.nvl(avaliacao.getObservacao(), avaliacaoManaged.getObservacao()));
        avaliacaoManaged.setDataAlteracao(DateUtils.getDate());

        incluirImagens(avaliacao);

        profissionalAvaliacaoRepository.save(avaliacaoManaged);
    }

    @Override
    public void excluir(Integer idAvaliacao) {
        ProfissionalAvaliacao avaliacaoManaged = findAndValidate(idAvaliacao);

        List<Imagem> imagens = imagemService.findAllByEntity(avaliacaoManaged);

        if(ListUtils.isNotNullOrEmpty(imagens)) {
            imagens.forEach(imagem -> imagemService.excluir(imagem.getId(), true));
        }

        profissionalAvaliacaoRepository.delete(avaliacaoManaged);
    }

    private void incluirImagens(ProfissionalAvaliacao avaliacao) {
        if (ListUtils.isNotNullOrEmpty(avaliacao.getFiles())) {
            avaliacao.getFiles().forEach(imagem -> imagemService.incluirImgur(imagem, ProfissionalAvaliacao.class, avaliacao.getId()));
        }
    }

    @Override
    public void excluirImagem(Integer idImagem) {
        imagemService.excluir(idImagem);
    }

    @Override
    public List<Imagem> findAllImagens(ProfissionalAvaliacao avaliacao) {
        return imagemService.findAllByEntity(avaliacao);
    }
}
