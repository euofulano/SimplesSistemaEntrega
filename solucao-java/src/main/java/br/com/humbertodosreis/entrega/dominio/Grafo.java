package br.com.humbertodosreis.entrega.dominio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Grafo {

    private HashMap<String, Vertice> vertices;
    private HashMap<Integer, Aresta> edges;

    public Grafo() {
        this.vertices = new HashMap<String, Vertice>();
        this.edges = new HashMap<Integer, Aresta>();
    }

    /**
     * This constructor accepts an ArrayList<Vertex> and populates
     * this.vertices. If multiple Vertex objects have the same label, then the
     * last Vertex with the given label is used.
     *
     * @param vertices The initial Vertices to populate this Graph
     */
    public Grafo(ArrayList<Vertice> vertices) {
        this.vertices = new HashMap<String, Vertice>();
        this.edges = new HashMap<Integer, Aresta>();

        for (Vertice v : vertices) {
            this.vertices.put(v.getNome(), v);
        }

    }

    /**
     * This method adds am edge between Vertices one and two of weight 1, if no
     * Edge between these Vertices already exists in the Graph.
     *
     * @param one The first vertex to add
     * @param two The second vertex to add
     * @return true iff no Edge relating one and two exists in the Graph
     */
    public boolean addEdge(Vertice one, Vertice two) {
        return addEdge(one, two, 1);
    }

    /**
     * Accepts two vertices and a weight, and adds the edge ({one, two}, weight)
     * iff no Edge relating one and two exists in the Graph.
     *
     * @param one The first Vertex of the Edge
     * @param two The second Vertex of the Edge
     * @param weight The weight of the Edge
     * @return true iff no Edge already exists in the Graph
     */
    public boolean addEdge(Vertice one, Vertice two, int weight) {
        if (one.equals(two)) {
            return false;
        }

        //ensures the Edge is not in the Graph
        Aresta e = new Aresta(one, two, weight);
        if (edges.containsKey(e.hashCode())) {
            return false;
        } //and that the Edge isn't already incident to one of the vertices
        else if (one.contemVizinho(e) || two.contemVizinho(e)) {
            return false;
        }

        edges.put(e.hashCode(), e);
        one.adicionarVizinho(e);
        two.adicionarVizinho(e);
        return true;
    }

    /**
     *
     * @param e The Edge to look up
     * @return true iff this Graph contains the Edge e
     */
    public boolean containsEdge(Aresta e) {
        if (e.getOrigem() == null || e.getDestino() == null) {
            return false;
        }

        return this.edges.containsKey(e.hashCode());
    }

    /**
     * This method removes the specified Edge from the Graph, including as each
     * vertex's incidence neighborhood.
     *
     * @param e The Edge to remove from the Graph
     * @return Edge The Edge removed from the Graph
     */
    public Aresta removeEdge(Aresta e) {
        e.getOrigem().retirarVizinho(e);
        e.getDestino().retirarVizinho(e);
        return this.edges.remove(e.hashCode());
    }

    /**
     *
     * @param vertex The Vertex to look up
     * @return true iff this Graph contains vertex
     */
    public boolean containsVertex(Vertice vertex) {
        return this.vertices.get(vertex.getNome()) != null;
    }

    /**
     *
     * @param label The specified Vertex label
     * @return Vertex The Vertex with the specified label
     */
    public Vertice getVertex(String label) {
        return vertices.get(label);
    }

    /**
     * This method adds a Vertex to the graph. If a Vertex with the same label
     * as the parameter exists in the Graph, the existing Vertex is overwritten
     * only if overwriteExisting is true. If the existing Vertex is overwritten,
     * the Edges incident to it are all removed from the Graph.
     *
     * @param vertex
     * @param overwriteExisting
     * @return true iff vertex was added to the Graph
     */
    public boolean addVertex(Vertice vertex, boolean overwriteExisting) {
        Vertice current = this.vertices.get(vertex.getNome());
        if (current != null) {
            if (!overwriteExisting) {
                return false;
            }

            while (current.getTotalVizinhos() > 0) {
                this.removeEdge(current.getVizinho(0));
            }
        }

        vertices.put(vertex.getNome(), vertex);
        return true;
    }

    /**
     *
     * @param label The label of the Vertex to remove
     * @return Vertex The removed Vertex object
     */
    public Vertice removeVertex(String label) {
        Vertice v = vertices.remove(label);

        while (v.getTotalVizinhos() > 0) {
            this.removeEdge(v.getVizinho((0)));
        }

        return v;
    }

    /**
     *
     * @return Set<String> The unique labels of the Graph's Vertex objects
     */
    public Set<String> vertexKeys() {
        return this.vertices.keySet();
    }

    /**
     *
     * @return Set<Edge> The Edges of this graph
     */
    public Set<Aresta> getEdges() {
        return new HashSet<Aresta>(this.edges.values());
    }

}
