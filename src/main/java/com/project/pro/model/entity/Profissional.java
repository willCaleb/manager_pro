package com.project.pro.model.entity;

import com.project.pro.model.dto.ProfissionalDTO;
import com.project.pro.utils.NumericUtils;
import com.project.pro.utils.Utils;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "profissional")
public class Profissional extends AbstractEntity<Integer, ProfissionalDTO> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "avaliacao")
    private BigDecimal avaliacao;

    @Column(name = "qntd_avaliacao")
    private Integer qntdAvaliacao;

    @OneToOne
    @JoinColumn(name = "id_pessoa", referencedColumnName = "id")
    private Pessoa pessoa;

    @Transient
    private BigDecimal mediaAvaliacao;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Categoria> categorias;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_profissional", referencedColumnName = "id")
    private List<ServicoProfissional> servicos;

    @PostLoad
    private void setMediaAvaliacao() {
        avaliacao = NumericUtils.notNullable(avaliacao);

        if (NumericUtils.isGreater(avaliacao, 0) && Utils.isNotEmpty(qntdAvaliacao)) {
            mediaAvaliacao = BigDecimal.ZERO;
            mediaAvaliacao = avaliacao.divide(BigDecimal.valueOf(qntdAvaliacao), RoundingMode.FLOOR);

            DecimalFormat format = new DecimalFormat("#,###.00");
            String av = format.format(mediaAvaliacao);
        }
    }
}
