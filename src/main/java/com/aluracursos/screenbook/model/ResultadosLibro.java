package com.aluracursos.screenbook.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ResultadosLibro(
            @JsonAlias("id") Integer idLibro,
            @JsonAlias("title") String titulo,
            @JsonAlias("authors")  List<Autor> autor,
            @JsonAlias("summaries") List<String> resumen,
            @JsonAlias("translators") List<Autor> traductor,  //ejemplos: Metamorphosis, Crime and Punishment, Thus Spake Zarathustra: A Book for All and Non
            @JsonAlias("languages") List<String> idiomas,
            @JsonAlias("copyright") boolean copyright,
            @JsonAlias("media_type") String tipoMedio,
            @JsonAlias("download_count") Integer descargas
        ) {
}
