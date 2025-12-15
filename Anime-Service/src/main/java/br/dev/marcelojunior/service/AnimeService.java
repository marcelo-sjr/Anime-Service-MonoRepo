package br.dev.marcelojunior.service;

import br.dev.marcelojunior.DTO.AnimeResponse;
import br.dev.marcelojunior.DTO.AnimePostRequest;
import br.dev.marcelojunior.DTO.AnimePutRequest;
import br.dev.marcelojunior.mapper.AnimeMapper;
import br.dev.marcelojunior.model.Anime;
import br.dev.marcelojunior.repository.AnimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class AnimeService {

    private final AnimeRepository repository;
    private final AnimeMapper mapper = AnimeMapper.INSTANCE;

    public List<AnimeResponse> findAll(){
        return repository
                .findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public AnimeResponse findById(Long id){
        return repository
                .findById(id)
                .map(mapper::toResponse)
                .orElseThrow(()-> new ResponseStatusException(NOT_FOUND,"Resource not found!"));
    }

    public List<AnimeResponse> findByName(String name){
        var animes = repository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(mapper::toResponse)
                .toList();
        if(animes.isEmpty()){
            throw new ResponseStatusException(NOT_FOUND,"Resource not found!");
        }
        return animes;
    }

    public AnimeResponse save(AnimePostRequest request){
       Anime anime = repository.save(mapper.toAnime(request));
       return mapper.toResponse(anime);
    }

    public void delete(Long id){
        repository.deleteById(id);
    }

    public void update(AnimePutRequest request){
         repository.findById(request.id()).orElseThrow(()-> new ResponseStatusException(NOT_FOUND,"Resource not found!"));
         repository.save(mapper.updateAnime(request));
    }

}
