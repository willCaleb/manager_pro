package com.project.pro.service.impl;

import com.project.pro.enums.EnumCustomException;
import com.project.pro.exception.CustomException;
import com.project.pro.model.beans.Data;
import com.project.pro.model.beans.ImgurReturn;
import com.project.pro.model.dto.FileUploadDTO;
import com.project.pro.model.dto.ImagemDTO;
import com.project.pro.model.entity.AbstractEntity;
import com.project.pro.model.entity.Imagem;
import com.project.pro.repository.ImagemRepository;
import com.project.pro.service.IImgurService;
import com.project.pro.service.IImagemService;
import com.project.pro.utils.DateUtils;
import com.project.pro.utils.StringUtil;
import com.project.pro.utils.Utils;
import lombok.RequiredArgsConstructor;
import okhttp3.Response;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImagemServiceImpl extends AbstractService<Imagem, ImagemDTO, ImagemRepository> implements IImagemService {

    private final IImgurService imgurService;

    private final ImagemRepository imagemRepository;

    @Override
    public Imagem incluir(FileUploadDTO file, Class clazz, Integer idEntidade) {

        ImgurReturn upload = imgurService.upload(file);

        if (Utils.isNotEmpty(upload)) {
            Imagem imagem = new Imagem();

            Data data = upload.getData();

            imagem.setImgLink(data.getLink());
            imagem.setAtivo(Boolean.TRUE);
            imagem.setDataInclusao(DateUtils.getDate());
            imagem.setFilesize(StringUtil.toString(data.getSize()));
            imagem.setImgLink(data.getLink());
            imagem.setEntityId(idEntidade);
            imagem.setEntityName(clazz.getSimpleName());
            imagem.setType(data.getType());
            imagem.setDeleteHash(data.getDeletehash());
            imagem.setIdImgur(data.getId());
            imagem.setFilename(file.getFilename());

            return imagemRepository.save(imagem);
        }
        throw new CustomException(EnumCustomException.IMGUR_IMPOSSIVEL_FAZER_UPLOAD);
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

        String idImgur = imagem.getIdImgur();

        Response response = imgurService.delete(idImgur);

        if (response.isSuccessful() && !excluir) {
            imagem.setDeleted(Boolean.TRUE);
            imagemRepository.save(imagem);
            return;
        }
        imagemRepository.delete(imagem);
    }

    @Override
    public void excluir(Integer imageId) {
        excluir(imageId, false);
    }
}
