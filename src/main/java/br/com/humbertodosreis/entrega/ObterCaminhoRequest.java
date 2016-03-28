
package br.com.humbertodosreis.entrega;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ObterCaminhoRequest {

    private String mapa;
    
    private String origem;
    
    private String destino;
    
    private int autonomia;
    
    private double valorLitro;
    
    public ObterCaminhoRequest() {}
    
    public ObterCaminhoRequest(
            String mapa, 
            String origem, 
            String destino, 
            int autonomia, 
            double valorLitro
    ) {
        this.mapa = mapa;
        this.origem = origem;
        this.destino = destino;
        this.autonomia = autonomia;
        this.valorLitro = valorLitro;
    }

    public String getMapa() {
        return mapa;
    }

    public String getOrigem() {
        return origem;
    }

    public String getDestino() {
        return destino;
    }

    public int getAutonomia() {
        return autonomia;
    }

    public double getValorLitro() {
        return valorLitro;
    }    
}
