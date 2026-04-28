package com.alunos.controle.sistema.viki.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
  private boolean success;
  private T data;
  private String message;

  public static <T> ApiResponse<T> success(T data, String message) {
    return new ApiResponse<>(true, data, message);
  }

  public static ApiResponse<Void> successMessage(String message) {
    return new ApiResponse<>(true, null, message);
  }

  public static ApiResponse<Void> error(String message) {
    return new ApiResponse<>(false, null, message);
  }
}