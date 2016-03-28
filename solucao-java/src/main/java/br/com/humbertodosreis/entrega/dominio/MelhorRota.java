/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.humbertodosreis.entrega.dominio;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MelhorRota {
    
    private List<String> rota = new ArrayList<String>();
    private double custo;

    public MelhorRota(
            List<Local> caminho,
            double custo
    ) {
        this.custo = custo;
        
        for(Local l : caminho) {
            this.rota.add(l.getNome());
        }
    }

    public List<String> getRota() {
        return rota;
    }

    public double getCusto() {
        return custo;
    }
}
