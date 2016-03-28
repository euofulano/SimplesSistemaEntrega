package br.com.humbertodosreis.entrega.dao;

import br.com.humbertodosreis.entrega.dominio.Local;
import br.com.humbertodosreis.entrega.dominio.Rota;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author humberto
 */
public class MapaDao {

    public void cadastrar(String nomeMapa, Rota rota) {

        Connection conn = null;

        try {
            conn = getConexao();
        } catch (SQLException ex) {

        } catch (ClassNotFoundException ex) {

        }
    }

    public List<Local> buscarLocaisPorMapa(String mapa) {
        Connection conn = null;
        ArrayList<Local> locais = new ArrayList<Local>();

        try {
            conn = getConexao();

            PreparedStatement ps = conn.prepareStatement(
                    "SELECT origem FROM mapas WHERE mapa = ? "
                    + "UNION SELECT destino FROM mapas WHERE mapa = ?");

            ps.setString(1, mapa);
            ps.setString(2, mapa);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Local local = new Local(rs.getString(1));
                locais.add(local);
            }

        } catch (SQLException ex) {
            System.err.print(ex);
        } catch (ClassNotFoundException ex) {
            System.err.print(ex);
        }

        return locais;
    }

    public List<Rota> buscarRotasPorMapa(String mapa) {
        Connection conn = null;
        ArrayList<Rota> rotas = new ArrayList<Rota>();

        try {
            conn = getConexao();

            PreparedStatement ps = conn.prepareStatement(
                    "SELECT origem, destino, distancia FROM mapas WHERE mapa = ?");

            ps.setString(1, mapa);

            ResultSet rs = ps.executeQuery();
            
            HashMap<String, Local> cache = new HashMap<>();

            while (rs.next()) {
                
                String nomeLocalOrigem = rs.getString(1);
                String nomeLocalDestino = rs.getString(2);
                
                Local origem = null;
                Local destino = null;
                
                if (cache.containsKey(nomeLocalOrigem)) {
                    origem = cache.get(nomeLocalOrigem);
                } else {
                    origem = new Local(nomeLocalOrigem);
                    cache.put(nomeLocalOrigem, origem);
                }
                
                if (cache.containsKey(nomeLocalDestino)) {
                    destino = cache.get(nomeLocalDestino);
                } else {
                    destino = new Local(nomeLocalDestino);
                    cache.put(nomeLocalDestino, destino);
                }
                
                int distancia = rs.getInt(3);

                rotas.add(new Rota(origem, destino, distancia));
            }

        } catch (SQLException ex) {
            System.err.print(ex);
        } catch (ClassNotFoundException ex) {
            System.err.print(ex);
        }

        return rotas;
    }

    private Connection getConexao() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");

        Connection conn = DriverManager.getConnection("jdbc:sqlite::resource:banco.db");

        return conn;
    }

}
