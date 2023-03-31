package org.example.aula01;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class Main {
    public static final String RESET = "\033[0m"; // Text Reset
    public static final String RED_BOLD = "\033[1;31m"; // RED
    public static final String BLACK_BACKGROUND = "\033[40m"; // BLACK

    public static void main(String[] args) throws Exception {

        // fazer uma conexão HTTP e fazer uma busca TopMovies
        // String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json";
        String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/MostPopularMovies.json";

        URI endereco = URI.create(url);

        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

        // extrair só os dados que interessam (título, poster, classificação)
        var parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);

        var diretorio = new File("figurinhas/");
        diretorio.mkdir();

        // exibir e manipular os dados
        GeradoraDeFigurinhas geradora = new GeradoraDeFigurinhas();
        for (Map<String, String> filme : listaDeFilmes) {
            //for (int index = 0; index < 5; index++) {
            ///var filme = listaDeFilmes.get(index);

            String urlImagem = filme.get("image");
            String titulo = filme.get("title");

            InputStream inputStream = new URL(urlImagem).openStream();
            String nomeArquivo = "figurinhas/" + titulo + ".png";


            System.out.println("\n" + RED_BOLD + BLACK_BACKGROUND + "Titulo: " + titulo + RESET);
            System.out.println();
            var rating = Float.parseFloat(filme.get("imDbRating"));

            String textoFigurinha;
            InputStream imagemFeedback;

            if (rating >= 8.0) {
                textoFigurinha = "TOPZERA";
                imagemFeedback = new FileInputStream("sobreposicao/gostei.png");
            } else {
                textoFigurinha = "HMMMMMM...";
                imagemFeedback = new FileInputStream("sobreposicao/nao-gostei.png");
            }


            String star = "";
            for (int i = 0; i < rating; i++) {
                star = star + "⭐";
            }
            System.out.println(BLACK_BACKGROUND + RED_BOLD + "Rating: " + rating + " " + star + RESET);
            System.out.println();

            geradora.cria(inputStream, nomeArquivo, textoFigurinha, imagemFeedback);
        }
    }
}