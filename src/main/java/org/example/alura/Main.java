package org.example.alura;

import java.io.File;

public class Main {


    public static void main(String[] args) throws Exception {

        API api = API.IMDB_TOP_MOVIES;

        String url = api.getUrl();
        ExtratorDeConteudo extrator = api.getExtrator();

        var http = new ClienteHttp();
        String json = http.buscaDados(url);

        var diretorio = new File("figurinhas/");
        diretorio.mkdir();

        extrator.extraiConteudos(json);

    }

}
