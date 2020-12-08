CREATE TABLE Commune (
    code_postal INT,
    code_insee  INT,
    nom_commune VARCHAR (6),
    PRIMARY KEY (
        code_insee
    )
);


CREATE TABLE Adresse (
    numero     INT          NOT NULL,
    rep        VARCHAR (0),
    nom_voie   VARCHAR (26) NOT NULL,
    code_insee INT          NOT NULL
                            REFERENCES Commune (code_insee),
    id         INTEGER      PRIMARY KEY AUTOINCREMENT
);
