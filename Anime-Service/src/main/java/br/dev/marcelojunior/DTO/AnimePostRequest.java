package br.dev.marcelojunior.DTO;

import br.dev.marcelojunior.model.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public record AnimePostRequest(String name, Category category){}
