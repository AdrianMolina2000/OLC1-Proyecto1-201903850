/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estructuras;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.LinkedList;

/**
 *
 * @author Adrian
 */
public class AFND {

    String anterior = "";

    public LinkedList<nodoAFN> recursivoAFND(nodo nodo) {
        LinkedList<nodoAFN> listaTemp = new LinkedList<>();
        int contador = 0;
        if (nodo != null) {
            LinkedList<nodoAFN> izquierda = recursivoAFND(nodo.izquierda);
            LinkedList<nodoAFN> derecha = recursivoAFND(nodo.derecha);
            if (nodo.estado.equals("hoja")) {
                listaTemp.add(new nodoAFN(contador, nodo.contenido, (contador + 1)));
                listaTemp.add(new nodoAFN((contador + 1), "nada", -200));
                return listaTemp;

            } else {
                switch (nodo.contenido) {
                    case "+":
                        listaTemp.add(new nodoAFN(contador, "ε", (contador + 1)));
                        izquierda = sumar(izquierda, 1);
                        izquierda.getLast().cambiar("ε", izquierda.getFirst().inicio);
                        listaTemp.addAll(izquierda);
                        int contador2 = obtenerTamano(izquierda) + 1;
                        listaTemp.add(new nodoAFN(izquierda.getLast().inicio, "ε", contador2));
                        listaTemp.add(new nodoAFN(contador2, "nada", -200));
                        anterior = "+";
                        return listaTemp;
                    case "*":

                        listaTemp.add(new nodoAFN(contador, "ε", (contador + 1)));
                        izquierda = sumar(izquierda, 1);
                        izquierda.getLast().cambiar("ε", izquierda.getFirst().inicio);
                        listaTemp.addAll(izquierda);

                        contador2 = obtenerTamano(izquierda) + 1;
                        listaTemp.add(new nodoAFN(izquierda.getLast().inicio, "ε", contador2));
                        listaTemp.add(new nodoAFN(contador, "ε", contador2));
                        listaTemp.add(new nodoAFN(contador2, "nada", -200));
                        anterior = "*";
                        return listaTemp;
                    case "?":

                        listaTemp.add(new nodoAFN(contador, "ε", (contador + 1)));
                        izquierda = sumar(izquierda, 1);

                        contador2 = obtenerTamano(izquierda) + 1;
                        izquierda.getLast().cambiar("ε", contador2);
                        listaTemp.addAll(izquierda);

                        listaTemp.add(new nodoAFN(contador, "ε", contador2));
                        listaTemp.add(new nodoAFN(contador2, "nada", -200));
                        anterior = "?";
                        return listaTemp;
                    case ".":
                        if (!nodo.derecha.contenido.equalsIgnoreCase("#")) {
                            izquierda.removeLast();
                            contador2 = obtenerTamano(izquierda);
                            derecha = sumar(derecha, contador2);
                            listaTemp.addAll(izquierda);
                            listaTemp.addAll(derecha);
                            anterior = ".";
                            return listaTemp;
                        }
                        izquierda.removeLast();
                        return izquierda;
                    case "|":
                        listaTemp.add(new nodoAFN(contador, "ε", (contador + 1)));
                        izquierda = sumar(izquierda, 1);
                        contador2 = obtenerTamano(izquierda) + 1;
                        derecha = sumar(derecha, contador2);
                        listaTemp.add(new nodoAFN(contador, "ε", contador2));
                        contador2 += obtenerTamano(derecha);

                        izquierda.getLast().cambiar("ε", contador2);
                        derecha.getLast().cambiar("ε", contador2);
                        listaTemp.addAll(izquierda);
                        listaTemp.addAll(derecha);
                        listaTemp.add(new nodoAFN(contador2, "nada", -200));
                        anterior = "|";
                        return listaTemp;
                }
            }
        }
        return listaTemp;
    }

    public LinkedList<nodoAFN> sumar(LinkedList<nodoAFN> tmpo, int x) {
        for (nodoAFN i : tmpo) {
            i.inicio += x;
            i.fin += x;
        }
        return tmpo;
    }

    public int obtenerTamano(LinkedList<nodoAFN> tmpo) {
        int tamano = 0;
        LinkedList<String> inicios = new LinkedList<>();

        for (nodoAFN i : tmpo) {
            if (!inicios.contains(String.valueOf(i.inicio))) {
                inicios.add(String.valueOf(i.inicio));
            }
        }
        return (inicios.size());
    }

    public void graficarAFND(String path, LinkedList lista) {
        File imagenes = new File("Imagenes");
        File transicion = new File("Imagenes/AFND_201903850");
        if (!imagenes.exists()) {
            if (imagenes.mkdirs()) {
                System.out.println("Directorio creado");
            } else {
                System.out.println("Error al crear directorio");
            }
        }
        if (!transicion.exists()) {
            if (!transicion.mkdirs()) {
                System.out.println("Error al crear directorio");
            }
        }

        FileWriter fichero = null;
        PrintWriter escritor;
        try {
            fichero = new FileWriter("Imagenes/AFND_201903850/" + path + ".dot");
            escritor = new PrintWriter(fichero);
            escritor.print(getCodigoAFND(lista));
        } catch (Exception e) {
            System.err.println("Error al escribir el archivo" + path + ".dot");
        } finally {
            try {
                if (null != fichero) {
                    fichero.close();
                }
            } catch (Exception e2) {
                System.err.println("Error al cerrar el" + path + ".dot");
            }
        }
        try {
            Runtime rt = Runtime.getRuntime();
            rt.exec("dot -Tjpg -o " + "Imagenes/AFND_201903850/" + path + " Imagenes/AFND_201903850/" + path + ".dot");
            //Esperamos medio segundo para dar tiempo a que la imagen se genere.
            //Para que no sucedan errores en caso de que se decidan graficar varios
            //árboles sucesivamente.
            Thread.sleep(500);
        } catch (Exception ex) {
            System.err.println("Error al generar la imagen para el archivo" + path + ".dot");
        }
    }

    /**
     * Método que retorna el código que grapviz usará para generar la imagen del
     * AST.
     *
     * @return
     */
    private String getCodigoAFND(LinkedList lista) {
        return "digraph grafica{\n"
                + "rankdir=LR;\n"
                + "node [shape =circle, style=filled, fillcolor=seashell2];\n"
                + getCodigoAFND2(lista)
                + "}\n";
    }

    /**
     * Genera el código interior de graphviz, este método tiene la
     * particularidad de ser recursivo, esto porque recorrer un árbol de forma
     * recursiva es bastante sencillo y reduce el código considerablemente.
     *
     * @return
     */
    private String getCodigoAFND2(LinkedList<nodoAFN> lista) {
        String etiqueta = "";

        for (nodoAFN i : lista) {
//            if (null != i.getAceptacion()) {
//                aux += "S" + i.inicio + ";\n";
//                aux += "S" + i.fin + "[shape=\"doublecircle\", style= filled,color=\"#098e3\"]; \n";
//            } else {
//            etiqueta += "S" + i.inicio + ";\n";
//            }

        }
        for (nodoAFN x : lista) {
            if (x.mueve.contains("\"")) {
                etiqueta += +x.inicio + "->" + x.fin + " [label =" + x.mueve + "];\n";
            } else {
                etiqueta += +x.inicio + "->" + x.fin + " [label =\"" + x.mueve + "\"];\n";
            }
        }
        return etiqueta;
    }

}
