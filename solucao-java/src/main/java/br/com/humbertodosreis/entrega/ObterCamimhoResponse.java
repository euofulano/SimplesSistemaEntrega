
package br.com.humbertodosreis.entrega;

import br.com.humbertodosreis.entrega.dominio.Local;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ObterCamimhoResponse {
    
    private List<String> rota = new ArrayList<String>();
    private double custo;

    public ObterCamimhoResponse(
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
