package services;

import org.joda.time.DateTime;

public interface IClock {
    DateTime getCurrentDateTime();
}