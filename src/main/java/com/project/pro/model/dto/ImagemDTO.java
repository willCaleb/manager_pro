package com.project.pro.model.dto;

import com.project.pro.model.entity.Imagem;
import lombok.Data;

import java.util.Date;

@Data
public class ImagemDTO extends AbstractDTO<Integer, Imagem>{

    private Integer id;

    private String entityName;

    private Integer entityId;

    private Date dataInclusao;

    private String imgLink;

    private String filename;

    private String filesize;

    private String idImgur;

    private Boolean ativo;

    private String deleteHash;

    private String type;

    private boolean privateImage;

    private boolean principal;


}
