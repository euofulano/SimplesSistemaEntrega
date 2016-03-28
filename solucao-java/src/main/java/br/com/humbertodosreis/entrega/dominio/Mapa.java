package br.com.humbertodosreis.entrega.dominio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Mapa {

    private HashMap<String, Local> locais;
    private HashMap<Integer, Rota> trajetos;

    public Mapa() {
        this.locais = new HashMap<>();
        this.trajetos = new HashMap<>();
    }

    public Mapa(ArrayList<Local> locais) {
        this.locais = new HashMap<>();
        this.trajetos = new HashMap<>();

        for (Local v : locais) {
            this.locais.put(v.getNome(), v);
        }

    }

    public boolean adicionarTrajeto(Local origem, Local destino) {
        return Mapa.this.conectar(origem, destino, 1);
    }

    public boolean conectar(Local origem, Local destino, int distancia) {
        if (origem.equals(destino)) {
            return false;
        }

        Rota e = new Rota(origem, destino, distancia);
        if (trajetos.containsKey(e.hashCode())) {
            return false;
        } 
        else if (origem.contemVizinho(e) || destino.contemVizinho(e)) {
            return false;
        }

        trajetos.put(e.hashCode(), e);
        origem.adicionarVizinho(e);
        destino.adicionarVizinho(e);
        return true;
    }

    public boolean contemTrajeto(Rota e) {
        if (e.getOrigem() == null || e.getDestino() == null) {
            return false;
        }

        return this.trajetos.containsKey(e.hashCode());
    }

    public Rota removerTrajeto(Rota e) {
        e.getOrigem().retirarVizinho(e);
        e.getDestino().retirarVizinho(e);
        return this.trajetos.remove(e.hashCode());
    }

    public boolean contemLocal(Local vertex) {
        return this.locais.get(vertex.getNome()) != null;
    }

    public Local getLocal(String label) {
        return locais.get(label);
    }

    public boolean adicionarLocal(Local local, boolean overwriteExisting) {
        Local current = this.locais.get(local.getNome());
        if (current != null) {
            if (!overwriteExisting) {
                return false;
            }

            while (current.getTotalVizinhos() > 0) {
                this.removerTrajeto(current.getVizinho(0));
            }
        }

        locais.put(local.getNome(), local);
        return true;
    }

    public Local removerLocal(String label) {
        Local v = locais.remove(label);

        while (v.getTotalVizinhos() > 0) {
            this.removerTrajeto(v.getVizinho((0)));
        }

        return v;
    }

    /**
     *
     * @return Nomes dos locais do mapa
     */
    public Set<String> getNomesLocais() {
        return this.locais.keySet();
    }

    /**
     * 
     * @return Trajetos do mapa
     */
    public Set<Rota> getTrajetos() {
        return new HashSet<Rota>(this.trajetos.values());
    }

}
