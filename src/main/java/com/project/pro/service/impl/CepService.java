package com.project.pro.service.impl;

import com.google.gson.Gson;
import com.project.pro.exception.CustomException;
import com.project.pro.model.beans.EnderecoByCepBean;
import com.project.pro.model.entity.Endereco;
import com.project.pro.service.ICepService;
import com.project.pro.utils.CepUtil;
import com.project.pro.utils.JsonUtil;
import com.project.pro.utils.Utils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class CepService implements ICepService{
    private static final String WEB_SERVICE = "http://viacep.com.br/ws/";

    public Endereco buscarEnderecoPorCep(String cep) throws Exception {
        String urlParaChamada = WEB_SERVICE + cep + "/json";

        cep = cep.replaceAll("-", "");

        try {
            URL url = new URL(urlParaChamada);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

            if (!Utils.equals(HttpStatus.OK.value(), conexao.getResponseCode()))
                throw new CustomException("HTTP error code : " + conexao.getResponseCode());

            BufferedReader resposta = new BufferedReader(new InputStreamReader((conexao.getInputStream())));
            String jsonEmString = JsonUtil.converteJsonEmString(resposta);

            Gson gson = new Gson();
            EnderecoByCepBean enderecoByCep = gson.fromJson(jsonEmString, EnderecoByCepBean.class);

            return getEndereco(cep, enderecoByCep);
        } catch (Exception e) {
            throw new CustomException("ERRO: " + e);
        }
    }

    private Endereco getEndereco(String cep, EnderecoByCepBean enderecoByCep) {
        Endereco endereco = new Endereco();

        endereco.setBairro(enderecoByCep.getBairro());
        endereco.setCep(cep);
        endereco.setLocalidade(enderecoByCep.getLocalidade());
        endereco.setLogradouro(enderecoByCep.getLogradouro());
        endereco.setUf(enderecoByCep.getUf());
        return endereco;
    }
}
