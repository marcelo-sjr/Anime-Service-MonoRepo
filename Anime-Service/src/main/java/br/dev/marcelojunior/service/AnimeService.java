package br.dev.marcelojunior.service;

import br.dev.marcelojunior.DTO.AnimeGetResponse;
import br.dev.marcelojunior.DTO.AnimePostRequest;
import br.dev.marcelojunior.mapper.AnimeMapper;
import br.dev.marcelojunior.model.Anime;
import br.dev.marcelojunior.repository.AnimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class AnimeService {

    private final AnimeRepository repository;
    private final AnimeMapper mapper = AnimeMapper.INSTANCE;

    public List<AnimeGetResponse> findAll(){
        return repository
                .findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public AnimeGetResponse findById(Long id){
        return repository
                .findById(id)
                .map(mapper::toResponse)
                .orElseThrow(()-> new ResponseStatusException(NOT_FOUND,"Resource not found!"));
    }

    public AnimeGetResponse save(AnimePostRequest request){
       Anime anime = repository.save(mapper.toAnime(request));
       return mapper.toResponse(anime);
    }
}
