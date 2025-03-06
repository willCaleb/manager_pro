package com.project.pro.service.impl;

import com.project.pro.exception.CustomException;
import com.project.pro.model.dto.CategoriaDTO;
import com.project.pro.model.entity.Categoria;
import com.project.pro.repository.CategoriaRepository;
import com.project.pro.service.ICategoriaService;
import com.project.pro.utils.Utils;
import com.project.pro.validator.ValidadorCategoria;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoriaService extends AbstractService<Categoria, CategoriaDTO, CategoriaRepository> implements ICategoriaService {

    private final CategoriaRepository categoriaRepository;
    private ValidadorCategoria validadorCategoria = new ValidadorCategoria();

    @PostConstruct
    private void setValidadorService() {
        validadorCategoria.setCategoriaService(this);
    }

    @Override
    public Categoria incluir(Categoria categoria) {
        verificarDuplicidadeNome(categoria);
        validadorCategoria.validarInsert(categoria);
        validadorCategoria.validarInsertOuUpdate(categoria);
        return save(categoria);
    }

    @Override
    public void editar(Integer idCategoria, Categoria categoria) {
        verificarDuplicidadeNome(categoria);
        Categoria categoriaManaged = findAndValidate(idCategoria);

        categoria.setId(categoriaManaged.getId());

        save(categoria);
    }

    private void verificarDuplicidadeNome(Categoria categoria) {

        if (Utils.isNotEmpty(categoria.getCategoriaSuperior())) {
            Optional<Categoria> optcategoria = categoriaRepository.findByNome(categoria.getNome());

            if (optcategoria.isPresent()) {
                Categoria categoriaManaged = optcategoria.get();
                throw new CustomException("Já existe uma categoria com o nome ", categoriaManaged.getNome());
            }
        }
    }
}
