package com.lucbecker.jwtapi.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucbecker.jwtapi.config.JWTUtil;
import com.lucbecker.jwtapi.dtos.CredenciaisDTO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * Ao criar uma classe que extends UsernamePasswordAuthenticationFilter
 * automaticamente o Spring sabe que essa classe terá que interceptar a
 * requisição de login e o endpoint /login já é reservado para o Spring Security
 *
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    private JWTUtil jwtUtil;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        super();
        setAuthenticationFailureHandler(new JwtAuthenticationFailureHandler());
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Sobreescrita do metodo que tentará realizar a autenticação
     *
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        try {

            /**
             * A linha abaixo irá pegar as informações da requisição (HttpServletRequest)
             * passada no endpoint /login e tentará converter em uma CredenciaisDTO
             *
             * Ex: "email": "fulano@rmail.com", "senha": "123"
             *
             */
            CredenciaisDTO credenciaisDTO = new ObjectMapper().readValue(request.getInputStream(),
                    CredenciaisDTO.class);

            /**
             * Após pegar o email e senha, será realizado a instanciação de um
             * UsernamePasswordAuthenticationToken do (Spring Security) e para instanciar um
             * o objeto desse é necessário passar o usuário, senha e uma lista vazia
             *
             */
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    credenciaisDTO.getEmail(), credenciaisDTO.getSenha(), new ArrayList<>());

            /**
             * De posse do objeto acima nós vamos então chamar o método que irá validar se
             * realmente o usuário e senha são válidpos. Esse será o nosso filtro de
             * autênticação. O próprio framework fará isso com base no que eu implementei lá
             * no UserDetailService e UserDetail, usando os contratos immplementados e
             * verifica se o usuario e senha são válidos
             *
             * Se as informações estiverem incorretas ele vai retornar um BAD_CREDENCTIALS
             *
             */
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            return authentication;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Sobreescrita do metodo que retorna a autenticação realizada com sucesso caso
     * a mesma ocorra após a validação acima implementada (attemptAuthentication)
     *
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        String username = ((UserSS) authResult.getPrincipal()).getUsername();
        String token = jwtUtil.generateToken(username);
        response.addHeader("Authorization", "Bearer " + token);
    }

    private class JwtAuthenticationFailureHandler implements AuthenticationFailureHandler {

        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                            AuthenticationException exception) throws IOException, ServletException {
            response.setStatus(401);
            response.setContentType("application/json");
            response.getWriter().append(json());
        }

        private String json() {
            long date = new Date().getTime();
            return "{\"timestamp\": " + date + ", " + "\"status\": 401, " + "\"error\": \"Não autorizado\", "
                    + "\"message\": \"Email ou senha inválidos\", " + "\"path\": \"/login\"}";
        }
    }

}
