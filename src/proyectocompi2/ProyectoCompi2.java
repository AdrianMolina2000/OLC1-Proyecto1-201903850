/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectocompi2;

import estructuras.ast;
import java.io.FileInputStream;
import estructuras.nodo;
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
            pars=new analizadores.Sintactico(new analizadores.Lexico(new FileInputStream(path)));
            pars.parse();
            
            LinkedList<ast> arboles = pars.arboles;
            ast arbolito = arboles.getFirst();


            
            
            arbolito.postOrden(arbolito.arbol);
    
            
//            System.out.println(arbolito.arbol.contenido);
//            nodo a1 = arbolito.arbol.izquierda;
//            System.out.println(a1.derecha.contenido);
//            arbolito.postOrden(arbolito.arbol);
            
            
            
        } catch (Exception ex) {
            System.out.println("Error fatal en compilación de entrada.");
            System.out.println("Causa: "+ex.getCause());
        } 
    }
    
}
