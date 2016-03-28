package br.com.humbertodosreis.entrega;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.glassfish.grizzly.http.server.HttpServer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class MapaResourceTest {

    private HttpServer server;
    private WebTarget target;

    @Before
    public void setUp() throws Exception {
        // start the server
        server = Main.startServer();
        // create the client
        Client c = ClientBuilder.newClient();

        // uncomment the following line if you want to enable
        // support for JSON in the client (you also have to uncomment
        // dependency on jersey-media-json module in pom.xml and Main.startServer())
        // --
        // c.configuration().enable(new org.glassfish.jersey.media.json.JsonJaxbFeature());

        target = c.target(Main.BASE_URI);        
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void testObterCaminho() {        
        ObterCaminhoRequest request = new ObterCaminhoRequest("SP", "A", "D", 10, 2.5);        
        
        String response = target
                .path("mapas")
                .path("obter-caminho")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.json(request), String.class);
                
        assertEquals("{\"rota\":[\"A\",\"B\",\"D\"],\"custo\":6.25}", response);
    }
    
    @Test(expected = NotFoundException.class)    
    public void testDeveriaLancarExcecaoCasoMapaNaoCadastrado() {
        ObterCaminhoRequest request = new ObterCaminhoRequest("XX", "A", "D", 10, 2.5);        
        
        String response = target
                .path("mapas")
                .path("obter-caminho")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.json(request), String.class);
                
        assertEquals("{\"rota\":[\"A\",\"B\",\"D\"],\"custo\":6.25}", response);
    }            
}