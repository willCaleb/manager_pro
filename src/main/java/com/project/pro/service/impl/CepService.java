package com.project.pro.service.impl;

import com.google.gson.Gson;
import com.project.pro.exception.CustomException;
import com.project.pro.model.beans.EnderecoByCepBean;
import com.project.pro.model.entity.Endereco;
import com.project.pro.service.ICepService;
import com.project.pro.utils.CepUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class CepService implements ICepService{
    static String webService = "http://viacep.com.br/ws/";

    public Endereco buscarEnderecoPorCep(String cep) throws Exception {
        String urlParaChamada = webService + cep + "/json";

        cep = cep.replaceAll("-", "");

        try {
            URL url = new URL(urlParaChamada);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

            if (conexao.getResponseCode() != HttpStatus.OK.value())
                throw new CustomException("HTTP error code : " + conexao.getResponseCode());

            BufferedReader resposta = new BufferedReader(new InputStreamReader((conexao.getInputStream())));
            String jsonEmString = CepUtil.converteJsonEmString(resposta);

            Gson gson = new Gson();
            EnderecoByCepBean enderecoByCep = gson.fromJson(jsonEmString, EnderecoByCepBean.class);

            Endereco endereco = new Endereco();

            endereco.setBairro(enderecoByCep.getBairro());
            endereco.setCep(cep);
            endereco.setLocalidade(enderecoByCep.getLocalidade());
            endereco.setLogradouro(enderecoByCep.getLogradouro());
            endereco.setUf(enderecoByCep.getUf());

            return endereco;
        } catch (Exception e) {
            throw new CustomException("ERRO: " + e);
        }
    }
}
