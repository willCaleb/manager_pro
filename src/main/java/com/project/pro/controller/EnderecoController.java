package com.project.pro.controller;

import com.project.pro.model.dto.EnderecoDTO;
import com.project.pro.model.entity.Endereco;
import com.project.pro.model.entity.Pessoa;
import com.project.pro.pattern.OperationsParam;
import com.project.pro.pattern.OperationsPath;
import com.project.pro.service.IEnderecoService;
import com.project.pro.service.IPessoaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(EnderecoController.PATH)
@RequiredArgsConstructor
public class EnderecoController extends AbstractController<Endereco, EnderecoDTO>{

    private final IEnderecoService enderecoService;

    private final IPessoaService pessoaService;

    public static final String PATH = PessoaController.PATH + OperationsPath.ID + "/endereco";

    @GetMapping
    public List<EnderecoDTO> findAllWithoutCoordenates() {
        return toDtoList(enderecoService.findAllWithoutCoordenate());
    }

    @GetMapping("/distancia/{idEndA}/{idEndB}")
    public Double getDistancia(@PathVariable("idEndA") Integer idEndA, @PathVariable("idEndB") Integer idEndB) {
        return enderecoService.calcularDistancia(idEndA, idEndB);
    }

    @PutMapping
    public void definirPrincipal() {
//        enderecoService.definirPrincipal();
    }

    @PostMapping
    EnderecoDTO incluir(@RequestBody EnderecoDTO enderecoDTO, @PathVariable(OperationsParam.ID) Integer idPessoa) throws Exception{

        Pessoa pessoa = pessoaService.findAndValidate(idPessoa);
        return enderecoService.incluir(enderecoDTO.toEntity(), pessoa).toDto();
    }

}
