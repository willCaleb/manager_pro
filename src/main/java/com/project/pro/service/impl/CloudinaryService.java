package com.project.pro.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.project.pro.exception.CustomException;
import com.project.pro.model.dto.FileUploadDTO;
import com.project.pro.service.ICloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService implements ICloudinaryService {

    private final Cloudinary cloudinary;

    @Override
    public Map upload(FileUploadDTO file, String folder) {
        try {
            byte[] decodedImage = Base64.getDecoder().decode(file.getContent());

            return cloudinary.uploader().upload(decodedImage, ObjectUtils.asMap("folder", folder));

        }catch (Exception ex) {
            throw new CustomException(ex.getMessage());
        }
    }
}
