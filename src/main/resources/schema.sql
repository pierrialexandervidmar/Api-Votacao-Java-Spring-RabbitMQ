CREATE TABLE candidato
(
    id      SERIAL PRIMARY KEY,
    nome    VARCHAR(255) NOT NULL
);

CREATE TABLE voto
(
    id              SERIAL PRIMARY KEY,
    id_candidato   BIGINT NOT NULL,
    data_hora       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_candidato) REFERENCES candidato (id)
);

