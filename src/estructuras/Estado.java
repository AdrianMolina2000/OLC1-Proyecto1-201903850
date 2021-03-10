/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estructuras;

import java.util.LinkedList;

/**
 *
 * @author Adrian
 */
public class Estado {
    public String numero;
    public LinkedList<String> siguientes= new LinkedList<>();
    public String aceptacion = "";

    public Estado(String numero, LinkedList lista) {
        this.numero = numero;
        this.siguientes = lista;
    }
}
