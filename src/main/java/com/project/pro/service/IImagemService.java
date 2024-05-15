package com.project.pro.service;

import com.project.pro.model.dto.FileUploadDTO;
import com.project.pro.model.dto.ImagemDTO;
import com.project.pro.model.entity.AbstractEntity;
import com.project.pro.model.entity.Imagem;
import com.project.pro.repository.ImagemRepository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImagemService extends IAbstractService<Imagem, ImagemDTO, ImagemRepository> {

    Imagem incluir(FileUploadDTO file, Class clazz, Integer idEntidade);

    void editar(Integer idImagem, Imagem imagem);

    <E extends AbstractEntity> List<Imagem> findAllByEntity(E entity);

    void excluir(Integer imageId, boolean excluir);

    void excluir(Integer imageId);

}
