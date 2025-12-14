package br.dev.marcelojunior.DTO;

import br.dev.marcelojunior.model.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class AnimePostRequest {
    private Long id;
    private String name;
    private Category category;
}
