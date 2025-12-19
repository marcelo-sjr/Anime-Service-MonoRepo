package br.dev.marcelojunior.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserPostRequest(@NotBlank String firstName,
                              @NotBlank String lastName,
                              @NotBlank String nickname,
                              @Email(regexp = "^[A-Za-z0-9]+([._%+-][A-Za-z0-9]+)*@([A-Za-z0-9]+(-[A-Za-z0-9]+)*\\.)+[A-Za-z]{2,}$",
                              message = "invalid e-mail address!")
                              String email){}
