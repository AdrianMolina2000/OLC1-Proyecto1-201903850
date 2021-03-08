/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectocompi2;

import estructuras.Estado;
import estructuras.FilasTrans;
import estructuras.ast;
import java.io.File;
import java.io.FileInputStream;
import java.util.Hashtable;
import java.util.LinkedList;

/**
 *
 * @author Adrian
 */
public class ProyectoCompi2 {

    public static void main(String[] args) {
        interpretar("prueba.txt");
    }

    private static void interpretar(String path) {
        analizadores.Sintactico pars;
        try {
            pars = new analizadores.Sintactico(new analizadores.Lexico(new FileInputStream(path)));
            pars.parse();

            LinkedList<ast> arboles = pars.arboles;
            ast arbolito = arboles.get(0);
//
//              
            arbolito.postOrden(arbolito.arbol);
            arbolito.arbol.graficarArbolAST(arbolito.nombre + ".jpg", arbolito.nombre);
            arbolito.getTabla();
            arbolito.transiciones();

        } catch (Exception ex) {
            System.out.println("Error fatal en compilación de entrada.");
            System.out.println("Causa: " + ex.getCause());
        }
    }

}
