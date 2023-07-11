package com.project.pro.service.impl;

import com.project.pro.model.beans.GoogleMaps;
import com.project.pro.model.entity.Endereco;
import com.project.pro.model.entity.Pessoa;
import com.project.pro.repository.EnderecoRepository;
import com.project.pro.service.IEnderecoService;
import com.project.pro.validator.ValidadorEndereco;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EnderecoService implements IEnderecoService {

    private final EnderecoRepository enderecoRepository;

    private final GoogleMapsService googleMapsService;

    private final ValidadorEndereco validadorEndereco = new ValidadorEndereco();

    private final CepService cepService;

    public Endereco incluir(Endereco endereco) throws Exception{

        validadorEndereco.validarInsert(endereco);

        Endereco endByCep = cepService.buscarEnderecoPorCep(endereco.getCep());
        endereco.setLogradouro(endByCep.getLogradouro());
        endereco.setBairro(endByCep.getBairro());
        endereco.setUf(endByCep.getUf());
        endereco.setLocalidade(endByCep.getLocalidade());

        GoogleMaps.Geometria geometria = googleMapsService.geolocatioFromAddress(endereco);

        endereco.setLatitude(geometria.getLatitude());
        endereco.setLongitude(geometria.getLongitude());

        return endereco;
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
}
