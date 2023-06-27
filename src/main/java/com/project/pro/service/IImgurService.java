package com.project.pro.service;

import com.mashape.unirest.http.HttpResponse;
import com.project.pro.model.beans.ImgurReturn;
import org.springframework.web.multipart.MultipartFile;

public interface IImgurService {

    HttpResponse<String> generateToken();

    HttpResponse<String> getAccountBase();

    ImgurReturn upload(MultipartFile file);

    String getToken();

    String qqr();

}
