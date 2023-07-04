package com.project.pro.service;

import com.project.pro.model.beans.GoogleMaps;
import com.project.pro.model.entity.Endereco;

public interface IGoogleMapsService {

    GoogleMaps.Geometria geolocatioFromAddress(Endereco endereco);

}
