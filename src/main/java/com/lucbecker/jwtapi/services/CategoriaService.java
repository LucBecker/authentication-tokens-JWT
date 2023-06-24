package com.lucbecker.jwtapi.services;

import com.lucbecker.jwtapi.domain.Categoria;
import com.lucbecker.jwtapi.domain.Cliente;
import com.lucbecker.jwtapi.dtos.CategoriaDTO;
import com.lucbecker.jwtapi.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repository;
    @Autowired
    private ClienteService clienteService;

    public Categoria findById(Integer id) {
        Optional<Categoria> obj = repository.findById(id);
        return obj.orElse(null);
    }

    public List<Categoria> findAll() {
        return repository.findAll();
    }

    public Categoria create(Integer id_cli, CategoriaDTO objDTO) {
        Cliente cli = clienteService.findById(id_cli);
        Categoria newObj = new Categoria(null, objDTO.getNome(), cli);
        return repository.save(newObj);
    }

    public Categoria update(Integer id, @Valid CategoriaDTO objDTO) {
        Categoria obj = this.findById(id);
        this.fromDTO(obj, objDTO);
        return repository.save(obj);
    }

    private void fromDTO(Categoria obj, @Valid CategoriaDTO objDTO) {
        obj.setNome(objDTO.getNome());
    }

    public void delete(Integer id) {
        this.findById(id);
        repository.deleteById(id);
    }

}
