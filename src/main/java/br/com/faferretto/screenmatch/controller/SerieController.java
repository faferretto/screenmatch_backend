package br.com.faferretto.screenmatch.controller;

import br.com.faferretto.screenmatch.dto.EpisodioDTO;
import br.com.faferretto.screenmatch.dto.SerieDTO;
import br.com.faferretto.screenmatch.model.Categoria;
import br.com.faferretto.screenmatch.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/series")
public class SerieController {

    @Autowired
    private SerieService servico;

    @GetMapping
    public List<SerieDTO> obterSeries(){
        return servico.obterTodasAsSeries();
    }

    @GetMapping("/top5")
    public List<SerieDTO> obterTop5Series() {
        return servico.obterTop5Series();
    }

    @GetMapping("/lancamentos")
    public List<SerieDTO> obterLancamentos() {
        return servico.obterLancamentos();
    }

    @GetMapping("/{id}")
    public SerieDTO obterPorID(@PathVariable Long id) {
        return servico.obterPorId(id);
    }

    @GetMapping("/{id}/temporadas/todas")
    public List<EpisodioDTO> obterTodasTemporadas(@PathVariable Long id){
        return servico.obterTodasTemporadas(id);
    }

    @GetMapping("/{id}/temporadas/{temporada}")
    public List<EpisodioDTO> obterEpisodiosTemporada(@PathVariable Long id, @PathVariable Integer temporada) {
        return servico.obterEpisodiosPorTemporada(id,temporada);
    }

    @GetMapping("/categoria/{categoria}")
    public List<SerieDTO> obterSeriePorCategoria(@PathVariable String categoria) {
        return servico.obterSeriesPorCategoria(categoria);
    }

    @GetMapping("/{id}/temporadas/top")
    public List<EpisodioDTO> obterTop5Episodios(@PathVariable Long id){
        return servico.obterTop5Episodios(id);
    }

}
