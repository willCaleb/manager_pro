package com.project.pro.service.impl;

import com.project.pro.enums.EnumEtapaServico;
import com.project.pro.model.dto.ServicoDTO;
import com.project.pro.model.entity.Servico;
import com.project.pro.repository.ServicoRepository;
import com.project.pro.service.IServicoService;
import com.project.pro.utils.DateUtils;
import com.project.pro.utils.Utils;
import com.project.pro.validator.ValidadorServico;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServicoService extends AbstractService<Servico, ServicoDTO, ServicoRepository> implements IServicoService {


    private final ValidadorServico validadorServico = new ValidadorServico();

    @Override
    public Servico incluir(Servico servico) {
        onPrepareInsert(servico);
        return getRepository().save(servico);
    }

    @Override
    public void editar(Integer idProduto, Servico servico) {

        Servico servicoManaged = findAndValidate(idProduto);

        if (!Utils.equals(servicoManaged.getCategoria().getCategoriaPrincipal(), servico.getCategoria().getCategoriaPrincipal())) {
            return;
        }
        servico.setDataAlteracao(DateUtils.getDate());
        servico.setId(idProduto);

        getRepository().save(servico);
    }

    private void onPrepareInsert(Servico servico) {
        servico.setDataInclusao(DateUtils.getDate());
        servico.setEtapaServico(EnumEtapaServico.AGUARDANDO_CONFIRMACAO);
        validadorServico.validarInsert(servico);
    }
}
