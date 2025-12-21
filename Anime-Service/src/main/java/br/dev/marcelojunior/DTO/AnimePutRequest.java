package br.dev.marcelojunior.DTO;

import br.dev.marcelojunior.model.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


public record AnimePutRequest (@Positive @NotNull Long id, @NotBlank String name, @NotBlank Category category){}
