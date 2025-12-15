package br.dev.marcelojunior.DTO;

import br.dev.marcelojunior.model.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


public record AnimeResponse(Long id, String name, Category category){}
