package com.lucbecker.jwtapi.resources;

import com.lucbecker.jwtapi.domain.Produto;
import com.lucbecker.jwtapi.dtos.ProdutoDTO;
import com.lucbecker.jwtapi.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {

    @Autowired
    private ProdutoService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Produto> findById(@PathVariable Integer id) {
        Produto obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> findAll() {
        List<Produto> list = service.findAll();
        List<ProdutoDTO> listDTO = list.stream().map(obj -> new ProdutoDTO(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }

    @PostMapping
    public ResponseEntity<Produto> create(@Valid @RequestParam(value = "usuario", defaultValue = "0") Integer id_cat, @RequestBody ProdutoDTO objDto) {
        Produto newObj = service.create(id_cat, objDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
        return ResponseEntity.created(uri).body(newObj);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<ProdutoDTO> update(@PathVariable Integer id, @Valid @RequestBody ProdutoDTO objDTO) {
        Produto newObj = service.update(id, objDTO);
        objDTO = new ProdutoDTO(newObj);
        return ResponseEntity.ok().body(objDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
