package estructuras;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.LinkedList;

public class nodo {

    public String contenido;
    public nodo izquierda;
    public nodo derecha;
    public String estado;
    public String tipo = "";
    public int ID;
    public int conta;
    public char anulable;
    public LinkedList<String> primeros = new LinkedList<>();
    public LinkedList<String> ultimos = new LinkedList<>();

    public nodo(String contenido, nodo izquierda, nodo derecha, int conta) {
        this.contenido = contenido;
        this.izquierda = izquierda;
        this.derecha = derecha;
        this.estado = "operacion";
        this.conta = conta;
    }

    public nodo(String contenido, int conta, String tipo) {
        this.contenido = contenido;
        this.estado = "hoja";
        this.anulable = 'N';
        this.conta = conta;
        this.tipo = tipo;
    }

    public void graficarArbolAST(String path, String n) {
        File imagenes = new File("Imagenes");
        File arbol = new File("Imagenes/Arbol");
        if (!imagenes.exists()) {
            if (!imagenes.mkdirs()) {
                System.out.println("Error al crear directorio");
            }
        }
        if (!arbol.exists()) {
            if (!arbol.mkdirs()) {
                System.out.println("Error al crear directorio");
            }
        }

        FileWriter fichero = null;
        PrintWriter escritor;
        try {
            fichero = new FileWriter("Imagenes/Arbol/" + n + ".dot");
            escritor = new PrintWriter(fichero);
            escritor.print(CuerpoGraphvizAST());
        } catch (Exception e) {
            System.err.println("Error al escribir el archivo " + n + ".dot");
        } finally {
            try {
                if (null != fichero) {
                    fichero.close();
                }
            } catch (Exception e2) {
                System.err.println("Error al cerrar el archivo " + n + ".dot");
            }
        }
        try {
            Runtime rt = Runtime.getRuntime();
            rt.exec("dot -Tjpg -o " + "Imagenes/Arbol/" + path + " Imagenes/Arbol/" + n + ".dot");
            //Esperamos medio segundo para dar tiempo a que la imagen se genere.
            //Para que no sucedan errores en caso de que se decidan graficar varios
            //árboles sucesivamente.
            Thread.sleep(500);
        } catch (Exception ex) {
            System.err.println("Error al generar la imagen para el archivo " + n + ".dot");
        }
    }

    /**
     * Método que retorna el código que grapviz usará para generar la imagen del
     * árbol binario de búsqueda.
     *
     * @return
     */
    private String CuerpoGraphvizAST() {
        return "digraph grafica{\n"
                + "rankdir=TB;\n"
                + "node [shape = record, style=filled, fillcolor=seashell2];\n"
                + CodigoInternoAST()
                + "}\n";
    }

    /**
     * Genera el código interior de graphviz, este método tiene la
     * particularidad de ser recursivo, esto porque recorrer un árbol de forma
     * recursiva es bastante sencillo y reduce el código considerablemente.
     *
     * @return
     */
    private String CodigoInternoAST() {
        String etiqueta;
        if (izquierda == null && derecha == null) {
            if (contenido.equals("|")) {
                etiqueta = "nodo" + conta + " [ label =\"" + primeros + "|" + "{" + anulable + " |\\" + "|" + "|" + ID + "}" + "|" + ultimos + "\"];\n";
            } else {
                if (tipo.equals("caracter")) {
                    etiqueta = "nodo" + conta + " [ label =\"" + primeros + "|" + "{" + anulable + "|&quot;\"+" + contenido + "+\"&quot;|" + ID + "}" + "|" + ultimos + "\"];\n";

                } else {
                    etiqueta = "nodo" + conta + " [ label =\"" + primeros + "|" + "{" + anulable + "|" + contenido + "|" + ID + "}" + "|" + ultimos + "\"];\n";
                }
            }
        } else {
            if (contenido.equals("|")) {
                etiqueta = "nodo" + conta + " [ label =\"" + primeros + "|{" + anulable + " |\\" + "|" + "|" + ID + "}" + "|" + ultimos + "\"];\n";
            } else {
                if (tipo.equals("caracter")) {
                    etiqueta = "nodo" + conta + " [ label =\"" + primeros + "|{" + anulable + "|&quot;\"+" + contenido + "+\"&quot;|" + ID + "}" + "|" + ultimos + "\"];\n";

                } else {
                    etiqueta = "nodo" + conta + " [ label =\"" + primeros + "|{" + anulable + "|" + contenido + "|" + ID + "}" + "|" + ultimos + "\"];\n";

                }
            }
        }
        if (izquierda != null) {
            etiqueta = etiqueta + izquierda.CodigoInternoAST()
                    + "nodo" + conta + ":C0->nodo" + izquierda.conta + "\n";
        }
        if (derecha != null) {
            etiqueta = etiqueta + derecha.CodigoInternoAST()
                    + "nodo" + conta + ":C1->nodo" + derecha.conta + "\n";
        }
        return etiqueta;
    }
}
