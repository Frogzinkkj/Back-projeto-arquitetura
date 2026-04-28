package com.alunos.controle.sistema.viki.controller;

import com.alunos.controle.sistema.viki.dto.ApiResponse;
import com.alunos.controle.sistema.viki.dto.LoginDTO;
import com.alunos.controle.sistema.viki.dto.RegisterDTO;
import com.alunos.controle.sistema.viki.dto.TokenDTO;
import com.alunos.controle.sistema.viki.entity.Professor;
import com.alunos.controle.sistema.viki.entity.Role;
import com.alunos.controle.sistema.viki.entity.Usuario;
import com.alunos.controle.sistema.viki.repository.ProfessorRepository;
import com.alunos.controle.sistema.viki.repository.RoleRepository;
import com.alunos.controle.sistema.viki.repository.UsuarioRepository;
import com.alunos.controle.sistema.viki.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  JWTUtil jwtUtil;

  @Autowired
  UsuarioRepository usuarioRepository;

  @Autowired
  ProfessorRepository professorRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder passwordEncoder;


  @PostMapping("/login")
  public ResponseEntity<ApiResponse<TokenDTO>> login(@Valid @RequestBody LoginDTO loginDTO){
    var loginTemp = new UsernamePasswordAuthenticationToken(loginDTO.username(),loginDTO.password());
    var auth = authenticationManager.authenticate(loginTemp);
    var validUser = (Usuario) auth.getPrincipal();
    TokenDTO token = new TokenDTO(jwtUtil.generateToken(validUser));
    return ResponseEntity.ok(ApiResponse.success(token, "Login realizado com sucesso"));
  }

  @PostMapping("/register")
  public ResponseEntity<ApiResponse<Void>> register(@Valid @RequestBody RegisterDTO registerDTO){

    Professor foundProfessor = professorRepository.findByMatriculaAndEmail(registerDTO.matricula(),registerDTO.email());

    if(foundProfessor == null){
      throw new RuntimeException("Matricula ou email não encontrado");
    }

    Usuario foundUser = usuarioRepository.findByUsername(registerDTO.username());

    if( foundUser != null){
      return ResponseEntity.badRequest().body(ApiResponse.error("Username ja em uso"));
    }



    Role roleProf = roleRepository.findByNome("ROLE_PROFESSOR");
    if(roleProf ==null){
      throw new RuntimeException("Perfil de professor não configurado no banco");
    }


    Usuario newUser = new Usuario();
    newUser.setUsername(registerDTO.username());
    newUser.setPassword(passwordEncoder.encode(registerDTO.password()));
    newUser.setEmail(registerDTO.email());
    newUser.setProfessor(foundProfessor);
    newUser.setRole(roleProf);

    usuarioRepository.save(newUser);
    return ResponseEntity.status(201).body(ApiResponse.successMessage("Usuário registrado com sucesso"));
  }
}
