package br.com.humbertodosreis.entrega.dominio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Mapa {

    private HashMap<String, Local> locais;
    private HashMap<Integer, Rota> trajetos;

    public Mapa() {
        this.locais = new HashMap<String, Local>();
        this.trajetos = new HashMap<Integer, Rota>();
    }

    /**
     * This constructor accepts an ArrayList<Vertex> and populates
     * this.vertices. If multiple Vertex objects have the same label, then the
     * last Vertex with the given label is used.
     *
     * @param locais The initial Vertices to populate this Graph
     */
    public Mapa(ArrayList<Local> locais) {
        this.locais = new HashMap<String, Local>();
        this.trajetos = new HashMap<Integer, Rota>();

        for (Local v : locais) {
            this.locais.put(v.getNome(), v);
        }

    }

    /**
     * This method adds am edge between Vertices one and two of weight 1, if no
     * Edge between these Vertices already exists in the Graph.
     *
     * @param origem The first vertex to add
     * @param destino The second vertex to add
     * @return true iff no Edge relating one and two exists in the Graph
     */
    public boolean adicionarTrajeto(Local origem, Local destino) {
        return Mapa.this.conectar(origem, destino, 1);
    }

    /**
     * Accepts two vertices and a weight, and adds the edge ({one, two}, weight)
     * iff no Edge relating one and two exists in the Graph.
     *
     * @param origem The first Vertex of the Edge
     * @param destino The second Vertex of the Edge
     * @param distancia The weight of the Edge
     * @return true iff no Edge already exists in the Graph
     */
    public boolean conectar(Local origem, Local destino, int distancia) {
        if (origem.equals(destino)) {
            return false;
        }

        //ensures the Edge is not in the Graph
        Rota e = new Rota(origem, destino, distancia);
        if (trajetos.containsKey(e.hashCode())) {
            return false;
        } //and that the Edge isn't already incident to one of the vertices
        else if (origem.contemVizinho(e) || destino.contemVizinho(e)) {
            return false;
        }

        trajetos.put(e.hashCode(), e);
        origem.adicionarVizinho(e);
        destino.adicionarVizinho(e);
        return true;
    }

    /**
     *
     * @param e The Edge to look up
     * @return true iff this Graph contains the Edge e
     */
    public boolean contemTrajeto(Rota e) {
        if (e.getOrigem() == null || e.getDestino() == null) {
            return false;
        }

        return this.trajetos.containsKey(e.hashCode());
    }

    /**
     * This method removes the specified Edge from the Graph, including as each
     * vertex's incidence neighborhood.
     *
     * @param e The Edge to remove from the Graph
     * @return Edge The Edge removed from the Graph
     */
    public Rota removerTrajeto(Rota e) {
        e.getOrigem().retirarVizinho(e);
        e.getDestino().retirarVizinho(e);
        return this.trajetos.remove(e.hashCode());
    }

    /**
     *
     * @param vertex The Vertex to look up
     * @return true iff this Graph contains vertex
     */
    public boolean contemLocal(Local vertex) {
        return this.locais.get(vertex.getNome()) != null;
    }

    /**
     *
     * @param label The specified Vertex label
     * @return Vertex The Vertex with the specified label
     */
    public Local getLocal(String label) {
        return locais.get(label);
    }

    /**
     * This method adds a Vertex to the graph. If a Vertex with the same label
     * as the parameter exists in the Graph, the existing Vertex is overwritten
     * only if overwriteExisting is true. If the existing Vertex is overwritten,
     * the Edges incident to it are all removed from the Graph.
     *
     * @param local
     * @param overwriteExisting
     * @return true iff vertex was added to the Graph
     */
    public boolean adicionarLocal(Local local, boolean overwriteExisting) {
        Local current = this.locais.get(local.getNome());
        if (current != null) {
            if (!overwriteExisting) {
                return false;
            }

            while (current.getTotalVizinhos() > 0) {
                this.removerTrajeto(current.getVizinho(0));
            }
        }

        locais.put(local.getNome(), local);
        return true;
    }

    /**
     *
     * @param label The label of the Vertex to remove
     * @return Vertex The removed Vertex object
     */
    public Local removerLocal(String label) {
        Local v = locais.remove(label);

        while (v.getTotalVizinhos() > 0) {
            this.removerTrajeto(v.getVizinho((0)));
        }

        return v;
    }

    /**
     *
     * @return Set<String> The unique labels of the Graph's Vertex objects
     */
    public Set<String> vertexKeys() {
        return this.locais.keySet();
    }

    /**
     *
     * @return Set<Edge> The Edges of this graph
     */
    public Set<Rota> getTrajetos() {
        return new HashSet<Rota>(this.trajetos.values());
    }

}
