package com.alunos.controle.sistema.viki.security;

import com.alunos.controle.sistema.viki.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

  @Autowired
  private JWTUtil jwtUtil;

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    tryAuthUser(request);
    filterChain.doFilter(request,response);
  }

  private String recoverToken(HttpServletRequest request) {
    var authHeader = request.getHeader("Authorization");
    if (authHeader == null) return null;
    return authHeader.replace("Bearer ", "");
  }

  private void tryAuthUser(HttpServletRequest request){
    String token = this.recoverToken(request);
    if (token == null) return;

    String login = jwtUtil.validateToken(token);
    if (login == null || login.isEmpty()) return;

    var usuario = usuarioRepository.findByUsername(login);
    if (usuario == null) return;

    var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }
}
