/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estructuras;

import java.io.File;
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
    public LinkedList<FilasTrans> tablaTrans = new LinkedList<>();
    public LinkedList<String> alfa = new LinkedList<>();
    public LinkedList<Estado> estados = new LinkedList<>();
    public String nombre;
    public nodo arbol;
    public Hashtable<String, String> alfabeto = new Hashtable<>();
    public int id = 1;
    public int count = 0;

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

    public void transiciones() {
        for (String k : alfabeto.keySet()) {
            if (alfa.contains(alfabeto.get(k))) {
            } else {
                alfa.add(alfabeto.get(k));
            }
        }
        Estado inicial = new Estado("S" + count, this.arbol.primeros);
        count++;
        estados.add(inicial);
        transiciones2(inicial);
        graficarTablaTrans("tablaTrans.jpg");
    }

    public void transiciones2(Estado estado) {
        LinkedList<String> terminales = new LinkedList<>();
        Estado nuevo = new Estado("S-1", terminales);
        for (String i : estado.siguientes) {
            for (Filas j : tablaSig) {
                LinkedList<Estado> aux = new LinkedList<>();
                if (i.equals(j.ID)) {
                    for (Estado k : estados) {
                        if (k.siguientes.containsAll(j.siguientes) && j.siguientes.containsAll(k.siguientes)) {

                        } else {
                            nuevo = new Estado("S" + count, j.siguientes);
                            aux.add(nuevo);
                            count++;

                        }

                    }
                    estados.addAll(aux);

                }
            }
            if (alfa.contains(alfabeto.get(i))) {
                terminales.add(alfabeto.get(i));
            }

        }
        tablaTrans.add(new FilasTrans(estado, terminales));
        if (terminales.contains("#")) {
            return;
        }
        transiciones2(nuevo);
    }

    public void getTabla() {
        LinkedList<String> filaFinal = new LinkedList<>();
        filaFinal.add("--");
        tablaSig.add(new Filas(alfabeto.get(String.valueOf(id - 1)), String.valueOf((id - 1)), filaFinal));
        System.out.println("");
        for (Filas k : this.tablaSig) {
            System.out.println(k.alfabeto + "|" + k.ID + "|" + k.siguientes);
        }
        graficarTabla(nombre + ".jpg");
    }

    private void graficarTabla(String path) {
        File imagenes = new File("Imagenes");
        File siguientes = new File("Imagenes/Siguientes");
        if (!imagenes.exists()) {
            if (imagenes.mkdirs()) {
                System.out.println("Directorio creado");
            } else {
                System.out.println("Error al crear directorio");
            }
        }
        if (!siguientes.exists()) {
            if (!siguientes.mkdirs()) {
                System.out.println("Error al crear directorio");
            }
        }

        FileWriter fichero = null;
        PrintWriter escritor;

        try {
            fichero = new FileWriter("Imagenes/Siguientes/" + nombre + ".dot");
            escritor = new PrintWriter(fichero);
            escritor.print(getCodigoTabla());
        } catch (Exception e) {
            System.err.println("Error al escribir el archivo " + nombre + ".dot");
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
            rt.exec("dot -Tjpg -o " + "Imagenes/Siguientes/" + path + " Imagenes/Siguientes/" + nombre + ".dot");
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
     * AST.
     *
     * @return
     */
    private String getCodigoTabla() {
        return "digraph grafica{\n"
                + "rankdir=TB;\n"
                + "node [shape=plaintext];\n"
                + getCodigoTablaC()
                + "}\n";
    }

    /**
     * Genera el código interior de graphviz, este método tiene la
     * particularidad de ser recursivo, esto porque recorrer un árbol de forma
     * recursiva es bastante sencillo y reduce el código considerablemente.
     *
     * @return
     */
    private String getCodigoTablaC() {
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

    private void graficarTablaTrans(String path) {

        FileWriter fichero = null;
        PrintWriter escritor;
        try {
            fichero = new FileWriter("tablaTrans.dot");
            escritor = new PrintWriter(fichero);
            escritor.print(getCodigoTablaTrans());
        } catch (Exception e) {
            System.err.println("Error al escribir el archivo TablaTrans.dot");
        } finally {
            try {
                if (null != fichero) {
                    fichero.close();
                }
            } catch (Exception e2) {
                System.err.println("Error al cerrar el TablaTrans.dot");
            }
        }
        try {
            Runtime rt = Runtime.getRuntime();
            rt.exec("dot -Tjpg -o " + path + " TablaTrans.dot");
            //Esperamos medio segundo para dar tiempo a que la imagen se genere.
            //Para que no sucedan errores en caso de que se decidan graficar varios
            //árboles sucesivamente.
            Thread.sleep(500);
        } catch (Exception ex) {
            System.err.println("Error al generar la imagen para el archivo TablaTrans.dot");
        }
    }

    /**
     * Método que retorna el código que grapviz usará para generar la imagen del
     * AST.
     *
     * @return
     */
    private String getCodigoTablaTrans() {
        return "digraph grafica{\n"
                + "rankdir=TB;\n"
                + "node [shape=plaintext];\n"
                + getCodigoTablaTrans2()
                + "}\n";
    }

    /**
     * Genera el código interior de graphviz, este método tiene la
     * particularidad de ser recursivo, esto porque recorrer un árbol de forma
     * recursiva es bastante sencillo y reduce el código considerablemente.
     *
     * @return
     */
    private String getCodigoTablaTrans2() {
        String etiqueta;
        etiqueta = "some_node ["
                + "label=<"
                + "<table border=\"0\" cellborder=\"1\" cellspacing=\"0\">";

        etiqueta += "<tr><td>Estado</td>";
        for (String i : alfa) {
            if (i.equals("#")) {

            } else {
                etiqueta += "<td>" + i + "</td>";
            }
        }

        etiqueta += "</tr>";
        for (FilasTrans i : tablaTrans) {
            etiqueta += "<tr><td>" + i.estado.numero + i.estado.siguientes + "</td><td>" + i.terminales + "</td><td></td></tr>";
        }

        etiqueta += "</table>>" + "];";

        return etiqueta;
    }

}
