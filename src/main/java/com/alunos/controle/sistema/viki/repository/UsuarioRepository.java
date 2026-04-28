package com.alunos.controle.sistema.viki.repository;

import com.alunos.controle.sistema.viki.entity.Usuario;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.function.Function;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {

  Usuario findByUsername(String username);
}

