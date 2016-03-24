
package br.com.humbertodosreis.entrega.dominio;

import java.util.*;

/**
 * 
 * @author Michael Levet
 * @see http://www.dreamincode.net/forums/topic/386358-dijkstras-algorithm/
 */
public class ServicoRota {
    
    private Mapa malhaLogistica;
    private String nomeLocalOrigem;
    private HashMap<String, String> predecessors;
    private HashMap<String, Integer> distancias; 
    private PriorityQueue<Local> locaisAnalisados;
    private HashSet<Local> locaisVisitados; 
    
    
    /**
     * This constructor initializes this Dijkstra object and executes
     * Dijkstra's algorithm on the graph given the specified initialVertexLabel.
     * After the algorithm terminates, the shortest a-b paths and the corresponding
     * distances will be available for all vertices b in the graph.
     * 
     * @param malha The Graph to traverse
     * @param nomeLocalOrigem The starting Vertex label
     * @throws IllegalArgumentException If the specified initial vertex is not in the Graph
     */
    public ServicoRota(Mapa malha, String nomeLocalOrigem){
        this.malhaLogistica = malha;
        Set<String> vertexKeys = this.malhaLogistica.vertexKeys();
        
        if(!vertexKeys.contains(nomeLocalOrigem)){
            throw new IllegalArgumentException("The graph must contain the initial vertex.");
        }
        
        this.nomeLocalOrigem = nomeLocalOrigem;
        this.predecessors = new HashMap<String, String>();
        this.distancias = new HashMap<String, Integer>();
        this.locaisAnalisados = new PriorityQueue<Local>(vertexKeys.size(), new Comparator<Local>(){
            
            public int compare(Local origem, Local destino){
                int distanciaOrigem = ServicoRota.this.distancias.get(origem.getNome());
                int distanciaDestino = ServicoRota.this.distancias.get(destino.getNome());
                return distanciaOrigem - distanciaDestino;
            }
        });
        
        this.locaisVisitados = new HashSet<Local>();
        
        //for each Vertex in the graph
        //assume it has distance infinity denoted by Integer.MAX_VALUE
        for(String key: vertexKeys){
            this.predecessors.put(key, null);
            this.distancias.put(key, Integer.MAX_VALUE);
        }
        
        
        //the distance from the initial vertex to itself is 0
        this.distancias.put(nomeLocalOrigem, 0);
        
        //and seed initialVertex's neighbors
        Local localInicial = this.malhaLogistica.getLocal(nomeLocalOrigem);
        ArrayList<Rota> locaisVizinhos = localInicial.getVizinhos();
        
        for(Rota e : locaisVizinhos){
            Local other = e.getVizinho(localInicial);
            this.predecessors.put(other.getNome(), nomeLocalOrigem);
            this.distancias.put(other.getNome(), e.getDistancia());
            this.locaisAnalisados.add(other);
        }
        
        this.locaisVisitados.add(localInicial);
        
        //now apply Dijkstra's algorithm to the Graph
        analisarRotas();
        
    }
    
    /**
     * This method applies Dijkstra's algorithm to the graph using the Vertex
     * specified by initialVertexLabel as the starting point.
     * 
     * @post The shortest a-b paths as specified by Dijkstra's algorithm and 
     *       their distances are available 
     */
    private void analisarRotas(){
        
        //as long as there are Edges to process
        while(this.locaisAnalisados.size() > 0){
            
            //pick the cheapest vertex
            Local next = this.locaisAnalisados.poll();
            int distanceToNext = this.distancias.get(next.getNome());
            
            //and for each available neighbor of the chosen vertex
            List<Rota> nextNeighbors = next.getVizinhos();
            
            for(Rota e: nextNeighbors){
                Local other = e.getVizinho(next);
                if(this.locaisVisitados.contains(other)){
                    continue;
                }
                
                //we check if a shorter path exists
                //and update to indicate a new shortest found path
                //in the graph
                int distanciaAtual = this.distancias.get(other.getNome());
                int novaDistancia = distanceToNext + e.getDistancia();
                
                if(novaDistancia < distanciaAtual){
                    this.predecessors.put(other.getNome(), next.getNome());
                    this.distancias.put(other.getNome(), novaDistancia);
                    this.locaisAnalisados.remove(other);
                    this.locaisAnalisados.add(other);
                }
                
            }
            
            // finally, mark the selected vertex as visited 
            // so we don't revisit it
            this.locaisVisitados.add(next);
        }
    }
    
    
    /**
     * 
     * @param nomeDestino The Vertex whose shortest path from the initial Vertex is desired
     * @return LinkedList<Vertex> A sequence of Vertex objects starting at the 
     *         initial Vertex and terminating at the Vertex specified by destinationLabel.
     *         The path is the shortest path specified by Dijkstra's algorithm.
     */
    public List<Local> getCaminhoPara(String nomeDestino){
        LinkedList<Local> caminho = new LinkedList<Local>();
        caminho.add(malhaLogistica.getLocal(nomeDestino));
        
        while(!nomeDestino.equals(this.nomeLocalOrigem)){
            Local predecessor = malhaLogistica.getLocal(this.predecessors.get(nomeDestino));
            nomeDestino = predecessor.getNome();
            caminho.add(0, predecessor);
        }
        return caminho;
    }
    
    
    /**
     * 
     * @param nomeDestino The Vertex to determine the distance from the initial Vertex
     * @return int The distance from the initial Vertex to the Vertex specified by destinationLabel
     */
    public int getDistanciaPara(String nomeDestino){
        return this.distancias.get(nomeDestino);
    }
    
    
    public static void main(String[] args){
        Mapa graph = new Mapa();
        Local[] vertices = new Local[6];
        
        for(int i = 0; i < vertices.length; i++){
            vertices[i] = new Local(i + "");
            graph.adicionarLocal(vertices[i], true);
        }
        
        Rota[] edges = new Rota[9];
        edges[0] = new Rota(vertices[0], vertices[1], 7);
        edges[1] = new Rota(vertices[0], vertices[2], 9);
        edges[2] = new Rota(vertices[0], vertices[5], 14);
        edges[3] = new Rota(vertices[1], vertices[2], 10);
        edges[4] = new Rota(vertices[1], vertices[3], 15);
        edges[5] = new Rota(vertices[2], vertices[3], 11);
        edges[6] = new Rota(vertices[2], vertices[5], 2);
        edges[7] = new Rota(vertices[3], vertices[4], 6);
        edges[8] = new Rota(vertices[4], vertices[5], 9);
        
        for(Rota e: edges){
            graph.adicionarTrajeto(e.getOrigem(), e.getDestino(), e.getDistancia());
        }
        
        ServicoRota dijkstra = new ServicoRota(graph, vertices[0].getNome());
        System.out.println(dijkstra.getDistanciaPara("5"));
        System.out.println(dijkstra.getCaminhoPara("5"));
    }
}

