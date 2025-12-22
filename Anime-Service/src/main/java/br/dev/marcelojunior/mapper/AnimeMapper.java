package br.dev.marcelojunior.mapper;

import br.dev.marcelojunior.DTO.AnimePostRequest;
import br.dev.marcelojunior.DTO.AnimePutRequest;
import br.dev.marcelojunior.DTO.AnimeResponse;
import br.dev.marcelojunior.model.Anime;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AnimeMapper {

    Anime toAnime(AnimePostRequest request);

    AnimeResponse toResponse(Anime entity);

    Anime updateAnime(AnimePutRequest request);

    List<AnimeResponse> toResponseList(List<Anime> entities);
}

