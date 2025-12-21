package br.dev.marcelojunior.DTO;

import br.dev.marcelojunior.model.Category;
import jakarta.validation.constraints.NotBlank;

public record AnimePostRequest(@NotBlank String name, Category category){}
