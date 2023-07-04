package com.project.pro.service.impl;

import com.project.pro.exception.CustomException;
import com.project.pro.model.beans.GoogleMaps;
import com.project.pro.model.entity.Endereco;
import com.project.pro.repository.EnderecoRepository;
import com.project.pro.service.IEnderecoService;
import com.project.pro.utils.Utils;
import com.project.pro.validator.ValidadorEndereco;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class EnderecoService implements IEnderecoService {

    private final EnderecoRepository enderecoRepository;

    private final GoogleMapsService googleMapsService;

    private final ValidadorEndereco validadorEndereco = new ValidadorEndereco();

    private final CepService cepService;

    public Endereco incluir(Endereco endereco) throws Exception{

        validadorEndereco.validarInsert(endereco);

        Endereco endByCep = cepService.buscarEnderecoPorCep(endereco.getCep());
        endByCep.setNumero(endereco.getNumero());
        endByCep.setComplemento(endereco.getComplemento());

        GoogleMaps.Geometria geometria = googleMapsService.geolocatioFromAddress(endByCep);

        endByCep.setLatitude(geometria.getLatitude());
        endByCep.setLongitude(geometria.getLongitude());

        return endByCep;
    }

    @Override
    public List<Endereco> incluir(List<Endereco> enderecos) {

        List<Endereco> enderecosWithGeolocation = new ArrayList<>();

        enderecos.forEach(endereco -> {
            try {
                enderecosWithGeolocation.add(incluir(endereco));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return enderecosWithGeolocation;
    }
}
