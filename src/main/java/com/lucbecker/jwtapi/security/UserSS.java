package com.lucbecker.jwtapi.security;

import com.lucbecker.jwtapi.enums.Perfil;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class UserSS  implements UserDetails {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String email;
    private String senha;
    private Collection<? extends GrantedAuthority> authorities;

    public UserSS() {
        super();
    }

    public UserSS(Integer id, String email, String senha, Set<Perfil> perfis) {
        super();
        this.id = id;
        this.email = email;
        this.senha = senha;

        /**
         * Como eu mudei no construtor Collection<? extends GrantedAuthority> para
         * receber um Set<Perfil> eu terei que converter este Set para o tipo
         * GrantedAuthority do SpringSecurity uma vez que nós pegamos a descrição do
         * perfil
         */
        this.authorities = perfis.stream().map(x -> new SimpleGrantedAuthority(x.getDescricao())).collect(Collectors.toList());
    }

    public Integer getId() {
        return id;
    }

    /**
     * Retorna as autoridades concedidas ao usuário.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    /**
     * Indica se o usuário está bloqueado ou expirada
     */
    @Override
    public boolean isAccountNonExpired() {
        /*** Por padrão vou retornar que a conta não está expirada ***/
        return true;
    }

    /**
     * Indica se o usuário está bloqueado ou desbloqueado
     */
    @Override
    public boolean isAccountNonLocked() {
        /*** Por padrão vou retornar que a conta não está bloqueada ***/
        return true;
    }

    /**
     * Indica se as credenciais do usuário (senha) expiraram.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        /*** Por padrão vou retornar que a senha não está bloqueada ***/
        return true;
    }

    /**
     * Indica se o usuário está habilitado ou desabilitado.
     */
    @Override
    public boolean isEnabled() {
        /*** Por padrão vou retornar que o usuário está habilitado ***/
        return true;
    }
}
