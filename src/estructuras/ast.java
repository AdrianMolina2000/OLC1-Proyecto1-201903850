/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estructuras;

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
            raiz.primeros.add(String.valueOf(raiz.ID));
        }

//      NODO OPERADORES
        if (raiz.contenido.equals("*")) {
            raiz.anulable = 'A';
        }
        if (raiz.contenido.equals("?")) {
            raiz.anulable = 'A';
        }

        if (raiz.contenido.equals("+")) {
            if (raiz.izquierda.anulable == 'N') {
                raiz.anulable = 'N';
            } else {
                raiz.anulable = 'A';
            }
        }

        if (raiz.contenido.equals("|")) {
            if (raiz.izquierda.anulable == 'A' || raiz.derecha.anulable == 'A') {
                raiz.anulable = 'A';
            } else {
                raiz.anulable = 'N';
            }
        }

        if (raiz.contenido.equals(".")) {
            if (raiz.izquierda.anulable == 'A' && raiz.derecha.anulable == 'A') {
                raiz.anulable = 'A';
            } else {
                raiz.anulable = 'N';
            }
        }

//      PRIMEROS
        
        







        if (raiz.estado.equals("hoja")) {
            System.out.print(raiz.contenido + "<id:" + raiz.ID + ", " + raiz.anulable + ">    ");
        } else {
            System.out.print(raiz.contenido + "<" + raiz.anulable + ">    ");
        }
    }

}
