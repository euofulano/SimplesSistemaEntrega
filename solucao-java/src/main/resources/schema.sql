CREATE TABLE mapas (
    id INTEGER PRIMARY KEY AUTOINCREMENT, 
    mapa VARCHAR(16), 
    origem CHAR(1), 
    destino CHAR(1), 
    distancia INTEGER
);

INSERT INTO mapas (mapa, origem, destino, distancia) VALUES ("SP", "A", "B", 10);
INSERT INTO mapas (mapa, origem, destino, distancia) VALUES ("SP", "B", "D", 15);
INSERT INTO mapas (mapa, origem, destino, distancia) VALUES ("SP", "A", "C", 20);
INSERT INTO mapas (mapa, origem, destino, distancia) VALUES ("SP", "C", "D", 30);
INSERT INTO mapas (mapa, origem, destino, distancia) VALUES ("SP", "B", "E", 50);
INSERT INTO mapas (mapa, origem, destino, distancia) VALUES ("SP", "D", "E", 30);