package com.project.pro.service;

import com.mashape.unirest.http.HttpResponse;
import com.project.pro.model.beans.ImgurReturn;
import com.project.pro.model.beans.ImgurReturnList;
import com.project.pro.model.dto.FileUploadDTO;
import com.project.pro.model.entity.Profissional;
import okhttp3.Response;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImgurService {

    HttpResponse<String> generateToken();

    HttpResponse<String> getAccountBase();

    ImgurReturn upload(FileUploadDTO file);

    String getToken();

    ImgurReturnList listAllImages(String imgurUsername);

    Response delete(String hash);

}
