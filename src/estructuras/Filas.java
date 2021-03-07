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
public class Filas {
    public String alfabeto;
    public String ID;
    public LinkedList<String> siguientes= new LinkedList<>();

    public Filas(String alfabeto, String ID, LinkedList siguientes) {
        this.alfabeto = alfabeto;
        this.ID = ID;
        this.siguientes = siguientes;
    }
    
    
}
