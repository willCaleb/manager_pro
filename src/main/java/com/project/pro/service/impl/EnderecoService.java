package com.project.pro.service.impl;

import com.project.pro.exception.CustomException;
import com.project.pro.model.beans.GoogleMaps;
import com.project.pro.model.dto.EnderecoDTO;
import com.project.pro.model.entity.Endereco;
import com.project.pro.model.entity.Pessoa;
import com.project.pro.repository.EnderecoRepository;
import com.project.pro.service.IEnderecoService;
import com.project.pro.utils.DistanceUtil;
import com.project.pro.utils.ListUtils;
import com.project.pro.utils.Utils;
import com.project.pro.validator.ValidadorEndereco;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EnderecoService extends AbstractService<Endereco, EnderecoDTO, EnderecoRepository> implements IEnderecoService {

    private final EnderecoRepository enderecoRepository;

    private final GoogleMapsService googleMapsService;

    private final ValidadorEndereco validadorEndereco = new ValidadorEndereco();

    private final CepService cepService;

    private final DistanceUtil distanceUtil;

    @PostConstruct
    private void setValidadorEnderecoRepository() {
        validadorEndereco.setEnderecoRepository(enderecoRepository);
    }

    public Endereco incluir(Endereco endereco) throws Exception {

        validadorEndereco.validarInsert(endereco);

        Endereco endByCep = cepService.buscarEnderecoPorCep(endereco.getCep());
        endereco.setLogradouro(Utils.nvl(endByCep.getLogradouro(), "Sem endereço"));
        endereco.setBairro(Utils.nvl(endByCep.getBairro(), "Sem bairro"));
        endereco.setUf(endByCep.getUf());
        endereco.setLocalidade(endByCep.getLocalidade());

        GoogleMaps.Geometria geometria = googleMapsService.geolocatioFromAddress(endereco);

        endereco.setLatitude(geometria.getLatitude());
        endereco.setLongitude(geometria.getLongitude());
        validadorEndereco.validarEnderecoJaPertenceAPessoa(endereco);
        definirPrincipal(endereco);

        if (endereco.getPessoa().hasId()) {
            return enderecoRepository.save(endereco);
        }

        return endereco;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Endereco incluir(Endereco endereco, Pessoa pessoa) throws Exception {
        endereco.setPessoa(pessoa);
        return incluir(endereco);
    }

    @Override
    public List<Endereco> incluir(List<Endereco> enderecos, Pessoa pessoa) {

        List<Endereco> enderecosWithGeolocation = new ArrayList<>();

        enderecos.forEach(endereco -> {
            try {
                endereco.setPessoa(pessoa);
                enderecosWithGeolocation.add(incluir(endereco));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return enderecosWithGeolocation;
    }

    @Override
    public void incluirCoordenadas(Endereco endereco) {
        if (Utils.isNotEmpty(endereco.getCep())) {
            GoogleMaps.Geometria geometria = googleMapsService.geolocatioFromAddress(endereco);
            endereco.setLatitude(geometria.getLatitude());
            endereco.setLongitude(geometria.getLongitude());
            getRepository().save(endereco);
        } else {
            throw new CustomException("O endereço não possui CEP e não é possível localizar as coordenadas");
        }
    }

    @Override
    public List<Endereco> findAllWithoutCoordenate() {
        return enderecoRepository.findAllWithoutCoordenate();
    }

    @Override
    public double calcularDistancia(Integer idEndA, Integer idEndB) {

        Endereco enderecoA = findAndValidate(idEndA);
        Endereco enderecoB = findAndValidate(idEndB);

        return distanceUtil.calcularDistanciaKm(enderecoA, enderecoB);
    }

    @Override
    public Endereco findEnderecoPrincipal(Pessoa pessoa) {
        return enderecoRepository.findByPessoaAndPrincipalTrue(pessoa);
    }

    private void definirPrincipal(Endereco endereco) {
        Pessoa pessoa = endereco.getPessoa();
        List<Endereco> enderecos = enderecoRepository.findAllByPessoa(pessoa);
        if (ListUtils.isNullOrEmpty(enderecos)) {
            endereco.setPrincipal(Boolean.TRUE);
            return;
        }
        if (endereco.isPrincipal()) {
            enderecos.stream()
                    .filter(Endereco::isPrincipal)
                    .findFirst()
                    .ifPresent(end -> end.setPrincipal(Boolean.FALSE));
        }
        enderecoRepository.saveAll(enderecos);
    }

    @Scheduled(fixedDelay = 1000 * 3600 * 24)
    protected void atualizarEnderecos() {
        List<Endereco> enderecosSemCoordenada = findAllWithoutCoordenate();

        enderecosSemCoordenada.forEach(endereco -> {
            try {
                final Endereco enderecoByCep = cepService.buscarEnderecoPorCep(endereco.getCep());
                endereco.setLogradouro(Utils.nvl(enderecoByCep.getLogradouro(), "Sem endereço"));
                endereco.setBairro(Utils.nvl(enderecoByCep.getBairro(), "Sem Bairro"));
                endereco.setLocalidade(enderecoByCep.getLocalidade());
                endereco.setUf(enderecoByCep.getUf());
                GoogleMaps.Geometria geometria = googleMapsService.geolocatioFromAddress(endereco);
                if (Utils.isNotEmpty(geometria)) {
                    endereco.setLatitude(geometria.getLatitude());
                    endereco.setLongitude(geometria.getLongitude());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        getRepository().saveAll(enderecosSemCoordenada);
    }
}
