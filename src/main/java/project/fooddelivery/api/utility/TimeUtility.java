package project.fooddelivery.api.utility;

import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;

public class TimeUtility {

    public static LocalTime convertFromInstantToLocalTime(Instant instant){
        ZoneId zone = ZoneId.of("Africa/Cairo");
        return LocalTime.ofInstant(instant, zone);
    }
}
