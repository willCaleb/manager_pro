package com.project.pro.service.impl;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.project.pro.enums.EnumCustomException;
import com.project.pro.exception.CustomException;
import com.project.pro.model.beans.ImgurDataBean;
import com.project.pro.model.beans.ImgurReturn;
import com.project.pro.model.entity.Profissional;
import com.project.pro.model.entity.ProfissionalImagem;
import com.project.pro.service.IImgurService;
import com.project.pro.service.ProfissionalImagemService;
import com.project.pro.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.SystemDefaultRoutePlanner;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.ProxySelector;

@Service
@RequiredArgsConstructor
public class ImgurService implements IImgurService {

    @Value("${imgur.client_id}")
    private String clientId;

    @Value("${imgur.refresh_token}")
    private String refreshToken;

    @Value("${imgur.client_secret}")
    private String clientSecret;

    @Value("${imgur.login_url}")
    private String loginUrl;

    @Value("${imgur.base_url_upload}")
    private String uploadUrl;

    @Value("${imgur.username}")
    private String username;

    private final CacheManager cacheManager;

    private final ProfissionalService profissionalService;
    private final ProfissionalImagemService profissionalImagemService;

    @Override
    public HttpResponse<String> generateToken() {

        Unirest.setTimeouts(0, 0);
        try {
            HttpResponse<String> response = Unirest.post(loginUrl)
                    .field("refresh_token", refreshToken)
                    .field("client_id", clientId)
                    .field("client_secret", clientSecret)
                    .field("grant_type", "refresh_token")
                    .asString();

            return response;
        } catch (UnirestException e) {
            throw new CustomException(EnumCustomException.IMGUR_IMPOSSIVEL_GERAR_TOKEN, e.getMessage());
        }
    }

    @Cacheable("coisa")
    public String qqr() {
        return "qualquer coisa";
    }

    @Cacheable("imgur_token")
    public String getToken() {
        ImgurDataBean imgurDataBean = new Gson().fromJson(generateToken().getBody(), ImgurDataBean.class);
        return imgurDataBean.getAccess_token();
    }

    public HttpResponse<String> getAccountBase() {

        Unirest.setTimeouts(0, 0);
        try {
            HttpResponse<String> response = Unirest.get("https://api.imgur.com/3/account/" + username)
                    .header("Authorization", "Client-ID " + clientId)
                    .asString();
            return response;
        } catch (UnirestException e) {
            throw new CustomException(EnumCustomException.IMGUR_IMPOSSIVEL_RECUPERAR_CONTA, e.getMessage());
        }
    }

    public ImgurReturn upload(MultipartFile file) {
        MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
        entityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

        entityBuilder.addPart("image", new FileBody(convertToFile(file)));

        ImgurReturn imgurReturn;

        HttpPost httpPost = new HttpPost(uploadUrl);
        httpPost.setHeader("Authorization", "Client-ID " + clientId);
        httpPost.setEntity(entityBuilder.build());

        CloseableHttpClient closeable = HttpClients.custom()
                .setRoutePlanner(new SystemDefaultRoutePlanner(ProxySelector.getDefault())).build();

        String responseString;
        try {
            org.apache.http.HttpResponse response = closeable.execute(httpPost);

            responseString = EntityUtils.toString(response.getEntity());
            imgurReturn = new Gson().fromJson(responseString, ImgurReturn.class);

            if (imgurReturn.isSuccess()) {
                ProfissionalImagem imagem = new ProfissionalImagem();
                imagem.setDataInclusao(DateUtils.getDate());
                Profissional profissional = profissionalService.findAndValidate(1);
                imagem.setProfissional(profissional);
                imagem.setUrl(imgurReturn.getData().getLink());
                profissionalImagemService.incluir(imagem);
            }
        } catch (IOException e) {
            throw new CustomException(EnumCustomException.IMGUR_IMPOSSIVEL_FAZER_UPLOAD, e.getMessage());
        }
        return imgurReturn;
    }

    private File convertToFile(MultipartFile multipartFile) {

        try {
            multipartFile = new MockMultipartFile("sourceFile.tmp", multipartFile.getBytes());
            File file = new File("src/main/resources/targetFile.tmp");
            multipartFile.transferTo(file);
            return file;
        } catch (IOException e) {
            throw new CustomException(EnumCustomException.IMGUR_IMPOSSIVEL_CONVERTER_ARQUIVO, e.getMessage());
        }
    }
}
