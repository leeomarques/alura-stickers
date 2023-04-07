package org.example.alura;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExtratorDeConteudoDoIMDB implements ExtratorDeConteudo {
    public static final String RESET = "\033[0m"; // Text Reset
    public static final String RED_BOLD = "\033[1;31m"; // RED
    public static final String BLACK_BACKGROUND = "\033[40m"; // BLACK

    public List<Conteudo> extraiConteudos(String json) {
        // extrair só os dados que interessam (título, poster, classificação)
        var parser = new JsonParser();
        List<Map<String, String>> listaDeAtributos = parser.parse(json);

        List<Conteudo> conteudos = new ArrayList<>();

        GeradoraDeFigurinhas geradora = new GeradoraDeFigurinhas();
        // popular a lista de conteudos
        for (Map<String, String> atributos : listaDeAtributos) {
            String titulo = atributos.get("title");
            String urlImagem = atributos.get("image");
            String imDbRating = atributos.get("imDbRating");

            InputStream inputStream = null;
            try {
                inputStream = new URL(urlImagem).openStream();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String nomeArquivo = "figurinhas/" + titulo + ".png";


            System.out.println("\n" + RED_BOLD + BLACK_BACKGROUND + "Titulo: " + titulo + RESET);
            System.out.println();
            var rating = Float.parseFloat(imDbRating);

            String textoFigurinha;
            InputStream imagemFeedback;

            if (rating >= 8.0) {
                textoFigurinha = "TOPZERA";
                try {
                    imagemFeedback = new FileInputStream("sobreposicao/gostei.png");
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } else {
                textoFigurinha = "HMMMMMM...";
                try {
                    imagemFeedback = new FileInputStream("sobreposicao/nao-gostei.png");
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }


            String star = "";
            for (int i = 0; i < rating; i++) {
                star = star + "⭐";
            }
            System.out.println(BLACK_BACKGROUND + RED_BOLD + "Rating: " + rating + " " + star + RESET);
            System.out.println();


            try {
                geradora.cria(inputStream, nomeArquivo, textoFigurinha, imagemFeedback);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }


            var conteudo = new Conteudo(titulo, urlImagem, imDbRating);
            conteudos.add(conteudo);

        }

        return conteudos;
    }
}
