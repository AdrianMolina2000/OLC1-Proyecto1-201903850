/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectocompi2;

import Interfaz.Principal;
import estructuras.ast;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Hashtable;
import java.util.LinkedList;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author Adrian
 */
public class ProyectoCompi2 {

    public static void main(String[] args) {
        Principal interfaz = new Principal();
//        interpretar("pruebamedia.olc");
    }

    public static void interpretar(String path) {
        analizadores.Sintactico pars;
        try {
            pars = new analizadores.Sintactico(new analizadores.Lexico(new FileInputStream(path)));
            pars.parse();

            LinkedList<ast> arboles = pars.arboles;
            for (ast arbolito : arboles) {
                arbolito.postOrden(arbolito.arbol);
                arbolito.arbol.graficarArbolAST(arbolito.nombre + ".jpg", arbolito.nombre);
                arbolito.getTabla();
                arbolito.transiciones();
            }
        } catch (Exception ex) {
            System.out.println("Error fatal en compilación de entrada.");
            System.out.println("Causa: " + ex.getCause());
        }
    }

}
