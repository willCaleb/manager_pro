package com.project.pro.service.impl;

import com.google.gson.Gson;
import com.project.pro.exception.CustomException;
import com.project.pro.model.entity.Endereco;
import com.project.pro.utils.CepUtil;
import org.springframework.http.HttpStatus;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CepService {
    static String webService = "http://viacep.com.br/ws/";

    public static Endereco buscaEnderecoPeloCep(String cep) throws Exception {
        String urlParaChamada = webService + cep + "/json";

        try {
            URL url = new URL(urlParaChamada);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

            if (conexao.getResponseCode() != HttpStatus.OK.value())
                throw new CustomException("HTTP error code : " + conexao.getResponseCode());

            BufferedReader resposta = new BufferedReader(new InputStreamReader((conexao.getInputStream())));
            String jsonEmString = CepUtil.converteJsonEmString(resposta);

            Gson gson = new Gson();
            Endereco endereco = gson.fromJson(jsonEmString, Endereco.class);

            return endereco;
        } catch (Exception e) {
            throw new CustomException("ERRO: " + e);
        }
    }
}
