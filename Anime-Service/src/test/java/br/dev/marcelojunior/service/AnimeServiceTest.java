package br.dev.marcelojunior.service;

import br.dev.marcelojunior.DTO.AnimePostRequest;
import br.dev.marcelojunior.DTO.AnimePutRequest;
import br.dev.marcelojunior.DTO.AnimeResponse;
import br.dev.marcelojunior.exceptions.NotFoundException;
import br.dev.marcelojunior.mapper.AnimeMapper;
import br.dev.marcelojunior.model.Anime;
import br.dev.marcelojunior.model.Category;
import br.dev.marcelojunior.repository.AnimeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
class AnimeServiceTest {

    @Mock
    private AnimeRepository repository;
    @Mock
    private AnimeMapper mapper;
    @InjectMocks
    private AnimeService service;

    private final List<Anime> mockDB = new ArrayList<>();
    private final List<AnimeResponse> mockResponseList = new ArrayList<>();

    @BeforeEach
    public void setup() {
        mockResponseList.clear();
        mockDB.clear();

        var anime1 = new Anime(1L, "AnimeTest1", Category.DRAMA);
        var anime2 = new Anime(2L, "AnimeTest2", Category.SHOUNEN);
        mockDB.addAll(List.of(anime1, anime2));

        var response1 = new AnimeResponse(1L, "AnimeTest1", Category.DRAMA);
        var response2 = new AnimeResponse(2L, "AnimeTest2", Category.SHOUNEN);
        mockResponseList.addAll(List.of(response1, response2));
    }

    @Test
    public void findAll_shouldReturnAllAnimesToDto_whenArgIsNull() {
        Mockito.when(repository.findAll()).thenReturn(mockDB);
        Mockito.when(mapper.toResponseList(mockDB)).thenReturn(new ArrayList<>(mockResponseList));

        var animes = service.findAll(null);

        Assertions.assertThat(animes).isNotNull().isNotEmpty();
        Assertions.assertThat(animes).containsExactlyElementsOf(mockResponseList);

        Mockito.verify(repository).findAll();
        Mockito.verify(mapper).toResponseList(Mockito.anyList());
    }

    @Test
    public void findAll_shouldReturnAllAnimesToDto_whenArgIsBlank() {
        Mockito.when(repository.findAll()).thenReturn(mockDB);
        Mockito.when(mapper.toResponseList(mockDB)).thenReturn(new ArrayList<>(mockResponseList));

        var animes = service.findAll("  ");

        Assertions.assertThat(animes).isNotNull().isNotEmpty();
        Assertions.assertThat(animes).containsExactlyElementsOf(mockResponseList);

        Mockito.verify(repository).findAll();
        Mockito.verify(mapper).toResponseList(Mockito.anyList());
        Mockito.verify(repository, Mockito.never()).findByNameContainingIgnoreCase(Mockito.anyString());
    }

    @ParameterizedTest
    @MethodSource("findAllWithArgs")
    public void findAll_shouldFilterByNameContainingIgnoreCase_whenArgIsNotNullNorBlank(
            String args,
            List<Anime> repositoryReturn,
            List<AnimeResponse> mapperReturn) {
        Mockito.when(repository.findByNameContainingIgnoreCase(args)).thenReturn(repositoryReturn);
        Mockito.when(mapper.toResponseList(repositoryReturn)).thenReturn(mapperReturn);

        var animes = new ArrayList<>(service.findAll(args));

        Assertions.assertThat(animes).isNotNull().isNotEmpty();
        Assertions.assertThat(animes).containsExactlyElementsOf(mapperReturn);

        Mockito.verify(repository).findByNameContainingIgnoreCase(args);
        Mockito.verify(mapper).toResponseList(repositoryReturn);
        Mockito.verify(repository, Mockito.never()).findAll();
    }

    @Test
    public void findAll_shouldReturnAnEmptyList_whenArgDoesNotMatch() {
        Mockito.when(repository.findByNameContainingIgnoreCase("not_found")).thenReturn(Collections.emptyList());
        Mockito.when(mapper.toResponseList(Collections.emptyList())).thenReturn(Collections.emptyList());

        var animes = service.findAll("not_found");

        Assertions.assertThat(animes).isNotNull().isEmpty();

        Mockito.verify(repository).findByNameContainingIgnoreCase("not_found");
        Mockito.verify(mapper).toResponseList(Collections.emptyList());
        Mockito.verify(repository, Mockito.never()).findAll();
    }

    @Test
    public void findByIdOrThrow_shouldReturnASpecificAnime_whenIdMatches() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.ofNullable(mockDB.get(0)));

        var anime = service.findByIdOrThrow(1L);

        Assertions.assertThat(anime).isNotNull().isInstanceOf(Anime.class).hasNoNullFieldsOrProperties();
        Assertions.assertThat(mockDB).contains(anime);

        Mockito.verify(repository).findById(1L);
    }

    @Test
    public void findByIdOrThrow_shouldThrowNotFoundException_whenIdDoesNotMatch() {
        Mockito.when(repository.findById(99L)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> service.findById(99L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("404 NOT_FOUND \"Anime not found!\"")
                .extracting(ex -> ((ResponseStatusException) ex).getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void findById_shouldReturnAnAnimeToDto() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.ofNullable(mockDB.get(0)));
        Mockito.when(mapper.toResponse(mockDB.get(0))).thenReturn(new AnimeResponse(mockResponseList.get(0).id(),
                mockResponseList.get(0).name(), mockResponseList.get(0).category()));

        var animeResponse = service.findById(1L);

        Assertions.assertThat(animeResponse).isNotNull()
                .hasNoNullFieldsOrProperties()
                .isInstanceOf(AnimeResponse.class);
        Assertions.assertThat(mockResponseList).contains(animeResponse);

        Mockito.verify(repository).findById(1L);
        Mockito.verify(mapper).toResponse(mockDB.get(0));
    }

    @Test
    public void save_shouldSaveNewAnimeAndReturnADto_whenFieldsAreValid() {
        AnimePostRequest request = new AnimePostRequest("AnimeTest3", Category.ROMANCE);
        Anime entity = new Anime(null, request.name(), request.category());
        Anime savedEntity = new Anime(3L, request.name(), request.category());

        Mockito.when(mapper.toAnime(request)).thenReturn(entity);
        Mockito.when(repository.save(entity)).thenReturn(savedEntity);
        Mockito.when(mapper.toResponse(savedEntity)).thenReturn(new AnimeResponse(3L, "AnimeTest3", Category.ROMANCE));

        AnimeResponse result = service.save(request);

        Assertions.assertThat(result)
                .isNotNull()
                .hasNoNullFieldsOrProperties()
                .hasFieldOrPropertyWithValue("id", 3L)
                .hasFieldOrPropertyWithValue("name", "AnimeTest3")
                .hasFieldOrPropertyWithValue("category", Category.ROMANCE);

        Mockito.verify(mapper).toAnime(request);
        Mockito.verify(repository).save(entity);
        Mockito.verify(mapper).toResponse(savedEntity);
        Mockito.verifyNoMoreInteractions(mapper, repository);
    }

    @Test
    public void delete_shouldDeleteAnime_whenIdMatches() {
        Long id = 1L;
        Mockito.when(repository.findById(id)).thenReturn(Optional.ofNullable(mockDB.get(0)));
        Mockito.doNothing().when(repository).deleteById(id);

        Assertions.assertThatNoException()
                .isThrownBy(() -> service.delete(id));

        Mockito.verify(repository).findById(id);
        Mockito.verify(repository).deleteById(id);
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    void delete_shouldThrowException_whenIdDoesNotExist() {
        Long id = 99L;

        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> service.delete(id))
                .isInstanceOf(NotFoundException.class);

        Mockito.verify(repository, Mockito.never()).deleteById(Mockito.any());
    }

    @Test
    void update_shouldUpdateAnime_whenIdMatches() {
        AnimePutRequest request = new AnimePutRequest(1L, "UpdatedAnimeTest1", Category.ROMANCE);
        Anime mappedAnime = new Anime(1L, "UpdatedAnimeTest1", Category.ROMANCE);

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(mockDB.get(0)));
        Mockito.when(repository.save(mappedAnime)).thenReturn(mappedAnime);
        Mockito.when(mapper.updateAnime(request)).thenReturn(mappedAnime);

        Assertions.assertThatNoException().isThrownBy(() -> service.update(request));

        Mockito.verify(repository).findById(1L);
        Mockito.verify(mapper).updateAnime(request);
        Mockito.verify(repository).save(mappedAnime);
        Mockito.verifyNoMoreInteractions(repository, mapper);
    }

    @Test
    void update_shouldThrowException_whenIdDoesNotExist() {
        AnimePutRequest request = new AnimePutRequest(99L, "not_found", Category.ROMANCE);

        Mockito.when(repository.findById(request.id())).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> service.update(request))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("404 NOT_FOUND \"Anime not found!\"")
                .extracting(ex -> ((ResponseStatusException) ex).getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);

        Mockito.verify(repository).findById(99L);
        Mockito.verify(repository, Mockito.never()).save(Mockito.any());
        Mockito.verify(mapper, Mockito.never()).updateAnime(Mockito.any());
    }

    private static Stream<Arguments> findAllWithArgs() {
        var anime1 = new Anime(1L, "AnimeTest1", Category.DRAMA);
        var anime2 = new Anime(2L, "AnimeTest2", Category.SHOUNEN);
        var response1 = new AnimeResponse(1L, "AnimeTest1", Category.DRAMA);
        var response2 = new AnimeResponse(2L, "AnimeTest2", Category.SHOUNEN);

        return Stream.of(
                Arguments.of("AnimeTest1"
                        , Collections.singletonList(anime1)
                        , Collections.singletonList(response1)),

                Arguments.of("AnimeTest2"
                        , Collections.singletonList(anime2)
                        , Collections.singletonList(response2)),

                Arguments.of(anime1.getName().substring(0, 4)
                        , new ArrayList<>(List.of(anime1, anime2))
                        , new ArrayList<>(List.of(response1, response2)))
        );
    }
}