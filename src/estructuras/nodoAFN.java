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
public class nodoAFN {

    public int inicio;
    public String mueve;
    public int fin;
    public String aceptacion = "";

    public nodoAFN(int inicio, String mueve, int fin) {
        this.inicio = inicio;
        this.mueve = mueve;
        this.fin = fin;
    }

    public void cambiar(String mueve, int fin) {
        this.mueve = mueve;
        this.fin = fin;
    }
}
