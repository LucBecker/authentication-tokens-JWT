package com.lucbecker.jwtapi.resources;

import com.lucbecker.jwtapi.domain.Categoria;
import com.lucbecker.jwtapi.dtos.CategoriaDTO;
import com.lucbecker.jwtapi.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

    @Autowired
    private CategoriaService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Categoria> findById(@PathVariable Integer id) {
        Categoria obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> findAll() {
        List<Categoria> list = service.findAll();
        List<CategoriaDTO> listDTO = list.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }

    @PostMapping
    public ResponseEntity<Categoria> create(@Valid @RequestParam(value = "usuario", defaultValue = "0") Integer id_cli, @RequestBody CategoriaDTO objDto) {
        Categoria newObj = service.create(id_cli, objDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
        return ResponseEntity.created(uri).body(newObj);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<CategoriaDTO> update(@PathVariable Integer id, @Valid @RequestBody CategoriaDTO objDTO) {
        Categoria newObj = service.update(id, objDTO);
        objDTO = new CategoriaDTO(newObj);
        return ResponseEntity.ok().body(objDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
