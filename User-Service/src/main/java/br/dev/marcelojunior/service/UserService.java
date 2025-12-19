package br.dev.marcelojunior.service;

import br.dev.marcelojunior.DTO.UserPostRequest;
import br.dev.marcelojunior.DTO.UserResponse;
import br.dev.marcelojunior.mapper.UserMapper;
import br.dev.marcelojunior.model.User;
import br.dev.marcelojunior.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper = UserMapper.INSTANCE;

    public UserResponse findById(Long id){
        return mapper.toResponse(findByIdOrThrow(id));
    }

    public UserResponse save(UserPostRequest request){
        return mapper.toResponse(repository.save(mapper.toUser(request)));
    }

    private User findByIdOrThrow(Long id){
        return repository.findById(id).orElseThrow(()->new ResponseStatusException(NOT_FOUND,"User not found!"));
    }
}
