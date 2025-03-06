package com.project.pro.model.entity;

import com.project.pro.model.dto.ProfissionalDTO;
import com.project.pro.utils.NumericUtils;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "profissional")
public class Profissional extends AbstractEntity<Integer, ProfissionalDTO> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Transient
    private Integer qntdAvaliacao;

    @Column(name = "email")
    private String email;

    @Column(name = "cpf")
    private String cpf;

    @OneToMany(mappedBy = "profissional")
    private List<ProfissionalAvaliacao> avaliacoes;

    @OneToOne
    @JoinColumn(name = "id_pessoa", referencedColumnName = "id")
    private Pessoa pessoa;

    @OneToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    private Usuario usuario;

    @Transient
    private BigDecimal mediaAvaliacao;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Categoria> categorias;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_profissional", referencedColumnName = "id")
    private List<ServicoProfissional> servicos;

    @Column(name = "multiplo_agendamento")
    private boolean multiploAgendamento;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "pro_profissional_cliente",
            joinColumns = @JoinColumn(name = "id_profissional"),
            inverseJoinColumns = @JoinColumn(name = "id_cliente")
    )
    private List<Cliente> clientes;

    @Transient
    private List<Imagem> imagens;

    @PostLoad
    private void postLoad() {
        setMediaAvaliacao();
        setQntdAvaliacao();
    }

    private void setQntdAvaliacao() {
        this.qntdAvaliacao = avaliacoes.size() > 0 ? avaliacoes.size() : 0;
    }

    private void setMediaAvaliacao() {
        this.mediaAvaliacao = NumericUtils.notNullable(mediaAvaliacao);

//        if (NumericUtils.isGreater(avaliacoes.size(), 0)) {
//             mediaAvaliacao = avaliacoes.stream()
//                     .map(ProfissionalAvaliacao::getNota)
//                     .reduce(BigDecimal.ZERO, BigDecimal::add)
//                     .divide(BigDecimal.valueOf(avaliacoes.size()), RoundingMode.FLOOR);
//        }
    }
}
