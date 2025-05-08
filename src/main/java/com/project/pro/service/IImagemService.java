package com.project.pro.service;

import com.project.pro.model.dto.FileUploadDTO;
import com.project.pro.model.dto.ImagemDTO;
import com.project.pro.model.entity.AbstractEntity;
import com.project.pro.model.entity.Imagem;
import com.project.pro.model.entity.Profissional;
import com.project.pro.repository.ImagemRepository;

import java.util.List;
import java.util.Map;

public interface IImagemService extends IAbstractService<Imagem, ImagemDTO, ImagemRepository> {

    Imagem incluirImgur(FileUploadDTO file, Class<?> clazz, Integer idEntidade);

    void editar(Integer idImagem, Imagem imagem);

    <E extends AbstractEntity> List<Imagem> findAllByEntity(E entity);

    void excluir(Integer imageId, boolean excluir);

    void excluir(Integer imageId);

    Imagem incluirCloudinary(FileUploadDTO file, Class<?> clazz, Integer id);
}
