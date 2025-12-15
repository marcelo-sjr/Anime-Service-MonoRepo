package br.dev.marcelojunior.repository;

import br.dev.marcelojunior.DTO.AnimeGetResponse;
import br.dev.marcelojunior.model.Anime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimeRepository extends JpaRepository<Anime, Long> {
}
