package br.com.alura.screenmatch.principal;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.service.ConsumoAPI;
import br.com.alura.screenmatch.service.ConverteDados;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoAPI consumo = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();

    private final String ENDERECO = "http://omdbapi.com/?t=";
    private final String API_KEY = "&apikey=4def3586";
    

    public void exibeMenu () {
        System.out.println("Digite o nome da série para busca: ");
        var nomeSerie = leitura.nextLine();
		var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);

		DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
		System.out.println(dados);

        List <DadosTemporada> temporadas = new ArrayList<>();
        for (int i = 1; i < dados.totalTemporadas(); i++) {
            json = consumo.obterDados(ENDERECO+ nomeSerie.replace(" ", "+") + "&Season=" +  i + API_KEY);
            DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }
    
        //formato impressão com lambda
        //temporadas.forEach(temporada -> System.out.println(temporada));
    
        //formato impressão sem lambda
        temporadas.forEach(System.out::println);

        // forma mais trabalhosa de imprimir os nomes dos episodios
        // for (int i = 0; i < dados.totalTemporadas(); i++) {
        //     List <DadosEpisodio> episodiosTemporada = temporadas.get(i).listaEpisodios();
        //     for (int j = 0; j < episodiosTemporada.size(); j++) {
        //         System.out.println(episodiosTemporada.get(j).titulo());
        //     }
        // }   

        //forma mais curta de imprimir os nomes dos episodios
        temporadas.forEach(t -> t.listaEpisodios().forEach(e -> System.out.println(e.titulo())));
    }
}