package com.project.pro.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.project.pro.enums.EnumCustomException;
import com.project.pro.exception.CustomException;
import com.project.pro.model.beans.ImgurDataBean;
import com.project.pro.model.beans.ImgurReturn;
import com.project.pro.model.beans.ImgurReturnList;
import com.project.pro.model.dto.FileUploadDTO;
import com.project.pro.service.IImgurService;
import com.project.pro.utils.Utils;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.SystemDefaultRoutePlanner;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.ProxySelector;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class ImgurService implements IImgurService {

    @Value("${imgur.client_id}")
    private String clientId;

    @Value("${imgur.refresh_token}")
    private String refreshToken;

    @Value("${imgur.client_secret}")
    private String clientSecret;

    @Value("${imgur.base_url}")
    private String baseUrl;

    @Value("${imgur.base_url_upload}")
    private String uploadUrl;

    @Value("${imgur.username}")
    private String username;

    private final CacheService cacheService;

    @Override
    public HttpResponse<String> generateToken() {

        Unirest.setTimeouts(0, 0);
        String loginUrl = baseUrl + "/oauth2/token";
        try {

            CaffeineCache cacheToken = cacheService.findCacheByName("imgur_token");
            if (Utils.isNotEmpty(cacheToken)) {
                if (Utils.isNotEmpty(cacheToken.get(0))) ;
            }

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

    @Cacheable("imgur_token")
    public String getToken() {
        ImgurDataBean imgurDataBean = new Gson().fromJson(generateToken().getBody(), ImgurDataBean.class);
        return imgurDataBean.getAccess_token();
    }

    @Override
    public ImgurReturnList listAllImages(String imgurUsername) {

        String url = baseUrl + "/3/account/" + imgurUsername + "/images";

        Unirest.setTimeouts(0, 0);

        try {

            ObjectMapper objectMapper = new ObjectMapper();

            String token = getToken();

            HttpResponse<String> request = Unirest.get(url).header("Authorization", "Bearer " + token).asString();

            return objectMapper.readValue(request.getRawBody(), ImgurReturnList.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new CustomException("Não foi possível obter as files do usuario {0}", username);
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

    public ImgurReturn upload(FileUploadDTO file) {
        try {
            MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
            entityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            entityBuilder.addPart("image", new FileBody(convertToFile(file)));

            ImgurReturn imgurReturn;

            HttpPost httpPost = getHttpPost(entityBuilder);

            CloseableHttpClient closeable = getCloseableHttpClient();

            String responseString;
            org.apache.http.HttpResponse response = closeable.execute(httpPost);

            responseString = EntityUtils.toString(response.getEntity());
            imgurReturn = new Gson().fromJson(responseString, ImgurReturn.class);
            return imgurReturn;

        } catch (IOException e) {
            throw new CustomException(EnumCustomException.IMGUR_IMPOSSIVEL_FAZER_UPLOAD, e.getMessage());
        }
    }

    private CloseableHttpClient getCloseableHttpClient() {
        return HttpClients.custom()
                .setRoutePlanner(new SystemDefaultRoutePlanner(ProxySelector.getDefault())).build();
    }

    private HttpPost getHttpPost(MultipartEntityBuilder entityBuilder) {
        HttpPost httpPost = new HttpPost(uploadUrl);
        httpPost.setHeader("Authorization", "Client-ID " + clientId);
        httpPost.setEntity(entityBuilder.build());
        return httpPost;
    }

    public File convertToFile(FileUploadDTO fileUploadDTO) throws IOException {
        byte[] fileBytes = fileUploadDTO.getBytes();

        MockMultipartFile multipartFile = new MockMultipartFile(
                "sourceFile.tmp",
                fileBytes
        );

        File file = new File("src/main/resources/targetFile.tmp");
        multipartFile.transferTo(file);
        return file;
    }

    @Override
    public Response delete(String hash) {
        try {

            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON, "{}");
            Request request = new Request.Builder()
                    .url(uploadUrl + "/" + hash)
                    .method("DELETE", body)
                    .addHeader("Authorization", "Bearer " + getToken())
                    .build();
            return client.newCall(request).execute();
        } catch (IOException e) {
            throw new CustomException(EnumCustomException.IMGUR_NAO_FOI_POSSIVEL_EXCUIR);
        }
    }
}
