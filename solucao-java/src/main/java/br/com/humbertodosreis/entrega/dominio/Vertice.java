package br.com.humbertodosreis.entrega.dominio;

import java.util.ArrayList;

/**
 *
 * @author humberto
 */
public class Vertice {
    
    private ArrayList<Aresta> vizinhos;
    private String nome;
    
    public Vertice(String nome) {
        this.nome = nome;
        this.vizinhos = new ArrayList<Aresta>();
    }
    
    public void adicionarVizinho(Aresta vizinho) {
        if (this.vizinhos.contains(vizinho)) {
            return ;
        }
        
        this.vizinhos.add(vizinho);
    }
    
    public boolean contemVizinho(Aresta vizinho) {
        return this.vizinhos.contains(vizinho);
    }
    
    public Aresta getVizinho(int index) {
        return this.vizinhos.get(index);
    }
    
    Aresta retirarVizinho(int index) {
        return this.vizinhos.remove(index);
    }
    
    public void retirarVizinho(Aresta vizinho) {
        this.vizinhos.remove(vizinho);
    }
    
    public int getTotalVizinhos() {
        return this.vizinhos.size();
    }
    
    public String getNome() {
        return this.nome;
    }
            
    @Override
    public String toString() {
        return "Vertice " + this.nome;
    }
    
    
    @Override
    public int hashCode() {
        return this.nome.hashCode();
    }
    
     public boolean equals(Object other){
        if(!(other instanceof Vertice)){
            return false;
        }
        
        Vertice v = (Vertice)other;
        return this.nome.equals(v.nome);
    }
    
    public ArrayList<Aresta> getVizinhos(){
        return new ArrayList<Aresta>(this.vizinhos);
    }            
}