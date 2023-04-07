package org.example.alura;

import java.util.Objects;

public final class Conteudo {
    private final String titulo;
    private final String urlImagem;
    private String rating;

    public Conteudo(String titulo, String urlImagem, String rating) {
        this.titulo = titulo;
        this.urlImagem = urlImagem;
        this.rating = rating;
    }

    public Conteudo(String titulo, String urlImagem) {
        this.titulo = titulo;
        this.urlImagem = urlImagem;
    }

    public String titulo() {
        return titulo;
    }

    public String urlImagem() {
        return urlImagem;
    }

    public String rating() {
        return rating;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Conteudo) obj;
        return Objects.equals(this.titulo, that.titulo) &&
                Objects.equals(this.urlImagem, that.urlImagem) &&
                Objects.equals(this.rating, that.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titulo, urlImagem, rating);
    }

    @Override
    public String toString() {
        return "Conteudo[" +
                "titulo=" + titulo + ", " +
                "urlImagem=" + urlImagem + ", " +
                "rating=" + rating + ']';
    }

}
