package com.project.pro.controller;

import com.project.pro.model.dto.PessoaDTO;
import com.project.pro.model.entity.Pessoa;
import com.project.pro.pattern.OperationsParam;
import com.project.pro.pattern.OperationsPath;
import com.project.pro.service.impl.PessoaService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @DeleteMapping(OperationsPath.ID)
    public void excluir(@PathVariable(OperationsParam.ID) Integer idPessoa) {
        pessoaService.excluir(idPessoa);
    }

    @PutMapping(OperationsPath.ID + "/imagem")
    public PessoaDTO incluirImagem(@PathVariable(OperationsParam.ID) Integer idPessoa,
                                   @RequestBody MultipartFile file){
        return pessoaService.incluirImagem(idPessoa, file).toDto();
    }

}
