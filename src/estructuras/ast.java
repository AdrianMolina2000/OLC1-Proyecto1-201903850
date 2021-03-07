/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estructuras;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.LinkedList;

/**
 *
 * @author Adrian
 */
public class ast {

    public LinkedList<Filas> tablaSig = new LinkedList<>();
    public String nombre;
    public nodo arbol;
    public Hashtable<String, String> alfabeto = new Hashtable<>();
    public int id = 1;

    public ast(String nombre, nodo arbol) {
        this.nombre = nombre;
        this.arbol = arbol;
    }

    public void postOrden(nodo raiz) {
        if (raiz == null) {
            return;
        }
        postOrden(raiz.izquierda);
        postOrden(raiz.derecha);

//      NODOS HOJAS
        if (raiz.estado.equals("hoja")) {
            raiz.ID = this.id;
            this.id++;
//      PRIMEROS
            raiz.primeros.add(String.valueOf(raiz.ID));
//      ULTIMOS
            raiz.ultimos.add(String.valueOf(raiz.ID));
//      GUARDAR ALFABETO
            alfabeto.put(String.valueOf(raiz.ID), raiz.contenido);
        }

//      NODO OPERADORES
        if (raiz.contenido.equals("*")) {
            raiz.anulable = 'A';
            raiz.primeros = (LinkedList) raiz.izquierda.primeros.clone();
            raiz.ultimos = (LinkedList) raiz.izquierda.ultimos.clone();

//          TABLA DE SIGUIENTES
            for (String i : raiz.izquierda.ultimos) {
                boolean existente = false;
                for (Filas j : tablaSig) {
                    if (j.ID.equals(i)) {
                        existente = true;
                        for (String k : raiz.izquierda.primeros) {
                            if (j.siguientes.contains(k)) {

                            } else {
                                j.siguientes.add(k);
                            }
                        }
                    }
                }

                if (existente == false) {
                    tablaSig.add(new Filas(alfabeto.get(i), i, (LinkedList) raiz.izquierda.primeros.clone()));
                }
            }
        }
        if (raiz.contenido.equals("?")) {
            raiz.anulable = 'A';
            raiz.primeros = (LinkedList) raiz.izquierda.primeros.clone();
            raiz.ultimos = (LinkedList) raiz.izquierda.ultimos.clone();
        }

        if (raiz.contenido.equals("+")) {
            if (raiz.izquierda.anulable == 'N') {
                raiz.anulable = 'N';
            } else {
                raiz.anulable = 'A';
            }
            raiz.primeros = (LinkedList) raiz.izquierda.primeros.clone();
            raiz.ultimos = (LinkedList) raiz.izquierda.ultimos.clone();

//          TABLA DE SIGUIENTES
            for (String i : raiz.izquierda.ultimos) {
                boolean existente = false;
                for (Filas j : tablaSig) {
                    if (j.ID.equals(i)) {
                        existente = true;
                        for (String k : raiz.izquierda.primeros) {
                            if (j.siguientes.contains(k)) {

                            } else {
                                j.siguientes.add(k);
                            }
                        }
                    }
                }

                if (existente == false) {
                    tablaSig.add(new Filas(alfabeto.get(i), i, (LinkedList) raiz.izquierda.primeros.clone()));
                }
            }
        }

        if (raiz.contenido.equals("|")) {
//          ANULABILIDAD
            if (raiz.izquierda.anulable == 'A' || raiz.derecha.anulable == 'A') {
                raiz.anulable = 'A';
            } else {
                raiz.anulable = 'N';
            }
//          PRIMEROS
            raiz.izquierda.primeros.forEach((i) -> {
                raiz.primeros.add(i);
            });
            raiz.derecha.primeros.forEach((i) -> {
                raiz.primeros.add(i);
            });
//          ULTIMOS
            raiz.izquierda.ultimos.forEach((i) -> {
                raiz.ultimos.add(i);
            });
            raiz.derecha.ultimos.forEach((i) -> {
                raiz.ultimos.add(i);
            });
        }

        if (raiz.contenido.equals(".")) {
//          ANULABILIDAD
            if (raiz.izquierda.anulable == 'A' && raiz.derecha.anulable == 'A') {
                raiz.anulable = 'A';
            } else {
                raiz.anulable = 'N';
            }
//          PRIMEROS
            if (raiz.izquierda.anulable == 'A') {
                raiz.izquierda.primeros.forEach((i) -> {
                    raiz.primeros.add(i);
                });
                raiz.derecha.primeros.forEach((i) -> {
                    raiz.primeros.add(i);
                });
            } else {
                raiz.primeros = (LinkedList) raiz.izquierda.primeros.clone();
            }
//          ULTIMOS
            if (raiz.derecha.anulable == 'A') {
                raiz.izquierda.ultimos.forEach((i) -> {
                    raiz.ultimos.add(i);
                });
                raiz.derecha.ultimos.forEach((i) -> {
                    raiz.ultimos.add(i);
                });
            } else {
                raiz.ultimos = (LinkedList) raiz.derecha.ultimos.clone();
            }

//          TABLA DE SIGUIENTES
            for (String i : raiz.izquierda.ultimos) {
                boolean existente = false;
                for (Filas j : tablaSig) {
                    if (j.ID.equals(i)) {
                        existente = true;
                        for (String k : raiz.derecha.primeros) {
                            if (j.siguientes.contains(k)) {

                            } else {
                                j.siguientes.add(k);
                            }
                        }
                    }
                }

                if (existente == false) {
                    tablaSig.add(new Filas(alfabeto.get(i), i, (LinkedList) raiz.derecha.primeros.clone()));
                }
            }

        }

        if (raiz.estado.equals(
                "hoja")) {
            System.out.print(raiz.contenido + "<id:" + raiz.ID + ", " + raiz.anulable + ", " + raiz.primeros + ", " + raiz.ultimos + ">    ");
        } else {
            System.out.print(raiz.contenido + "<" + raiz.anulable + ", " + raiz.primeros + ", " + raiz.ultimos + ">    ");
        }

    }

    public void getTabla() {
        LinkedList<String> filaFinal = new LinkedList<>();
        filaFinal.add("--");
        tablaSig.add(new Filas(alfabeto.get(String.valueOf(id - 1)), String.valueOf((id - 1)), filaFinal));
        for (Filas k : this.tablaSig) {
            System.out.println(k.alfabeto + "|" + k.ID + "|" + k.siguientes);
        }
        graficarTabla("tablaSiguientes.jpg");
    }

    private void graficarTabla(String path) {
        FileWriter fichero = null;
        PrintWriter escritor;
        try {
            fichero = new FileWriter("tabla.dot");
            escritor = new PrintWriter(fichero);
            escritor.print(getCodigoGraphviz());
        } catch (Exception e) {
            System.err.println("Error al escribir el archivo tabla.dot");
        } finally {
            try {
                if (null != fichero) {
                    fichero.close();
                }
            } catch (Exception e2) {
                System.err.println("Error al cerrar el tabla.dot");
            }
        }
        try {
            Runtime rt = Runtime.getRuntime();
            rt.exec("dot -Tjpg -o " + path + " tabla.dot");
            //Esperamos medio segundo para dar tiempo a que la imagen se genere.
            //Para que no sucedan errores en caso de que se decidan graficar varios
            //árboles sucesivamente.
            Thread.sleep(500);
        } catch (Exception ex) {
            System.err.println("Error al generar la imagen para el archivo tabla.dot");
        }
    }

    /**
     * Método que retorna el código que grapviz usará para generar la imagen del
     * árbol binario de búsqueda.
     *
     * @return
     */
    private String getCodigoGraphviz() {
        return "digraph grafica{\n"
                + "rankdir=TB;\n"
                + "node [shape=plaintext];\n"
                + getCodigoInterno()
                + "}\n";
    }

    /**
     * Genera el código interior de graphviz, este método tiene la
     * particularidad de ser recursivo, esto porque recorrer un árbol de forma
     * recursiva es bastante sencillo y reduce el código considerablemente.
     *
     * @return
     */
    private String getCodigoInterno() {
        String etiqueta;
        etiqueta = "some_node ["
                + "label=<"
                + "<table border=\"0\" cellborder=\"1\" cellspacing=\"0\">";
        etiqueta += "<tr><td>Hoja</td><td>ID</td><td>Siguientes</td></tr>";
        for (Filas i : this.tablaSig) {

            etiqueta += "<tr><td>" + i.alfabeto + "</td><td>" + i.ID + "</td><td>" + i.siguientes + "</td></tr>";
        }

        etiqueta += "</table>>" + "];";

        return etiqueta;
    }

}
