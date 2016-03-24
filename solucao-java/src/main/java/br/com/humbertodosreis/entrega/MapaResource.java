package br.com.humbertodosreis.entrega;

import br.com.humbertodosreis.entrega.dominio.ServicoRota;
import br.com.humbertodosreis.entrega.dominio.Rota;
import br.com.humbertodosreis.entrega.dominio.Mapa;
import br.com.humbertodosreis.entrega.dominio.Local;
import br.com.humbertodosreis.entrega.dominio.MalhaLogistica;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("mapas")
public class MapaResource {
    
    @POST
    @Path("/cadastrar")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response cadastrar(MalhaLogistica malha) {
        System.out.println(malha);
        
        return Response.status(201).build();
    }
    
    @GET
    @Path("/obter-rota")
    //@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public MalhaLogistica obterRota() {
        Mapa mapa = new Mapa();
        
        Local[] locais = new Local[5];
        
        locais[0] = new Local("A");
        locais[1] = new Local("B");
        locais[2] = new Local("C");
        locais[3] = new Local("D");
        locais[4] = new Local("E");     
        
        for(Local l: locais) {
            mapa.adicionarLocal(l, true);            
        }        
                
        Rota[] rotas = new Rota[6];
        
        rotas[0] = new Rota(new Local("A"), new Local("B"), 10);
        rotas[1] = new Rota(new Local("B"), new Local("D"), 15);
        rotas[2] = new Rota(new Local("A"), new Local("C"), 20);
        rotas[3] = new Rota(new Local("C"), new Local("D"), 30);
        rotas[4] = new Rota(new Local("B"), new Local("E"), 50);
        rotas[5] = new Rota(new Local("D"), new Local("E"), 30);
        
        for(Rota e: rotas){
            mapa.conectar(e.getOrigem(), e.getDestino(), e.getDistancia());
        }
                
        ServicoRota servico = new ServicoRota(mapa, "A");
        
        System.out.println(servico.getDistanciaPara("B"));
        //System.out.println(servico.getCaminhoPara("D"));      
                
        return new MalhaLogistica("SP", rotas);
    }    
}