package com.wajunkai.sistemaEstoque.infrastructure.security.config;

import com.wajunkai.sistemaEstoque.application.ports.outbound.TokenServicePort;
import com.wajunkai.sistemaEstoque.application.ports.outbound.UsuarioRepositoryPort;
import com.wajunkai.sistemaEstoque.domain.model.Usuario;
import io.micrometer.common.lang.NonNullApi;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@NonNullApi
@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenServicePort tokenServicePort;
    private final UsuarioRepositoryPort usuarioRepositoryPort;

    public SecurityFilter(TokenServicePort tokenServicePort, UsuarioRepositoryPort usuarioRepositoryPort) {
        this.tokenServicePort = tokenServicePort;
        this.usuarioRepositoryPort = usuarioRepositoryPort;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var token = recuperarToken(request);

        if (token != null) {
            var login = tokenServicePort.validarToken(token);

            if (login != null) {
                Usuario usuario = usuarioRepositoryPort.buscarPorLogin(login)
                        .orElse(null);

                if (usuario != null) {
                    var authentication = new UsernamePasswordAuthenticationToken(usuario, null, Collections.emptyList());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        return authHeader.substring(7);
    }
}
