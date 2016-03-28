# SimplesSistemaEntrega
Aplicação para determinar o caminho de menor custo entre dois pontos em um mapa. O caminho de menor custo é determinado com a implementação do *algoritmo de Disjkra* desenvolvido com o proposito de solucionar este tipo de problema.

### Solução
O sistema foi desenvolvido em java, o webservice foi construído com o padrão RESTful. O banco de dados SQLite foi selecionado para realizar a persistência e o servidor Grizzly para rodar o webservice. Com esta implementação a aplicação pode ser executada sem depender de muitos recursos do ambiente e o deploy pode ser realizado de forma simples.

### Executando o Webservice
Construir a aplicação:
> mvn clean compile

Executar o webservice:
> mvn exec:java

Ao executar a aplicação, o webservice será exposto na URL http://localhost:8080/app.

### Testes
O webservice expoe dois recursos para realizar o cadastro da malha logística e para busca o melhor caminho. 

Recurso | Método |Descrição
------- | ------ | --------
http://localhost:8080/app/mapas/cadastrar | POST | Cadastra uma malha logística
http://localhost:8080/app/mapas/obter-caminho | POST | Obtém o caminho de menor custo

Os testes pode ser executado utilizando o comando CURL.

Cadastrar uma malha logística:
> curl -X POST -i -H "Content-Type:application/json" -d '{"mapa":"RJ","rotas":[{"origem":"A","destino":"B","distancia":10},{"origem":"B","destino":"C","distancia":30}]}' http://localhost:8080/app/mapas/cadastrar

Obter um caminho de menor custo:
> curl -X POST -i -H "Content-Type:application/json" -d '{"mapa":"RS","origem":"A","destino":"C","autonomia":10,"valorLitro":2.5}' http://localhost:8080/app/mapas/obter-caminho

### Referências:
* https://en.wikipedia.org/wiki/Dijkstra's_algorithm
* http://algs4.cs.princeton.edu/44sp/
* http://rosettacode.org/wiki/Dijkstra's_algorithm#Java
* http://www.dreamincode.net/forums/topic/386358-dijkstras-algorithm/
* http://www.maxburstein.com/blog/introduction-to-graph-theory-finding-shortest-path/
* http://krishnalearnings.blogspot.com.br/2015/07/implementation-in-java-for-dijkstras.html
