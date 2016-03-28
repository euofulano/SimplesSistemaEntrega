package br.com.humbertodosreis.entrega.dao;

import br.com.humbertodosreis.entrega.dominio.Local;
import br.com.humbertodosreis.entrega.dominio.Rota;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapaDao {

    public void cadastrar(String nomeMapa, Rota rota) {

        Connection conn = null;

        try {
            conn = getConexao();            

            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO mapas (mapa, origem, destino, distancia) VALUES (?,?,?,?)");

            ps.setString(1, nomeMapa);
            ps.setString(2, rota.getOrigem().getNome());
            ps.setString(3, rota.getDestino().getNome());
            ps.setInt(4, rota.getDistancia());
            
            ps.executeUpdate();
            ps.close();

        } catch (SQLException ex) {
            System.err.print(ex);
        } catch (ClassNotFoundException ex) {
            System.err.print(ex);
        } finally {
            try {
                if (null != conn) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.err.print(ex);
            }
        }
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
            
            ps.close();

        } catch (SQLException ex) {
            System.err.print(ex);
        } catch (ClassNotFoundException ex) {
            System.err.print(ex);
        } finally {
            try {
                if (null != conn) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.err.print(ex);
            }
        }

        return rotas;
    }

    private Connection getConexao() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");

        Connection conn = DriverManager.getConnection(
                "jdbc:sqlite::resource:database.sqlite");

        return conn;
    }

}
