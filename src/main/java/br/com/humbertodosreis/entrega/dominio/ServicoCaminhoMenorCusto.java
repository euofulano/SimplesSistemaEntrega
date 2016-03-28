package br.com.humbertodosreis.entrega.dominio;

import java.util.*;

public class ServicoCaminhoMenorCusto {

    private Mapa malhaLogistica;
    private String nomeLocalOrigem;
    private HashMap<String, String> antecessores;
    private HashMap<String, Integer> distancias;
    private PriorityQueue<Local> locaisAnalisados;
    private HashSet<Local> locaisVisitados;

    public ServicoCaminhoMenorCusto(Mapa malha, String nomeLocalOrigem) {
        this.malhaLogistica = malha;
        Set<String> nomesLocais = this.malhaLogistica.getNomesLocais();

        if (!nomesLocais.contains(nomeLocalOrigem)) {
            throw new IllegalArgumentException("O mapa não contém o local de origem informado.");
        }

        this.nomeLocalOrigem = nomeLocalOrigem;
        this.antecessores = new HashMap<>();
        this.distancias = new HashMap<>();
        this.locaisAnalisados = new PriorityQueue<>(nomesLocais.size(), new Comparator<Local>() {
            public int compare(Local origem, Local destino) {
                int distanciaOrigem = ServicoCaminhoMenorCusto.this.distancias.get(origem.getNome());
                int distanciaDestino = ServicoCaminhoMenorCusto.this.distancias.get(destino.getNome());
                return distanciaOrigem - distanciaDestino;
            }
        });

        this.locaisVisitados = new HashSet<>();

        for (String key : nomesLocais) {
            this.antecessores.put(key, null);
            this.distancias.put(key, Integer.MAX_VALUE);
        }

        this.distancias.put(nomeLocalOrigem, 0);

        Local localInicial = this.malhaLogistica.getLocal(nomeLocalOrigem);
        ArrayList<Rota> locaisVizinhos = localInicial.getVizinhos();

        for (Rota e : locaisVizinhos) {
            Local other = e.getVizinho(localInicial);
            this.antecessores.put(other.getNome(), nomeLocalOrigem);
            this.distancias.put(other.getNome(), e.getDistancia());
            this.locaisAnalisados.add(other);
        }

        this.locaisVisitados.add(localInicial);

        processarMapa();

    }

    private void processarMapa() {

        while (this.locaisAnalisados.size() > 0) {

            Local proximoLocal = this.locaisAnalisados.poll();
            int distanciaProximoLocal = this.distancias.get(proximoLocal.getNome());

            List<Rota> vizinhos = proximoLocal.getVizinhos();

            for (Rota e : vizinhos) {
                Local other = e.getVizinho(proximoLocal);
                if (this.locaisVisitados.contains(other)) {
                    continue;
                }

                int distanciaAtual = this.distancias.get(other.getNome());
                int novaDistancia = distanciaProximoLocal + e.getDistancia();

                if (novaDistancia < distanciaAtual) {
                    this.antecessores.put(other.getNome(), proximoLocal.getNome());
                    this.distancias.put(other.getNome(), novaDistancia);
                    this.locaisAnalisados.remove(other);
                    this.locaisAnalisados.add(other);
                }

            }

            this.locaisVisitados.add(proximoLocal);
        }
    }

    /**
     * @param nomeDestino
     * @return
     */
    public List<Local> getCaminhoPara(String nomeDestino) {
        LinkedList<Local> caminho = new LinkedList<>();
        caminho.add(malhaLogistica.getLocal(nomeDestino));

        while (!nomeDestino.equals(this.nomeLocalOrigem)) {
            Local predecessor = malhaLogistica.getLocal(this.antecessores.get(nomeDestino));
            nomeDestino = predecessor.getNome();
            caminho.add(0, predecessor);
        }
        return caminho;
    }

    /**
     *
     * @param nomeDestino
     * @return
     */
    public int getDistanciaPara(String nomeDestino) {
        return this.distancias.get(nomeDestino);
    }
}
