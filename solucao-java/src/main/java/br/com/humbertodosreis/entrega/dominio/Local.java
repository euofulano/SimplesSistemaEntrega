package br.com.humbertodosreis.entrega.dominio;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Local {
    
    private ArrayList<Rota> vizinhos;
    private String nome;
        
    public Local() {}
    
    public Local(String nome) {
        this.nome = nome;
        this.vizinhos = new ArrayList<Rota>();
    }
    
    public void adicionarVizinho(Rota vizinho) {
        if (this.vizinhos.contains(vizinho)) {
            return ;
        }
        
        this.vizinhos.add(vizinho);
    }
    
    public boolean contemVizinho(Rota vizinho) {
        return this.vizinhos.contains(vizinho);
    }
    
    public Rota getVizinho(int index) {
        return this.vizinhos.get(index);
    }
    
    Rota retirarVizinho(int index) {
        return this.vizinhos.remove(index);
    }
    
    public void retirarVizinho(Rota vizinho) {
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
        if(!(other instanceof Local)){
            return false;
        }
        
        Local v = (Local)other;
        return this.nome.equals(v.nome);
    }
    
    public ArrayList<Rota> getVizinhos(){
        return new ArrayList<Rota>(this.vizinhos);
    }            
}