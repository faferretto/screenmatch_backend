package br.com.faferretto.screenmatch.repository;

import br.com.faferretto.screenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SerieRepository extends JpaRepository<Serie, Long> {
}
