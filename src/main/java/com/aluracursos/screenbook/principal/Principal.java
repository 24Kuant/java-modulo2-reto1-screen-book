package com.aluracursos.screenbook.principal;

import com.aluracursos.screenbook.client.ConsumoAPI;
import com.aluracursos.screenbook.model.Autor;
import com.aluracursos.screenbook.model.DatosLibro;
import com.aluracursos.screenbook.model.Libro;
import com.aluracursos.screenbook.model.ResultadosLibro;
import com.aluracursos.screenbook.service.ConvierteDatos;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private final String URL_BASE = "https://gutendex.com/books";

    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();

    private ConvierteDatos conversor = new ConvierteDatos();

    public void obtenInfo() {
        DatosLibro datos;
        String datoCapturado;
        String url;
        String json;

        System.out.println("salida");
        System.out.println("--------");
        System.out.println("----------------------------------------------------------------------------------------------\n\n");
        System.out.println("--------");
        System.out.println("--------  Bienvenidos a ScreenBook ");
        System.out.println("--------");
        System.out.println("--------");
        System.out.println("--------");

        /* */
        //buscar por nombre del libro
        System.out.println("Por favor escribe el nombre del libro que deseas buscar: ");
        datoCapturado = teclado.nextLine();  //probemos con la palabra amor/love/dick great
        url = URL_BASE + "/?search=" + datoCapturado.replace(" ", "%20");
        json = consumoApi.obtenerDatos(url);
        //System.out.println("json = " + json);
        datos = conversor.obtenerDatos(json, DatosLibro.class);
        System.out.println("1.- url: " + url + "; total de libros obtenidos: " + datos.total() + ". Link siguiente: " + datos.linkSiguiente() + ". Link anterior: " + datos.linkAnterior());
        System.out.println("----------------------------------------------------------------------------------------------\n\n");

        System.out.println("--------");
        System.out.println("--------Lista los datos basicos del resultado :");
        System.out.println("--------");
        System.out.println("Espere unos momentos....:");
        System.out.println("--------");

        datos.resultadosLibro().forEach(e -> {
            System.out.println("id: " + e.idLibro() + ", titulo: " + e.titulo() + ", autor: " + ( e.autor().size() > 0 ? e.autor().getFirst().nombre() : "Sin autor" ) +
                ", idiomas: " + e.idiomas() + ", descargas: " + e.descargas()
            );
        });
        /* */

        /* */
        //buscar libros escritos en diferentes idiomas, por ejemplo si quieres en ingles y en frances
        System.out.println("--------");
        System.out.println("----------------------------------------------------------------------------------------------\n\n");
        System.out.println("--------");
        System.out.println("Por favor escribe los idiomas que deseas buscar (separado por comas): ");
        datoCapturado = teclado.nextLine();  //probemos con los idiomas: español, ingles, frances:   es,en,fr
        url = URL_BASE + "/?languages=" + datoCapturado;
        json = consumoApi.obtenerDatos(url);
        //System.out.println("json = " + json);
        datos = conversor.obtenerDatos(json, DatosLibro.class);
        System.out.println("1.- url: " + url + "; total de libros obtenidos: " + datos.total() + ". Link siguiente: " + datos.linkSiguiente() + ". Link anterior: " + datos.linkAnterior());
        System.out.println("----------------------------------------------------------------------------------------------\n\n");

        System.out.println("--------");
        System.out.println("--------Filtra los libros con descargas entre 40000 y 50000 :");
        System.out.println("--------");
        System.out.println("Espere unos momentos....:");  //probemos con la palabra amor/love/ dick great
        System.out.println("--------");

        datos.resultadosLibro().stream()
                .filter(e -> e.descargas() > 35000 && e.descargas() < 50000)
                //.map( d -> new Libro(d))
                .sorted(Comparator.comparing(ResultadosLibro::idLibro).reversed())
                .map( d -> new Libro(d))
                .map(e -> {
                    String titulo = e.getTitulo().toUpperCase();
                    e.setTitulo(titulo);
                    return new Libro(e.getId(), titulo, e.getAutor(), e.getIdiomas(), e.getDescargas(), e.isCopyright());
                })
                .forEach(System.out::println);
         /* */

        /* */
        //buscar libros escritos a partir del año selecionado
        System.out.println("--------");
        System.out.println("----------------------------------------------------------------------------------------------\n\n");
        System.out.println("--------");
        System.out.println("Por favor escribe el año a partir del cual fue escrito el libro : ");
        datoCapturado = teclado.nextLine();  //probemos con el año 2001 / 1954 / 1992
        url = URL_BASE + "/?author_year_start=" + datoCapturado;
        json = consumoApi.obtenerDatos(url);
        //System.out.println("json = " + json);
        datos = conversor.obtenerDatos(json, DatosLibro.class);
        System.out.println("1.- url: " + url + "; total de libros obtenidos: " + datos.total() + ". Link siguiente: " + datos.linkSiguiente() + ". Link anterior: " + datos.linkAnterior());
        System.out.println("----------------------------------------------------------------------------------------------\n\n");

        System.out.println("--------");
        System.out.println("--------Filtra los libros a partir del año seleccionado :");
        System.out.println("--------");
        System.out.println("Espere unos momentos....:");  //probemos con la palabra amor/love/ dick great
        System.out.println("--------");

        List<Libro> libros = datos.resultadosLibro().stream()
                .filter(e -> e.descargas() > 400 && e.descargas() < 1400)
                .map( d -> new Libro(d))
                //.sorted(Comparator.comparing(ResultadosLibro::idLibro).reversed())
                .map(e -> {
                    String titulo = e.getTitulo().toUpperCase();
                    e.setTitulo(titulo);
                    return new Libro(e.getId(), titulo, e.getAutor(), e.getIdiomas(), e.getDescargas(), e.isCopyright());
                })
                .collect(Collectors.toList());

        //System.out.println(libros);
        libros.stream()
                .filter(e -> e.getId() % 2 == 0)
                .sorted(Comparator.comparing(Libro::getTitulo))
                .forEach(System.out::println);

        System.out.println("--------");
        System.out.println("----------------------------------------------------------------------------------------------\n\n");
        System.out.println("--------");
        System.out.println("Por favor de los libros encontrados escribe el nombre del libro que deseas leer : ");
        var libroABuscar = teclado.nextLine();  //probemos con la palabra the / amor / love / friend

        Optional<Libro> libroBuscado = libros.stream()  //optional, son resultados que puede o no contener resultados, siempre va acompañado con isPresent().
                .filter(e -> e.getTitulo().toUpperCase().contains(libroABuscar.toUpperCase()))
                //.findAny() //trae la primera coincidencia, pero no siempre es la misma, es la primera de forma aleatoria
                .findFirst();//este siempre regresa en la primera coincdencia, la misma respuesta. y esta es una operacion FINAL

        if ( libroBuscado.isPresent()) {
            System.out.println("Libro encontrado:");
            System.out.println("Los datos son: " + libroBuscado.get());  //trae todos los dato.  y esto trae solo un dato:  episodioBuscado.get().getTitulo()
        } else {
            System.out.println("Libro no encontrado..."); //ejemplo : usar el titulo bastards  (y esta como Bastards
        }
        /* */


        /* */
        //buscar libros escritos con copyright (true o false)
        System.out.println("--------");
        System.out.println("----------------------------------------------------------------------------------------------\n\n");
        System.out.println("--------");
        System.out.println("Por favor presione cualquier tecla y luego [enter] para ver los TOP 10 libros MAS descargados, TOP 10 CON copyright, y TOP 10 SIN copyright: ");
        var temporal = teclado.nextLine();
        var conDerechos = "true,false";
        url = URL_BASE + "/?copyright=" + conDerechos;
        json = consumoApi.obtenerDatos(url);
        //System.out.println("json = " + json);
        datos = conversor.obtenerDatos(json, DatosLibro.class);
        System.out.println("1.- url: " + url + "; total de libros obtenidos: " + datos.total() + ". Link siguiente: " + datos.linkSiguiente() + ". Link anterior: " + datos.linkAnterior());
        System.out.println("----------------------------------------------------------------------------------------------\n\n");
        System.out.println("Espere unos momentos....:");
        System.out.println("--------");

        System.out.println("--------");
        System.out.println("--------Filtra los TOP 10 libros Mas Descargados :");
        System.out.println("--------");
        System.out.println("Espere unos momentos....:");  //probemos con la palabra amor/love/ dick great
        System.out.println("--------");

        datos.resultadosLibro().stream()
                //.filter(e -> e.descargas() > 400 && e.descargas() < 1400)
                //.map( d -> new Libro(d))
                .sorted(Comparator.comparing(ResultadosLibro::descargas).reversed())
                .map(e -> {
                    String titulo = e.titulo().toUpperCase();
                    return new Libro(e.idLibro(), titulo, e.autor(), e.idiomas(), e.descargas(), e.copyright());
                })
                .limit(10)
                .forEach(System.out::println);

        System.out.println("--------");
        System.out.println("--------Filtra los TOP 10 libros Con Copyright :");
        System.out.println("--------");
        datos.resultadosLibro().stream()
                .filter(e -> e.copyright() == true)
                //.map( d -> new Libro(d))
                .sorted(Comparator.comparing(ResultadosLibro::descargas).reversed())
                .map(e -> {
                    String titulo = e.titulo().toUpperCase();
                    return new Libro(e.idLibro(), titulo, e.autor(), e.idiomas(), e.descargas(), e.copyright());
                })
                .limit(10)
                .forEach(System.out::println);

        System.out.println("--------");
        System.out.println("--------Filtra los TOP 10 libros Sin Copyright :");
        System.out.println("--------");
        datos.resultadosLibro().stream()
                .filter(e -> e.copyright() == false)
                //.map( d -> new Libro(d))
                .sorted(Comparator.comparing(ResultadosLibro::descargas).reversed())
                .map(e -> {
                    String titulo = e.titulo().toUpperCase();
                    return new Libro(e.idLibro(), titulo, e.autor(), e.idiomas(), e.descargas(), e.copyright());
                })
                .limit(10)
                .forEach(System.out::println);
         /* */

        /* */
        //buscar libros escritos en un rango de años
        System.out.println("--------");
        System.out.println("----------------------------------------------------------------------------------------------\n\n");
        System.out.println("--------");
        System.out.println("Filtra los libros por un rango de Años y mostraremos las estadisticas:");
        System.out.println("--------");
        System.out.println("Por favor escribe el año inical cuando fue escrito el libro : ");
        var yearStart = teclado.nextLine();
        System.out.println("Por favor escribe el año final cuando fue escrito el libro : ");
        var yearEnd = teclado.nextLine();
        url = URL_BASE + "/?author_year_start=" + yearStart + "&author_year_end=" + yearEnd;
        json = consumoApi.obtenerDatos(url);
        //System.out.println("json = " + json);
        datos = conversor.obtenerDatos(json, DatosLibro.class);
        System.out.println("1.- url: " + url + "; total de libros obtenidos: " + datos.total() + ". Link siguiente: " + datos.linkSiguiente() + ". Link anterior: " + datos.linkAnterior());
        System.out.println("----------------------------------------------------------------------------------------------\n\n");

        System.out.println("--------");
        System.out.println("--------Obteniendo las Estadisticas :");
        System.out.println("--------");
        System.out.println("Espere unos momentos....:");
        System.out.println("--------");

        IntSummaryStatistics estadisticas = datos.resultadosLibro().stream()
                .filter(e -> e.descargas() > 0)
                .collect(Collectors.summarizingInt(ResultadosLibro::descargas));
        System.out.println(estadisticas);  //probamos con año inicial: 1990 y año final: 2024

        //personalizamos la salida, pq noqueremos ver la suma de todas las evaluaciones:
        System.out.println("Media de las Descargas de Libros: " + estadisticas.getAverage());  //probamos con game of thrones
        System.out.println("El Libro con Más Descargas tiene: " + estadisticas.getMax());
        System.out.println("El Libro con Menor Descargas tiene: " + estadisticas.getMin());
        /* */
        System.out.println("--------");
        System.out.println("----------------------------------------------------------------------------------------------\n\n");
        System.out.println("--------");
        System.out.println("--------  Gracias por usar ScreenBook ");
        System.out.println("--------");

    }

}
