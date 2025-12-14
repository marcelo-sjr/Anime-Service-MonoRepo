package br.dev.marcelojunior.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class Anime {
    private Long id;
    private String name;
    private Category category;
}
