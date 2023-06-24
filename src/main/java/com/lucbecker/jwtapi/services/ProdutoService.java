package com.lucbecker.jwtapi.services;

import com.lucbecker.jwtapi.domain.Categoria;
import com.lucbecker.jwtapi.domain.Produto;
import com.lucbecker.jwtapi.dtos.ProdutoDTO;
import com.lucbecker.jwtapi.repositories.CategoriaRepository;
import com.lucbecker.jwtapi.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;
    @Autowired
    private CategoriaService categoriaService;
    @Autowired
    private CategoriaRepository categoriaRepository;

    public Produto findById(Integer id) {
        Optional<Produto> obj = repository.findById(id);
        return obj.orElse(null);
    }

    public List<Produto> findAll() {
        return repository.findAll();
    }

    public Produto create(Integer id_cli, ProdutoDTO objDTO) {
        Categoria cat = categoriaService.findById(id_cli);
        Produto newObj = new Produto(id_cli, objDTO.getNome(), objDTO.getPreco());
        newObj.getCategorias().add(cat);
        cat.getProdutos().add(newObj);
        categoriaRepository.save(cat);
        return repository.save(newObj);
    }

    public Produto update(Integer id, @Valid ProdutoDTO objDTO) {
        Produto obj = this.findById(id);
        this.fromDTO(obj, objDTO);
        return repository.save(obj);
    }

    private void fromDTO(Produto obj, @Valid ProdutoDTO objDTO) {
        obj.setNome(objDTO.getNome());
    }

    public void delete(Integer id) {
        this.findById(id);
        repository.deleteById(id);
    }
}
