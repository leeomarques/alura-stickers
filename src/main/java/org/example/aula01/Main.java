package org.example.aula01;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class Main {
	public static final String RESET = "\033[0m"; // Text Reset
	public static final String BLACK_BOLD = "\033[1;30m"; // BLACK
	public static final String RED_BOLD = "\033[1;31m"; // RED
	public static final String RED_BACKGROUND = "\033[41m"; // RED
	public static final String BLACK_BACKGROUND = "\033[40m"; // BLACK

	public static void main(String[] args) throws IOException, InterruptedException {

		// fazer uma conexão HTTP e fazer uma busca TopMovies
		String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json";
		URI endereco = URI.create(url);

		var client = HttpClient.newHttpClient();
		var request = HttpRequest.newBuilder(endereco).GET().build();
		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
		String body = response.body();

		// extrair só os dados que interessam (título, poster, classificação)
		var parser = new JsonParser();
		List<Map<String, String>> listaDeFilmes = parser.parse(body);

		// exibir e manipular os dados

		for (Map<String, String> filme : listaDeFilmes) {
			System.out.println("\n" + RED_BOLD + BLACK_BACKGROUND + "Titulo: " + filme.get("title") + RESET);
			System.out.println(BLACK_BOLD + "Poster: " + filme.get("image") + RESET);
			var rating = Float.parseFloat(filme.get("imDbRating"));

			String star = "";
			for (int i = 0; i < rating; i++) {
				star = star + "⭐";
			}

			System.out.println(BLACK_BACKGROUND + RED_BOLD + "Rating: " + rating + " " + star + RESET);

			System.out.println();
			

		}
	}
}