package br.dev.marcelojunior.mapper;

import br.dev.marcelojunior.DTO.AnimeGetResponse;
import br.dev.marcelojunior.DTO.AnimePostRequest;
import br.dev.marcelojunior.model.Anime;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AnimeMapper {

    AnimeMapper INSTANCE = Mappers.getMapper(AnimeMapper.class);

    public Anime toAnime(AnimePostRequest request);

    public AnimeGetResponse toResponse(Anime entity);
}

