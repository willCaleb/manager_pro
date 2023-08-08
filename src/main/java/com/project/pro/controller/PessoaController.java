package com.project.pro.controller;

import com.project.pro.model.dto.PessoaDTO;
import com.project.pro.model.entity.Pessoa;
import com.project.pro.service.impl.PessoaService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(PessoaController.PATH)
@AllArgsConstructor
public class PessoaController extends AbstractController<Pessoa, PessoaDTO>{

    public static final String PATH = "/pessoa";
    private final PessoaService pessoaService;

    @PostMapping
    public PessoaDTO incluir(@RequestBody PessoaDTO pessoaDTO){
        Pessoa pessoa = pessoaService.incluir(pessoaDTO.toEntity());
        return pessoa.toDto();
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable("id") Integer idPessoa) {
        pessoaService.excluir(idPessoa);
    }

}
