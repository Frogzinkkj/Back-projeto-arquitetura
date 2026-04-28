package com.alunos.controle.sistema.viki.repository;

import com.alunos.controle.sistema.viki.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Role, Long> {

  Role findByNome(String nome);

}
