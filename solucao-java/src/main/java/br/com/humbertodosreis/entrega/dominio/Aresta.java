package br.com.humbertodosreis.entrega.dominio;

import java.util.Comparator;

/**
 *
 * @author humberto
 */
public class Aresta implements Comparable<Aresta> {

    private Vertice origem, destino;
    private int peso;

    public Aresta(Vertice origem, Vertice destino) {
        this(origem, destino, 1);
    }

    public Aresta(Vertice origem, Vertice destino, int peso) {
        this.origem = (origem.getNome().compareTo(destino.getNome()) <= 0) ? origem : destino;
        this.destino = (this.origem == origem) ? destino : origem;
        this.peso = peso;
    }

    public Vertice getVizinho(Vertice vertice) {
        if (!(vertice.equals(origem) || vertice.equals(destino))) {
            return null;
        }

        return (vertice.equals(origem)) ? destino : origem;
    }

    public Vertice getOrigem() {
        return this.origem;
    }

    public Vertice getDestino() {
        return this.destino;
    }

    public int getPeso() {
        return this.peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    @Override
    public int compareTo(Aresta aresta) {
        return this.peso - aresta.peso;
    }

    /**
     *
     * @return String A String representation of this Edge
     */
    public String toString() {
        return "({" + origem + ", " + destino + "}, " + peso + ")";
    }

    /**
     *
     * @return int The hash code for this Edge
     */
    public int hashCode() {
        return (origem.getNome() + destino.getNome()).hashCode();
    }

    /**
     *
     * @param other The Object to compare against this
     * @return ture if other is an Edge with the same Vertices as this
     */
    public boolean equals(Object other) {
        if (!(other instanceof Aresta)) {
            return false;
        }

        Aresta e = (Aresta) other;

        return e.origem.equals(this.origem) && e.destino.equals(this.destino);
    }

}
