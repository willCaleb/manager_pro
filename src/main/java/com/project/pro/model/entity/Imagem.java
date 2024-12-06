package com.project.pro.model.entity;

import com.project.pro.model.dto.ImagemDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "imagem")
public class Imagem extends AbstractEntity<Integer, ImagemDTO> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "entity_name")
    private String entityName;

    @Column(name = "entity_id")
    private Integer entityId;

    @Column(name = "data_inclusao")
    private Date dataInclusao;

    @Column(name = "img_link")
    private String imgLink;

    @Column(name = "img_secure_link")
    private String secureLink;

    @Column(name = "filename")
    private String filename;

    @Column(name = "filesize")
    private String filesize;

    @Column(name = "ativo")
    private Boolean ativo;

    @Column(name = "id_cloud")
    private String idCloud;

    @Column(name = "delete_hash")
    private String deleteHash;

    @Column(name = "type")
    private String type;

    @Column(name = "private")
    private boolean privateImage;

    @Column(name = "principal")
    private boolean principal;

    @Column(name = "deleted")
    private boolean deleted;

    @Column(name = "cloud")
    private String cloud;

    @Column(name = "folder")
    private String folder;
}
