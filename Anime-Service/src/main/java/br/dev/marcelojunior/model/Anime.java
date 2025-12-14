package br.dev.marcelojunior.model;

import lombok.*;

@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Anime {
    private Long id;
    private String name;
    private Category category;
}
