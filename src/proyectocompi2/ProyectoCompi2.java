/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectocompi2;

import java.io.FileInputStream;
import estructuras.nodo;
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
        } catch (Exception ex) {
            System.out.println("Error fatal en compilación de entrada.");
            System.out.println("Causa: "+ex.getCause());
        } 
    }
    
}
