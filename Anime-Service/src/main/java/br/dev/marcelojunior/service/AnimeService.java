package br.dev.marcelojunior.service;

import br.dev.marcelojunior.DTO.AnimePostRequest;
import br.dev.marcelojunior.DTO.AnimePutRequest;
import br.dev.marcelojunior.DTO.AnimeResponse;
import br.dev.marcelojunior.exceptions.NotFoundException;
import br.dev.marcelojunior.mapper.AnimeMapper;
import br.dev.marcelojunior.model.Anime;
import br.dev.marcelojunior.repository.AnimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class AnimeService {

    private final AnimeRepository repository;
    private final AnimeMapper mapper;

    public List<AnimeResponse> findAll(String name){
        if (name != null && !name.isBlank()){
            return mapper.toResponseList(repository.findByNameContainingIgnoreCase(name));
        }
        return mapper.toResponseList(repository.findAll());
    }

    public AnimeResponse findById(Long id){
        return mapper.toResponse(findByIdOrThrow(id));
    }

    public AnimeResponse save(AnimePostRequest request){
        return mapper.toResponse(repository.save(mapper.toAnime(request)));
    }

    public void delete(Long id){
        findByIdOrThrow(id);
        repository.deleteById(id);
    }

    public void update(AnimePutRequest request){
         findByIdOrThrow(request.id());
         repository.save(mapper.updateAnime(request));
    }

    public Anime findByIdOrThrow(Long id){
        return repository.findById(id).orElseThrow(()-> new NotFoundException(NOT_FOUND,"Anime not found!"));
    }
}
