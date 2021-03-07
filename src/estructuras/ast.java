/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estructuras;

import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Adrian
 */
public class ast {

    public String nombre;
    public nodo arbol;
    public int id = 1;

    public ast(String nombre, nodo arbol) {
        this.nombre = nombre;
        this.arbol = arbol;
    }

    public void postOrden(nodo raiz) {
        if (raiz == null) {
            return;
        }
        postOrden(raiz.izquierda);
        postOrden(raiz.derecha);

//      NODOS HOJAS
        if (raiz.estado.equals("hoja")) {
            raiz.ID = this.id;
            this.id++;
//      PRIMEROS
            raiz.primeros.add(String.valueOf(raiz.ID));
//      ULTIMOS
            raiz.ultimos.add(String.valueOf(raiz.ID));
        }

//      NODO OPERADORES
        if (raiz.contenido.equals("*")) {
            raiz.anulable = 'A';
            raiz.primeros = (LinkedList) raiz.izquierda.primeros.clone();
            raiz.ultimos = (LinkedList) raiz.izquierda.ultimos.clone();
        }
        if (raiz.contenido.equals("?")) {
            raiz.anulable = 'A';
            raiz.primeros = (LinkedList) raiz.izquierda.primeros.clone();
            raiz.ultimos = (LinkedList) raiz.izquierda.ultimos.clone();
        }

        if (raiz.contenido.equals("+")) {
            if (raiz.izquierda.anulable == 'N') {
                raiz.anulable = 'N';
            } else {
                raiz.anulable = 'A';
            }
            raiz.primeros = (LinkedList) raiz.izquierda.primeros.clone();
            raiz.ultimos = (LinkedList) raiz.izquierda.ultimos.clone();
        }

        
        if (raiz.contenido.equals("|")) {
//          ANULABILIDAD
            if (raiz.izquierda.anulable == 'A' || raiz.derecha.anulable == 'A') {
                raiz.anulable = 'A';
            } else {
                raiz.anulable = 'N';
            }
//          PRIMEROS
            for (String i : raiz.izquierda.primeros) {
                raiz.primeros.add(i);
            }
            for (String i : raiz.derecha.primeros) {
                raiz.primeros.add(i);
            }
//          ULTIMOS
            for (String i : raiz.izquierda.ultimos) {
                raiz.ultimos.add(i);
            }
            for (String i : raiz.derecha.ultimos) {
                raiz.ultimos.add(i);
            }
        }

        if (raiz.contenido.equals(".")) {
//          ANULABILIDAD
            if (raiz.izquierda.anulable == 'A' && raiz.derecha.anulable == 'A') {
                raiz.anulable = 'A';
            } else {
                raiz.anulable = 'N';
            }
//          PRIMEROS
            if (raiz.izquierda.anulable == 'A') {
                for (String i : raiz.izquierda.primeros) {
                    raiz.primeros.add(i);
                }
                for (String i : raiz.derecha.primeros) {
                    raiz.primeros.add(i);
                }
            } else {
                raiz.primeros = (LinkedList) raiz.izquierda.primeros.clone();
            }
//          ULTIMOS
            if (raiz.derecha.anulable == 'A') {
                for (String i : raiz.izquierda.ultimos) {
                    raiz.ultimos.add(i);
                }
                for (String i : raiz.derecha.ultimos) {
                    raiz.ultimos.add(i);
                }
            } else {
                raiz.ultimos = (LinkedList) raiz.derecha.ultimos.clone();
            }
        }

        if (raiz.estado.equals("hoja")) {
            System.out.print(raiz.contenido + "<id:" + raiz.ID + ", " + raiz.anulable + ", " + raiz.primeros + ", " + raiz.ultimos + ">    ");
        } else {
            System.out.print(raiz.contenido + "<" + raiz.anulable + ", " + raiz.primeros + ", " + raiz.ultimos + ">    ");
        }
    }

}
