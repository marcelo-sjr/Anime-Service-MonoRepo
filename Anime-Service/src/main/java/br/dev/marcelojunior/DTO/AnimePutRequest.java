package br.dev.marcelojunior.DTO;

import br.dev.marcelojunior.model.Category;


public record AnimePutRequest (Long id, String name, Category category){}
