package services;

import org.joda.time.DateTime;

public class RealClock implements IClock {
    @Override
    public DateTime getCurrentDateTime() {
        return DateTime.now();
    }
}
