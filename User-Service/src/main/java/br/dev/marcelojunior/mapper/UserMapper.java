package br.dev.marcelojunior.mapper;

import br.dev.marcelojunior.DTO.UserPostRequest;
import br.dev.marcelojunior.DTO.UserResponse;
import br.dev.marcelojunior.model.Email;
import br.dev.marcelojunior.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    public UserResponse toResponse(User user);

    public User toUser(UserPostRequest request);

    default Email emailMap(String value){
        return new Email(value);
    }
}
