package com.lucbecker.jwtapi.services;

import com.lucbecker.jwtapi.domain.Cliente;
import com.lucbecker.jwtapi.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    public Cliente findById(Integer id) {
        Optional<Cliente> obj = repository.findById(id);
        return obj.orElse(null);
    }

    public List<Cliente> findAll() {
        return repository.findAll();
    }
}
