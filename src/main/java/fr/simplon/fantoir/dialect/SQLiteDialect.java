package fr.simplon.fantoir.dialect;

//Importation des librairies
import org.hibernate.dialect.Dialect;
import java.sql.Types;

public class SQLiteDialect extends Dialect { 
    
    //Initialisation des variables correspondant aux colonnes de la table
    public SQLiteDialect() {
        registerColumnType(Types.CHAR, "id");
        registerColumnType(Types.CHAR, "numero");
        registerColumnType(Types.CHAR, "nom_voie");
        registerColumnType(Types.CHAR, "code_postal");
        registerColumnType(Types.CHAR, "nom_commune");
    }
} 