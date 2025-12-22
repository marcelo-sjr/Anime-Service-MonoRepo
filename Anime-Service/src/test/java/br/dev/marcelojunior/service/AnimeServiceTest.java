package br.dev.marcelojunior.service;

import br.dev.marcelojunior.DTO.AnimeResponse;
import br.dev.marcelojunior.mapper.AnimeMapper;
import br.dev.marcelojunior.model.Anime;
import br.dev.marcelojunior.model.Category;
import br.dev.marcelojunior.repository.AnimeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
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
    @Order(1)
    public void findAll_shouldReturnAllAnimes_whenArgIsNull() {
        BDDMockito.when(repository.findAll()).thenReturn(mockDB);
        BDDMockito.when(mapper.toResponseList(mockDB)).thenReturn(new ArrayList<>(mockResponseList));

        var animes = service.findAll(null);

        Assertions.assertThat(animes).isNotNull().isNotEmpty();
        Assertions.assertThat(animes).containsExactlyElementsOf(mockResponseList);

        Mockito.verify(repository).findAll();
        Mockito.verify(mapper).toResponseList(Mockito.anyList());
    }

    @Test
    @Order(2)
    public void findAll_shouldReturnAllAnimes_whenArgIsBlank() {
        BDDMockito.when(repository.findAll()).thenReturn(mockDB);
        BDDMockito.when(mapper.toResponseList(mockDB)).thenReturn(new ArrayList<>(mockResponseList));

        var animes = service.findAll("  ");

        Assertions.assertThat(animes).isNotNull().isNotEmpty();
        Assertions.assertThat(animes).containsExactlyElementsOf(mockResponseList);

        Mockito.verify(repository).findAll();
        Mockito.verify(mapper).toResponseList(Mockito.anyList());
        Mockito.verify(repository, Mockito.never()).findByNameContainingIgnoreCase(Mockito.anyString());
    }

    @ParameterizedTest
    @MethodSource("findAllWithArgs")
    @Order(3)
    public void findAll_shouldFilterByNameContainingIgnoreCase_whenArgIsNotNull(
            String args,
            List<Anime> repositoryReturn,
            List<AnimeResponse> mapperReturn) {
        BDDMockito.when(repository.findByNameContainingIgnoreCase(args))
                .thenReturn(repositoryReturn);
        BDDMockito.when(mapper.toResponseList(repositoryReturn)).thenReturn(mapperReturn);

        var animes = new ArrayList<>(service.findAll(args));

        Assertions.assertThat(animes).isNotNull().isNotEmpty();
        Assertions.assertThat(animes).containsExactlyElementsOf(mapperReturn);

        Mockito.verify(repository).findByNameContainingIgnoreCase(args);
        Mockito.verify(mapper).toResponseList(repositoryReturn);
        Mockito.verify(repository, Mockito.never()).findAll();
    }

    @Test
    @Order(4)
    public void findAll_shouldReturnAnEmptyList_whenArgDoesntMatch() {
        BDDMockito.when(repository.findByNameContainingIgnoreCase("not_found")).thenReturn(Collections.emptyList());
        BDDMockito.when(mapper.toResponseList(Collections.emptyList())).thenReturn(Collections.emptyList());

        var animes = service.findAll("not_found");

        Assertions.assertThat(animes).isNotNull().isEmpty();

        Mockito.verify(repository).findByNameContainingIgnoreCase("not_found");
        Mockito.verify(mapper).toResponseList(Collections.emptyList());
        Mockito.verify(repository, Mockito.never()).findAll();
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