import base.GuiceApplicationBaseTest;
import com.google.common.collect.ImmutableMap;
import org.junit.*;
import utils.db.FixturesUtil;

import java.util.Map;

public class DatabaseTest extends GuiceApplicationBaseTest {

    @Test
    public void createDatabase() {
        //
    }

    @Override
    protected Map<String, String> getDatabaseConfig() {
        return ImmutableMap.of(
            "driver"  , "org.postgresql.Driver",
            "url"     , "jdbc:postgresql://localhost/ubipri",
            "username", "postgres",
            "password", "postgres"
        );
    }

    @Override
    public void stopPlay() {
        // do nothing
    }
}
