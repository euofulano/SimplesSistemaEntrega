package br.com.humbertodosreis.entrega;

import br.com.humbertodosreis.entrega.dao.MapaDao;
import br.com.humbertodosreis.entrega.dominio.ServicoCaminhoMenorCusto;
import br.com.humbertodosreis.entrega.dominio.Rota;
import br.com.humbertodosreis.entrega.dominio.Mapa;
import br.com.humbertodosreis.entrega.dominio.Local;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("mapas")
public class MapaResource {

    @POST
    @Path("/cadastrar")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response cadastrar(CadastrarMapaRequest request) {

        MapaDao dao = new MapaDao();

        for (Rota rota : request.getRotas()) {
            dao.cadastrar(request.getMapa(), rota);
        }

        return Response.status(201).build();
    }

    @POST
    @Path("/obter-caminho")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ObterCamimhoResponse obterCaminho(ObterCaminhoRequest request) {
        MapaDao dao = new MapaDao();
        Mapa mapa = new Mapa();

        List<Rota> rotas = dao.buscarRotasPorMapa(request.getMapa());
        
        if (rotas.isEmpty()) {
            throw new WebApplicationException(
                    "O mapa '" + request.getMapa() + "' não existe", Response.Status.NOT_FOUND);
        }

        for (Rota r : rotas) {
            mapa.adicionarLocal(r.getOrigem(), false);
            mapa.adicionarLocal(r.getDestino(), false);
            mapa.conectar(r.getOrigem(), r.getDestino(), r.getDistancia());
        }

        ServicoCaminhoMenorCusto servico = new ServicoCaminhoMenorCusto(mapa, request.getOrigem());

        int distancia = servico.getDistanciaPara(request.getDestino());
        List<Local> locais = servico.getCaminhoPara(request.getDestino());

        double custo = (distancia / (double) request.getAutonomia()) * request.getValorLitro();

        return new ObterCamimhoResponse(locais, custo);
    }
}
