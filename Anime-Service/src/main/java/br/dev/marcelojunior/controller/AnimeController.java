package br.dev.marcelojunior.controller;

import br.dev.marcelojunior.DTO.AnimePostRequest;
import br.dev.marcelojunior.DTO.AnimePutRequest;
import br.dev.marcelojunior.DTO.AnimeResponse;
import br.dev.marcelojunior.service.AnimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class AnimeController {

    private final AnimeService service;

    @GetMapping("list")
    public ResponseEntity<List<AnimeResponse>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<AnimeResponse> findById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("filter")
    public ResponseEntity<List<AnimeResponse>> findByName(@RequestParam String name){
        return ResponseEntity.ok(service.findByName(name));
    }

    @PostMapping("save")
    public ResponseEntity<AnimeResponse> save(@RequestBody AnimePostRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(request));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("update")
    public ResponseEntity<Void> update(@RequestBody AnimePutRequest request){
        service.update(request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
