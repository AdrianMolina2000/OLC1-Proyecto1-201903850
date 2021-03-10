/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

/**
 *
 * @author Adrian
 */
public class Imagen {
    public String nombre;
    public String ruta;

    public Imagen(String nombre, String ruta) {
        this.nombre = nombre;
        this.ruta = ruta;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
