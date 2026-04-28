package com.alunos.controle.sistema.viki.service;

import com.alunos.controle.sistema.viki.repository.UsuarioRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {
  UsuarioRepository usuarioRepository;

  public AuthService(UsuarioRepository usuarioRepository) {
    this.usuarioRepository = usuarioRepository;
  }

  @Override
  public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {

    return usuarioRepository.findByUsername(username);
  }
}
