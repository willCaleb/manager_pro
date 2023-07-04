package com.project.pro.model.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Map;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoogleMaps implements Serializable {

    @JsonProperty("geometry")
    private Geometria geometria;

    @Data
    public class Geometria implements Serializable {

        private Double longitude;

        private Double latitude;

        @JsonProperty("location")
        public void setLatLnt(Map<String, Double> toMap) {
            this.latitude = toMap.get("lat");
            this.longitude = toMap.get("lng");
        }

    }
}