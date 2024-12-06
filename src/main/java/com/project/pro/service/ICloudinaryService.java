package com.project.pro.service;

import com.project.pro.model.dto.FileUploadDTO;

import java.util.Map;

public interface ICloudinaryService {

    Map upload(FileUploadDTO file, String folder);

}
