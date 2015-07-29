package utils.db;

import com.avaje.ebean.config.dbplatform.DbType;
import com.avaje.ebean.config.dbplatform.PostgresPlatform;
import java.sql.Types;

/**
 * Ebean database platform pro PostGIS.
 */
public class PostGisPlatform extends PostgresPlatform {
    public PostGisPlatform() {
        super();

        dbTypeMap.put(Types.OTHER, new DbType("other"));
        dbTypeMap.put(Types.VARCHAR, new DbType("text"));
    }
    
    
}