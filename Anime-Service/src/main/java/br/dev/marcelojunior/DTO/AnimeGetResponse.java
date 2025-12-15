package br.dev.marcelojunior.DTO;

import br.dev.marcelojunior.model.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class AnimeGetResponse {
    private Long id;
    private String name;
    private Category category;
}
