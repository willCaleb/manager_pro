package com.project.pro.utils;

import com.project.pro.exception.CustomException;
import com.project.pro.model.beans.GoogleMaps;
import com.project.pro.model.entity.Endereco;
import com.project.pro.service.ICepService;
import com.project.pro.service.IGoogleMapsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DistanceUtil {

    private final IGoogleMapsService googleMapsService;

    private final ICepService cepService;

    public double calcularDistanciaKm(Endereco enderecoA, Endereco enderecoB) {

        setLogradouroEBairro(enderecoA);
        setLogradouroEBairro(enderecoB);
        setCoordenadas(enderecoA);
        setCoordenadas(enderecoB);

        return calcularDistancia(enderecoA, enderecoB);
    }

    private void setLogradouroEBairro(Endereco endereco) {
        if (Utils.isEmpty(endereco.getLogradouro())) {
            try {
                Endereco enderecoByCep = cepService.buscarEnderecoPorCep(endereco.getCep());
                if (Utils.isNotEmpty(enderecoByCep)) {
                    endereco.setLogradouro(Utils.nvl(enderecoByCep.getLogradouro(), "Sem endereço"));
                    endereco.setBairro(Utils.nvl(enderecoByCep.getBairro(), "Sem bairro"));
                }
            } catch (Exception e) {
                throw new CustomException("Não foi possível encontrar o CEP ", endereco.getCep());
            }
        }
    }

    private void setCoordenadas(Endereco endereco) {
        if (Utils.isEmpty(endereco.getLatitude()) || Utils.isEmpty(endereco.getLongitude())) {

            if (!"Sem endereço".equals(endereco.getLogradouro())) {
                GoogleMaps.Geometria geometria = googleMapsService.geolocatioFromAddress(endereco);
                endereco.setLatitude(geometria.getLatitude());
                endereco.setLongitude(geometria.getLongitude());
            } else {
                throw new CustomException("Por favor atualize as informações de endereço");
            }
        }
    }

    private double calcularDistancia(Endereco enderecoA, Endereco enderecoB) {
        final double RAIO_TERRA = 6371.0;

        double lat1 = enderecoA.getLatitude();
        double lat2 = enderecoB.getLatitude();
        double lon1 = enderecoA.getLongitude();
        double lon2 = enderecoB.getLongitude();

        double radianosLat1 = Math.toRadians(lat1);
        double radianosLon1 = Math.toRadians(lon1);
        double radianosLat2 = Math.toRadians(lat2);
        double radianosLon2 = Math.toRadians(lon2);

        double deltaLat = radianosLat2 - radianosLat1;
        double deltaLon = radianosLon2 - radianosLon1;

        double a = Math.pow(Math.sin(deltaLat / 2), 2) + Math.cos(radianosLat1) * Math.cos(radianosLat2)
                * Math.pow(Math.sin(deltaLon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double distancia = RAIO_TERRA * c;
        return distancia;
    }
}
