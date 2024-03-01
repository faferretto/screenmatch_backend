package br.com.faferretto.screenmatch.repository;

import br.com.faferretto.screenmatch.model.Categoria;
import br.com.faferretto.screenmatch.model.Episodio;
import br.com.faferretto.screenmatch.model.Serie;
import jakarta.persistence.ManyToOne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long> {
    @Query("select s from Serie s WHERE s.titulo ILIKE %:nomeSerie%")
    Optional<Serie> seriePorTrecho(String nomeSerie);

    List<Serie> findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(String nomeAtor, Double avaliacao);

    List<Serie> findTop5ByOrderByAvaliacaoDesc();

    List<Serie> findByGenero(Categoria categoria);

    @Query("select s from Serie s WHERE s.totalTemporadas <= :qntTemporadas AND s.avaliacao >= :avaliacao")
    List<Serie> seriesPorTemporadaEAvaliacao(double avaliacao, int qntTemporadas);
    @Query("select e from Serie s JOIN s.episodios e WHERE e.titulo ILIKE %:trechoEpisodio%")
    List<Episodio> episodiosPorTrecho(String trechoEpisodio);

    @Query("select e from Serie s JOIN s.episodios e WHERE s = :serie ORDER BY e.avaliacao DESC LIMIT 5")
    List<Episodio> topEpisodiosPorSerie(Serie serie);

    @Query("select e from Serie s JOIN s.episodios e WHERE s = :serie AND year(e.dataLancamento) >= :anoLancamento")
    List<Episodio> episodiosPorSerieEAno(Serie serie, Integer anoLancamento);

    @Query("SELECT s FROM Serie s " +
            "JOIN s.episodios e " +
            "GROUP BY s " +
            "ORDER BY MAX(e.dataLancamento) DESC LIMIT 5")
    List<Serie> encontrarEpisodiosMaisRecentes();
}
