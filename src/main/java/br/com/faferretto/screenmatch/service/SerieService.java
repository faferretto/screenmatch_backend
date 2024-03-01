package br.com.faferretto.screenmatch.service;

import br.com.faferretto.screenmatch.dto.EpisodioDTO;
import br.com.faferretto.screenmatch.dto.SerieDTO;
import br.com.faferretto.screenmatch.model.Categoria;
import br.com.faferretto.screenmatch.model.Episodio;
import br.com.faferretto.screenmatch.model.Serie;
import br.com.faferretto.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SerieService {

    @Autowired
    private SerieRepository repositorio;

    public List<SerieDTO> obterTodasAsSeries() {
        return converteDados(repositorio.findAll());
    }

    public List<SerieDTO> obterTop5Series() {
        return converteDados(repositorio.findTop5ByOrderByAvaliacaoDesc());
    }

    public List<SerieDTO> obterLancamentos() {
        return converteDados(repositorio.encontrarEpisodiosMaisRecentes());
    }

    private List<SerieDTO> converteDados(List<Serie> series) {
        return series.stream()
                .map(s -> new SerieDTO(s.getId(),s.getTitulo(),s.getTotalTemporadas(),s.getAvaliacao(),
                        s.getGenero(),s.getAtores(),s.getPoster(),s.getSinopse()))
                .collect(Collectors.toList());
    }

    public SerieDTO obterPorId(Long id) {
        Optional<Serie> serie = repositorio.findById(id);
        if (serie.isPresent()) {
            Serie s = serie.get();
            return new SerieDTO(s.getId(),s.getTitulo(),s.getTotalTemporadas(),s.getAvaliacao(),
                    s.getGenero(),s.getAtores(),s.getPoster(),s.getSinopse());
        }
        return null;
    }

    public List<EpisodioDTO> obterTodasTemporadas(Long id) {
        Optional<Serie> serie = repositorio.findById(id);
        if (serie.isPresent()) {
            Serie s = serie.get();
            return s.getEpisodios().stream()
                    .map(e -> new EpisodioDTO(e.getTemporada(), e.getNumero(), e.getTitulo(),e.getAvaliacao()))
                    .collect(Collectors.toList());
        }
        return null;
    }

    public List<EpisodioDTO> obterEpisodiosPorTemporada(Long id, Integer temporada) {
        Optional<Serie> serie = repositorio.findById(id);
        if (serie.isPresent()) {
            Serie s = serie.get();
            return s.getEpisodios().stream()
                    .filter(e -> e.getTemporada() == temporada)
                    .map(e -> new EpisodioDTO(e.getTemporada(), e.getNumero(), e.getTitulo(), e.getAvaliacao()))
                    .collect(Collectors.toList());
        }
        return null;
    }

    public List<SerieDTO> obterSeriesPorCategoria(String categoria) {
        Categoria buscaCategoria = Categoria.fromPortugues(categoria);
        return converteDados(repositorio.findByGenero(buscaCategoria));
    }

    public List<EpisodioDTO> obterTop5Episodios(Long id) {
        Optional<Serie> serie = repositorio.findById(id);
        if (serie.isPresent()) {
            Serie s = serie.get();
            return repositorio.topEpisodiosPorSerie(s).stream()
                    .map(e -> new EpisodioDTO(e.getTemporada(), e.getNumero(), e.getTitulo(), e.getAvaliacao()))
                    .collect(Collectors.toList());
        }
        return null;
    }
}
