CREATE TABLE mapas (
    id INTEGER PRIMARY KEY AUTOINCREMENT, 
    mapa VARCHAR(16), 
    origem CHAR(1), 
    destino CHAR(1), 
    distancia INTEGER
);