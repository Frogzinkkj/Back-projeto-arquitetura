package com.alunos.controle.sistema.viki.security;

import com.alunos.controle.sistema.viki.entity.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.ZoneOffset;


@Component
public class JWTUtil {
  private String mySecret = "viki";
  private Algorithm encryptAlgorith = Algorithm.HMAC256(mySecret);

  public String generateToken(Usuario usuario) throws IllegalArgumentException, JWTCreationException {
    return JWT.create()
        .withIssuer("viki-api")
        .withSubject(usuario.getUsername())
        .withExpiresAt(LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00")))
        .sign(encryptAlgorith);
  }

  public String validateToken(String token){
    try{
      return (token.isEmpty()) ? "":
          JWT.require(encryptAlgorith)
          .withIssuer("viki-api")
          .build()
          .verify(token)
          .getSubject();
    }catch (JWTVerificationException exception){
      return "";
    }
  }
}
