package br.com.humbertodosreis.entrega;

import br.com.humbertodosreis.entrega.dao.MapaDao;
import br.com.humbertodosreis.entrega.dominio.ServicoRota;
import br.com.humbertodosreis.entrega.dominio.Rota;
import br.com.humbertodosreis.entrega.dominio.Mapa;
import br.com.humbertodosreis.entrega.dominio.Local;
import br.com.humbertodosreis.entrega.dominio.MalhaLogistica;
import br.com.humbertodosreis.entrega.dominio.MelhorRota;
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
        
        MapaDao dao = new MapaDao();
        dao.buscarRotasPorMapa("SP");
        
        System.out.println(malha);
        
        return Response.status(201).build();
    }
    
    @POST
    @Path("/obter-rota")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public MelhorRota obterRota() {
        String nomeMapa = "SP";
        String origem = "A";
        String destino = "D";
        int autonomia = 10;
        double valorLitro = 2.5;
        
        MapaDao dao = new MapaDao();
        Mapa mapa = new Mapa();
        
        List<Rota> rotas2 = dao.buscarRotasPorMapa(nomeMapa);
        
        for (Rota r : rotas2) {
            mapa.adicionarLocal(r.getOrigem(), false);
            mapa.adicionarLocal(r.getDestino(), false);
            mapa.conectar(r.getOrigem(), r.getDestino(), r.getDistancia());
        }

        ServicoRota servico = new ServicoRota(mapa, origem);
        
        int distancia = servico.getDistanciaPara(destino);
        List<Local> locais = servico.getCaminhoPara(destino);
        double custo = (distancia / (double) autonomia) * valorLitro;        
        
        return new MelhorRota(locais, custo);        
    }    
}