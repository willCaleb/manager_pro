package com.project.pro.service.impl;

import com.cloudinary.Cloudinary;
import com.project.pro.enums.EnumCustomException;
import com.project.pro.exception.CustomException;
import com.project.pro.model.beans.Data;
import com.project.pro.model.beans.ImgurReturn;
import com.project.pro.model.dto.FileUploadDTO;
import com.project.pro.model.dto.ImagemDTO;
import com.project.pro.model.entity.AbstractEntity;
import com.project.pro.model.entity.Imagem;
import com.project.pro.model.entity.Profissional;
import com.project.pro.pattern.Constants;
import com.project.pro.repository.ImagemRepository;
import com.project.pro.service.ICloudinaryService;
import com.project.pro.service.IImgurService;
import com.project.pro.service.IImagemService;
import com.project.pro.utils.DateUtils;
import com.project.pro.utils.StringUtil;
import com.project.pro.utils.Utils;
import lombok.RequiredArgsConstructor;
import okhttp3.Response;
import okio.Path;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ImagemServiceImpl extends AbstractService<Imagem, ImagemDTO, ImagemRepository> implements IImagemService {

    private final IImgurService imgurService;

    private final ImagemRepository imagemRepository;

    private final ICloudinaryService cloudinaryService;



    @Override
    public Imagem incluirImgur(FileUploadDTO file, Class<?> clazz, Integer idEntidade) {

        ImgurReturn upload = imgurService.upload(file);

        if (Utils.isNotEmpty(upload)) {
            Imagem imagem = new Imagem();

            Data data = upload.getData();

            imagem.setImgLink(data.getLink());
            imagem.setAtivo(Boolean.TRUE);
            imagem.setDataInclusao(DateUtils.getDate());
            imagem.setFilesize(StringUtil.toString(data.getSize()));
            imagem.setEntityId(idEntidade);
            imagem.setEntityName(clazz.getSimpleName());
            imagem.setType(data.getType());
            imagem.setDeleteHash(data.getDeletehash());
            imagem.setIdCloud(data.getId());
            imagem.setFilename(file.getFilename());
            imagem.setCloud(Constants.IMGUR_CLOUD);

            return save(imagem);
        }
        throw new CustomException(EnumCustomException.IMGUR_IMPOSSIVEL_FAZER_UPLOAD);
    }

    @Override
    public Imagem incluirCloudinary(FileUploadDTO file, Class<?> clazz, Integer id) {

        String separator = Path.DIRECTORY_SEPARATOR;

        String folder = separator + clazz.getSimpleName() + separator + id + separator;

        Map upload = cloudinaryService.upload(file, folder);

        Imagem imagem = new Imagem();

        imagem.setIdCloud((String)upload.get("public_id"));
        imagem.setDeleted(Boolean.FALSE);
        imagem.setAtivo(Boolean.TRUE);
        imagem.setFilesize(upload.get("bytes").toString());
        imagem.setSecureLink((String)upload.get("secure_url"));
        imagem.setImgLink((String) upload.get("url"));
        imagem.setFolder((String) upload.get("folder"));
        imagem.setFilename(file.getFilename());
        imagem.setEntityId(id);
        imagem.setEntityName(clazz.getSimpleName());
        imagem.setCloud(Constants.CLOUDINARY_CLOUD);

        return save(imagem);
    }

    @Override
    public void editar(Integer idImagem, Imagem imagem) {

    }

    @Override
    public <E extends AbstractEntity> List<Imagem> findAllByEntity(E entity) {

        String entityName = entity.getClass().getSimpleName();
        Integer entityId = entity.getId();

        return imagemRepository.findAllByEntityNameAndEntityIdAndAtivoIsTrueAndDeletedIsFalse(entityName, entityId);
    }

    @Override
    public void excluir(Integer imageId, boolean excluir) {
        Imagem imagem = findAndValidate(imageId);

        String idImgur = imagem.getIdCloud();

        Response response = imgurService.delete(idImgur);

        if (response.isSuccessful() && !excluir) {
            imagem.setDeleted(Boolean.TRUE);
            save(imagem);
            return;
        }
        delete(imagem);
    }

    @Override
    public void excluir(Integer imageId) {
        excluir(imageId, false);
    }
}
