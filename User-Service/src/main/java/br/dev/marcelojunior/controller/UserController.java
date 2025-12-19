package br.dev.marcelojunior.controller;

import br.dev.marcelojunior.DTO.UserPostRequest;
import br.dev.marcelojunior.DTO.UserResponse;
import br.dev.marcelojunior.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService service;

    @GetMapping("{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("save")
    public ResponseEntity<UserResponse> save(@RequestBody UserPostRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(request));
    }
}
