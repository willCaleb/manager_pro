package com.project.pro.model.entity;

import com.project.pro.enums.EnumTipoCobranca;
import com.project.pro.model.dto.ServicoProfissionalDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "pro_servico_profissional")
public class ServicoProfissional extends AbstractEntity<Integer, ServicoProfissionalDTO> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_profissional", referencedColumnName = "id")
    private Profissional profissional;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_servico", referencedColumnName = "id")
    private Servico servico;

    @Column(name = "valor")
    private BigDecimal valor;

    @Column(name = "tipo_cobranca")
    @Convert(converter = EnumTipoCobranca.EnumConverter.class)
    private EnumTipoCobranca tipoCobranca;

    @Column(name = "tempo_execucao")
    private Integer tempoExecucao;

}
