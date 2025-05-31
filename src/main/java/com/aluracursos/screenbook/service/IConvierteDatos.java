package com.aluracursos.screenbook.service;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);   // es de tipo de datos generico    <T> T obtenerDatos(String json, Class<T> clase);   // es de tipo de datos generico
}
