package com.project.pro.controller;

import com.project.pro.model.beans.IncluirProfissionalBean;
import com.project.pro.model.beans.LoginRequest;
import com.project.pro.model.dto.FileUploadDTO;
import com.project.pro.model.dto.ImagemDTO;
import com.project.pro.model.dto.ProfissionalDTO;
import com.project.pro.model.entity.Profissional;
import com.project.pro.pattern.OperationsParam;
import com.project.pro.pattern.OperationsPath;
import com.project.pro.service.IProfissionalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping(ProfissionalController.PATH)
@RequiredArgsConstructor
public class ProfissionalController extends AbstractController<Profissional,ProfissionalDTO> {

    public static final String PATH = "/profissional";

    private final IProfissionalService profissionalService;

    @PostMapping("/incluir")
    public ProfissionalDTO incluir(@RequestBody IncluirProfissionalBean profissionalDTO) {
        return profissionalService.incluir(profissionalDTO).toDto();
    }

    @PutMapping(OperationsPath.ID)
    public void editar(@RequestBody ProfissionalDTO profissionalDTO, @PathVariable(OperationsParam.ID) Integer id){
        profissionalService.editar(id, profissionalDTO.toEntity());
    }

    @GetMapping
    public List<ProfissionalDTO> findAllByNome(@PathParam("nome") String nome) {
        return toDtoList(profissionalService.findAllByNome(nome));
    }

    @PostMapping("/servico/{idServico}")
    public ProfissionalDTO incluirServico(@PathVariable("idServico") Integer idServico) {
        return profissionalService.incluirServico(idServico).toDto();
    }
//    @PostMapping(OperationsPath.ID + "/imagem")
//    public ImagemDTO incluirImagem(@RequestBody FileUploadDTO file,
//                                          @PathVariable(OperationsParam.ID) Integer idProfissional) {
//
//        return profissionalService.incluirImagem(file, idProfissional).toDto();
//    }

    @PostMapping(OperationsPath.ID + "/imagem")
    public ImagemDTO incluirImagem(@RequestBody FileUploadDTO file,
                                          @PathVariable(OperationsParam.ID) Integer idProfissional) {

        return profissionalService.incluirImagem(file, idProfissional).toDto();
    }

    @GetMapping(OperationsPath.ID + "/imagem")
    public List<ImagemDTO> listarImagens(@PathVariable(OperationsParam.ID) Integer idProfissional) {
        return toDtoList(profissionalService.listarImagens(idProfissional), ImagemDTO.class);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        return profissionalService.login(loginRequest);
    }

}
