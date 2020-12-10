package fr.simplon.fantoir.dialect;

import org.hibernate.dialect.Dialect;
import java.sql.Types;

public class SQLiteDialect extends Dialect { 
    
    public SQLiteDialect() {
        registerColumnType(Types.CHAR, "id");
        registerColumnType(Types.CHAR, "numero");
        registerColumnType(Types.CHAR, "nom_voie");
        registerColumnType(Types.CHAR, "code_postal");
        registerColumnType(Types.CHAR, "nom_commune");
    }
} 