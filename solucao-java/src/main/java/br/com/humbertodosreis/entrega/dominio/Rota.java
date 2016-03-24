package br.com.humbertodosreis.entrega.dominio;

/**
 *
 * @author humberto
 */
public class Rota implements Comparable<Rota> {

    private Local origem, destino;
    private int distancia;

    public Rota(Local origem, Local destino) {
        this(origem, destino, 1);
    }

    public Rota(Local origem, Local destino, int distancia) {
        this.origem = (origem.getNome().compareTo(destino.getNome()) <= 0) ? origem : destino;
        this.destino = (this.origem == origem) ? destino : origem;
        this.distancia = distancia;
    }

    public Local getVizinho(Local vertice) {
        if (!(vertice.equals(origem) || vertice.equals(destino))) {
            return null;
        }

        return (vertice.equals(origem)) ? destino : origem;
    }

    public Local getOrigem() {
        return this.origem;
    }

    public Local getDestino() {
        return this.destino;
    }

    public int getDistancia() {
        return this.distancia;
    }

    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }

    @Override
    public int compareTo(Rota trajeto) {
        return this.distancia - trajeto.distancia;
    }

    /**
     *
     * @return String A String representation of this Edge
     */
    @Override
    public String toString() {
        return "({" + origem + ", " + destino + "}, " + distancia + ")";
    }

    /**
     *
     * @return int The hash code for this Edge
     */
    @Override
    public int hashCode() {
        return (origem.getNome() + destino.getNome()).hashCode();
    }

    /**
     *
     * @param other The Object to compare against this
     * @return ture if other is an Edge with the same Vertices as this
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Rota)) {
            return false;
        }

        Rota e = (Rota) other;

        return e.origem.equals(this.origem) && e.destino.equals(this.destino);
    }

}
