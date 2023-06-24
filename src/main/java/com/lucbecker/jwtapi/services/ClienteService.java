package com.lucbecker.jwtapi.services;

import com.lucbecker.jwtapi.domain.Cliente;
import com.lucbecker.jwtapi.dtos.ClienteDTO;
import com.lucbecker.jwtapi.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    /**
     *
     * Injetando o @Bean BCryptPassowordEncoder para encodar a senha do usuárioDTO
     * antes de salvá-la na base de dados
     *
     */
    @Autowired
    private BCryptPasswordEncoder pe;

    @Autowired
    private ClienteRepository repository;

    public Cliente findById(Integer id) {
        Optional<Cliente> obj = repository.findById(id);
        return obj.orElse(null);
    }

    public List<Cliente> findAll() {
        return repository.findAll();
    }

    public Cliente create(ClienteDTO objDTO) {
        Cliente newObj = new Cliente(null, objDTO.getNome(), objDTO.getEmail(), pe.encode(objDTO.getSenha()));
        return repository.save(newObj);
    }

    public Cliente update(Integer id, @Valid ClienteDTO objDTO) {
        Cliente obj = this.findById(id);
        this.fromDTO(obj, objDTO);
        return repository.save(obj);
    }

    public void delete(Integer id) {
        this.findById(id);
        repository.deleteById(id);
    }

    private void fromDTO(Cliente obj, @Valid ClienteDTO objDTO) {
        obj.setNome(objDTO.getNome());
        obj.setEmail(objDTO.getEmail());
        obj.setSenha(objDTO.getSenha());
    }
}
