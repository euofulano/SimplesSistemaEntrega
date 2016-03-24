package br.com.humbertodosreis.entrega.dominio;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MalhaLogistica {
    
    private String nomeMapa;
    
    private Rota[] rotas;
    
    public MalhaLogistica() {}
    
    /**
     * 
     * @param nomeMapa
     * @param rotas 
     */
    public MalhaLogistica(String nomeMapa, Rota[] rotas) {
        this.nomeMapa = nomeMapa;
        this.rotas = rotas;
    }
    
    /**
     * 
     * @return nome do mapa
     */
    public String getNomeMapa() {
        return nomeMapa;
    }
    
    /**
     * 
     * @return Lista de rotas da malha logistica
     */
    public Rota[] getRotas() {
        return rotas;
    }    
}
