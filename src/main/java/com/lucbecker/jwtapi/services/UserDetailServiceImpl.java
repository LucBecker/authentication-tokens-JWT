package com.lucbecker.jwtapi.services;

import com.lucbecker.jwtapi.domain.Cliente;
import com.lucbecker.jwtapi.repositories.ClienteRepository;
import com.lucbecker.jwtapi.security.UserSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * A classe abaixo implementa a interface UserDetailsService do SpringSecurity
 * assim podemos sobreescrever o metodo loadUserByUsername() com a nossa lógica
 * de negócio e retornar um UserSS que também é uma classe que implementa a
 * interface UserDetail do SpringSecurity adotando as boas práticas
 *
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private ClienteRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Cliente cli = repository.findByEmail(email);

        if (cli == null)
            throw new UsernameNotFoundException("Usuário não encontrado! E-mail: " + email);

        return new UserSS(cli.getId(), cli.getEmail(), cli.getSenha(), cli.getPerfis());
    }
}
