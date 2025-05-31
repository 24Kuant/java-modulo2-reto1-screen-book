package com.aluracursos.screenbook.model;

import java.util.ArrayList;
import java.util.List;

public class Libro {
    private Integer id;
    private String titulo;
    private List<Autor> autor = new ArrayList<>();
    private List<String> idiomas = new ArrayList<>();
    private Integer descargas;
    private boolean copyright;

    //Contructor
    public Libro(Integer id, String titulo, List<Autor> autor, List<String> idiomas, Integer descargas, boolean copyright) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.idiomas = idiomas;
        this.descargas = descargas;
        this.copyright = copyright;
    }

    public Libro(ResultadosLibro libro) {
        this.id = libro.idLibro();
        this.titulo = libro.titulo();
        this.autor = libro.autor();
        this.idiomas = libro.idiomas();
        this.descargas = libro.descargas();
        this.copyright = copyright;
    }

    //getters y setters

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public List<Autor> getAutor() {
        return autor;
    }

    public List<String> getIdiomas() {
        return idiomas;
    }

    public Integer getDescargas() {
        return descargas;
    }

    public boolean isCopyright() {
        return copyright;
    }

    @Override
    public String toString() {
        return  "id=" + this.id +
                ", titulo='" + this.titulo +
                ", autor=" + this.autor +
                ", idiomas=" + this.idiomas +
                ", descargas=" + this.descargas +
                ", copyright=" + this.copyright;
    }
}
