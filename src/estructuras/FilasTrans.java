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
public class FilasTrans {
    public Estado estado;
    public LinkedList<String> terminales;

    public FilasTrans(Estado estado, LinkedList terminales) {
        this.estado = estado;
        this.terminales = terminales;
    }
}
