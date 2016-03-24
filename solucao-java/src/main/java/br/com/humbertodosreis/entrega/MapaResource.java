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

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("mapas")
public class MapaResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    /*@GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Got it!";
    }*/
    
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Local> getAll() {
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
        
        return dijkstra.getCaminhoPara("5");
    }
    
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
    @Produces(MediaType.TEXT_PLAIN)
    public String obterRota() {
        /*String[] x = new String[0];
        x[0] = "teste1";
        x[1] = "teste2";
        */
        return "Um Teste";
    }
    
    
}
