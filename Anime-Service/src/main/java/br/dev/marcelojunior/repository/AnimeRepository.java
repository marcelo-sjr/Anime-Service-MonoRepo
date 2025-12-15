package br.dev.marcelojunior.repository;

import br.dev.marcelojunior.model.Anime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimeRepository extends JpaRepository<Anime, Long> {
}
