package com.project.pro.model.dto;

import com.project.pro.model.entity.Profissional;
import com.project.pro.model.entity.ProfissionalImagem;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class ProfissionalImagemDTO extends AbstractDTO<Integer, ProfissionalImagem>{

    private Integer id;

    private String url;

    private Profissional profissional;

    private Date dataInclusao;

    private MultipartFile file;
}
