package com.project.pro.service.impl;

import com.project.pro.exception.CustomException;
import com.project.pro.model.beans.GoogleMaps;
import com.project.pro.model.entity.Endereco;
import com.project.pro.service.IGoogleMapsService;
import com.project.pro.utils.ListUtils;
import com.project.pro.utils.StringUtil;
import com.project.pro.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GoogleMapsService implements IGoogleMapsService{

    @Value("${google.maps.url}")
    private String apiUrl;

    @Value("${google.maps.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    @Override
    public GoogleMaps.Geometria geolocatioFromAddress(Endereco endereco) {

        String geocode = enderecoToGeocode(endereco);

        final URI googleUri = getPathAddress(apiUrl, apiKey, geocode);

        final ResponseEntity<Endereco> responseEntity = restTemplate.getForEntity(googleUri, Endereco.class);

        if (isNotNullOrNotEmpty(responseEntity)) {

            final List<GoogleMaps> maps = responseEntity.getBody().getGoogleMaps();

            final GoogleMaps map = maps
                    .stream()
                    .findFirst()
                    .orElse(null);
            return map.getGeometria();
        }

        throw new CustomException("Não foi possível encontrar coordenadas");
    }

    private String enderecoToGeocode(Endereco endereco) {
        StringBuilder geocode = new StringBuilder();
       return geocode
                .append(endereco.getLogradouro().replaceAll(" ", "+"))
                .append("+")
                .append(endereco.getNumero())
                .append("+")
                .append(endereco.getLocalidade().replaceAll(" ", "+") + ",+")
                .append(endereco.getUf())
                .toString();
    }

    private URI getPathAddress(String path, String... arguments){
        return URI.create(StringUtil.formatMessage(path, arguments));
    }

    private boolean isNotNullOrNotEmpty(ResponseEntity<Endereco> entity) {
        return HttpStatus.OK.equals(entity.getStatusCode()) ||
                Utils.isNotEmpty(entity.getBody()) ||
                ListUtils.isNotNullOrEmpty(entity.getBody().getGoogleMaps());
    }
}
