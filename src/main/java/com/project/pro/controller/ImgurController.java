package com.project.pro.controller;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.project.pro.component.ImgurTokenSession;
import com.project.pro.model.beans.ImgurDataBean;
import com.project.pro.model.beans.ImgurReturn;
import com.project.pro.model.beans.ImgurReturnList;
import com.project.pro.service.IImgurService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.project.pro.model.dto.FileUploadDTO;

@RestController
@RequestMapping("/imagem")
@RequiredArgsConstructor
public class ImgurController {


    private final IImgurService imgurService;

    @GetMapping("/token")
    public Object gerarToken() {
        HttpResponse<String> stringHttpResponse = imgurService.generateToken();
        ImgurDataBean imgurDataBean = new Gson().fromJson(stringHttpResponse.getBody(), ImgurDataBean.class);

        ImgurTokenSession.setImgurToken(imgurDataBean.getAccess_token());
        return imgurDataBean;
    }

    @GetMapping("/account")
    public ImgurReturn getAccountBase(){
        HttpResponse<String> accountBase = imgurService.getAccountBase();

        ImgurReturn accountBean = new Gson().fromJson(accountBase.getBody(), ImgurReturn.class);
        return accountBean;
    }

    @PostMapping
    public ImgurReturn sendImage(@RequestBody FileUploadDTO file) {
        return imgurService.upload(file);
    }

    @GetMapping("/teste")
    public ResponseEntity<String> getToken() {
        return ResponseEntity.ok(imgurService.getToken());
    }

    @GetMapping("account-images/{username}")
    public ImgurReturnList getAllAccountImages(@PathVariable("username") String username){
        return imgurService.listAllImages(username);
    }

    @DeleteMapping("/{imgHash}")
    public void excluirImagem(@PathVariable("imgHash") String hash){
        imgurService.delete(hash);
    }

}
