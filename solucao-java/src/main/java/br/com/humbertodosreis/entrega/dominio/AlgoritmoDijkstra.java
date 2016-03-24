
package br.com.humbertodosreis.entrega.dominio;

import java.util.*;

/**
 * 
 * @author Michael Levet
 * @see http://www.dreamincode.net/forums/topic/386358-dijkstras-algorithm/
 */
public class AlgoritmoDijkstra {
    
    private Grafo graph;
    private String initialVertexLabel;
    private HashMap<String, String> predecessors;
    private HashMap<String, Integer> distances; 
    private PriorityQueue<Vertice> availableVertices;
    private HashSet<Vertice> visitedVertices; 
    
    
    /**
     * This constructor initializes this Dijkstra object and executes
     * Dijkstra's algorithm on the graph given the specified initialVertexLabel.
     * After the algorithm terminates, the shortest a-b paths and the corresponding
     * distances will be available for all vertices b in the graph.
     * 
     * @param graph The Graph to traverse
     * @param initialVertexLabel The starting Vertex label
     * @throws IllegalArgumentException If the specified initial vertex is not in the Graph
     */
    public AlgoritmoDijkstra(Grafo graph, String initialVertexLabel){
        this.graph = graph;
        Set<String> vertexKeys = this.graph.vertexKeys();
        
        if(!vertexKeys.contains(initialVertexLabel)){
            throw new IllegalArgumentException("The graph must contain the initial vertex.");
        }
        
        this.initialVertexLabel = initialVertexLabel;
        this.predecessors = new HashMap<String, String>();
        this.distances = new HashMap<String, Integer>();
        this.availableVertices = new PriorityQueue<Vertice>(vertexKeys.size(), new Comparator<Vertice>(){
            
            public int compare(Vertice one, Vertice two){
                int weightOne = AlgoritmoDijkstra.this.distances.get(one.getNome());
                int weightTwo = AlgoritmoDijkstra.this.distances.get(two.getNome());
                return weightOne - weightTwo;
            }
        });
        
        this.visitedVertices = new HashSet<Vertice>();
        
        //for each Vertex in the graph
        //assume it has distance infinity denoted by Integer.MAX_VALUE
        for(String key: vertexKeys){
            this.predecessors.put(key, null);
            this.distances.put(key, Integer.MAX_VALUE);
        }
        
        
        //the distance from the initial vertex to itself is 0
        this.distances.put(initialVertexLabel, 0);
        
        //and seed initialVertex's neighbors
        Vertice initialVertex = this.graph.getVertex(initialVertexLabel);
        ArrayList<Aresta> initialVertexNeighbors = initialVertex.getVizinhos();
        for(Aresta e : initialVertexNeighbors){
            Vertice other = e.getVizinho(initialVertex);
            this.predecessors.put(other.getNome(), initialVertexLabel);
            this.distances.put(other.getNome(), e.getPeso());
            this.availableVertices.add(other);
        }
        
        this.visitedVertices.add(initialVertex);
        
        //now apply Dijkstra's algorithm to the Graph
        processGraph();
        
    }
    
    /**
     * This method applies Dijkstra's algorithm to the graph using the Vertex
     * specified by initialVertexLabel as the starting point.
     * 
     * @post The shortest a-b paths as specified by Dijkstra's algorithm and 
     *       their distances are available 
     */
    private void processGraph(){
        
        //as long as there are Edges to process
        while(this.availableVertices.size() > 0){
            
            //pick the cheapest vertex
            Vertice next = this.availableVertices.poll();
            int distanceToNext = this.distances.get(next.getNome());
            
            //and for each available neighbor of the chosen vertex
            List<Aresta> nextNeighbors = next.getVizinhos();     
            for(Aresta e: nextNeighbors){
                Vertice other = e.getVizinho(next);
                if(this.visitedVertices.contains(other)){
                    continue;
                }
                
                //we check if a shorter path exists
                //and update to indicate a new shortest found path
                //in the graph
                int currentWeight = this.distances.get(other.getNome());
                int newWeight = distanceToNext + e.getPeso();
                
                if(newWeight < currentWeight){
                    this.predecessors.put(other.getNome(), next.getNome());
                    this.distances.put(other.getNome(), newWeight);
                    this.availableVertices.remove(other);
                    this.availableVertices.add(other);
                }
                
            }
            
            // finally, mark the selected vertex as visited 
            // so we don't revisit it
            this.visitedVertices.add(next);
        }
    }
    
    
    /**
     * 
     * @param destinationLabel The Vertex whose shortest path from the initial Vertex is desired
     * @return LinkedList<Vertex> A sequence of Vertex objects starting at the 
     *         initial Vertex and terminating at the Vertex specified by destinationLabel.
     *         The path is the shortest path specified by Dijkstra's algorithm.
     */
    public List<Vertice> getPathTo(String destinationLabel){
        LinkedList<Vertice> path = new LinkedList<Vertice>();
        path.add(graph.getVertex(destinationLabel));
        
        while(!destinationLabel.equals(this.initialVertexLabel)){
            Vertice predecessor = graph.getVertex(this.predecessors.get(destinationLabel));
            destinationLabel = predecessor.getNome();
            path.add(0, predecessor);
        }
        return path;
    }
    
    
    /**
     * 
     * @param destinationLabel The Vertex to determine the distance from the initial Vertex
     * @return int The distance from the initial Vertex to the Vertex specified by destinationLabel
     */
    public int getDistanceTo(String destinationLabel){
        return this.distances.get(destinationLabel);
    }
    
    
    public static void main(String[] args){
        Grafo graph = new Grafo();
        Vertice[] vertices = new Vertice[6];
        
        for(int i = 0; i < vertices.length; i++){
            vertices[i] = new Vertice(i + "");
            graph.addVertex(vertices[i], true);
        }
        
        Aresta[] edges = new Aresta[9];
        edges[0] = new Aresta(vertices[0], vertices[1], 7);
        edges[1] = new Aresta(vertices[0], vertices[2], 9);
        edges[2] = new Aresta(vertices[0], vertices[5], 14);
        edges[3] = new Aresta(vertices[1], vertices[2], 10);
        edges[4] = new Aresta(vertices[1], vertices[3], 15);
        edges[5] = new Aresta(vertices[2], vertices[3], 11);
        edges[6] = new Aresta(vertices[2], vertices[5], 2);
        edges[7] = new Aresta(vertices[3], vertices[4], 6);
        edges[8] = new Aresta(vertices[4], vertices[5], 9);
        
        for(Aresta e: edges){
            graph.addEdge(e.getOrigem(), e.getDestino(), e.getPeso());
        }
        
        AlgoritmoDijkstra dijkstra = new AlgoritmoDijkstra(graph, vertices[0].getNome());
        System.out.println(dijkstra.getDistanceTo("5"));
        System.out.println(dijkstra.getPathTo("5"));
    }
}

