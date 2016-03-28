package br.com.humbertodosreis.entrega;

import br.com.humbertodosreis.entrega.dominio.Rota;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CadastrarMapaRequest {
    
    private String mapa;
    
    private Rota[] rotas;
    
    public CadastrarMapaRequest() {}
    
    /**
     * 
     * @param mapa
     * @param rotas 
     */
    public CadastrarMapaRequest(String mapa, Rota[] rotas) {
        this.mapa = mapa;
        this.rotas = rotas;
    }
    
    /**
     * 
     * @return nome do mapa
     */
    public String getMapa() {
        return mapa;
    }
    
    /**
     * 
     * @return Lista de rotas da malha logistica
     */
    public Rota[] getRotas() {
        return rotas;
    }    
}
