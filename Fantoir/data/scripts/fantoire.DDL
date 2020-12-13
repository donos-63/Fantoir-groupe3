/* Create Commune table */
CREATE TABLE Commune (
    code_postal INT         NOT NULL,
    code_insee  STRING      NOT NULL
                            PRIMARY KEY,
    nom_commune VARCHAR (30) NOT NULL,
    PRIMARY KEY (
        code_insee
    )
);

/* Create Adresse table */
CREATE TABLE Adresse (
    numero     INT,
    rep        VARCHAR (0),
    nom_voie   VARCHAR (26) NOT NULL,
    code_insee INT          NOT NULL
                            REFERENCES Commune (code_insee),
    id         INTEGER      PRIMARY KEY AUTOINCREMENT
);
