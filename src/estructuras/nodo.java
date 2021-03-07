package estructuras;

public class nodo {

    public String contenido;
    public nodo izquierda;
    public nodo derecha;
    public String estado;

    public nodo(String contenido, nodo izquierda, nodo derecha) {
        this.contenido = contenido;
        this.izquierda = izquierda;
        this.derecha = derecha;
        this.estado = "operacion";
    }

    public nodo(String contenido) {
        this.contenido = contenido;
        this.estado = "hoja";
    }

}
