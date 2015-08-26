package unit.services;

import org.joda.time.DateTime;
import services.IClock;

/**
 * Created by mayconbordin on 26/08/15.
 */
public class MockClock implements IClock {

    private static DateTime dt = DateTime.now();

    public static void setDateTime(DateTime dt) {
        MockClock.dt = dt;
    }

    @Override
    public DateTime getCurrentDateTime() {
        return dt;
    }
}
