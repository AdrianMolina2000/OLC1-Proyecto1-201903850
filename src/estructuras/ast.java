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

    public ast(String nombre, nodo arbol) {
        this.nombre = nombre;
        this.arbol = arbol;
    }
    
    public void postOrden(nodo raiz)
    {
        if( raiz == null )
            return;
        postOrden(raiz.izquierda);
        postOrden(raiz.derecha);
        System.out.print(raiz.contenido + " ");
    }
    
    public void inOrden(nodo raiz)
    {
        if( raiz == null )
            return;
        postOrden(raiz.izquierda);
        System.out.print(raiz.contenido + " ");
        postOrden(raiz.derecha);
    }
}
