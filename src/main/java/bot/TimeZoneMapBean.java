package bot;

import org.springframework.stereotype.Component;
import us.dustinj.timezonemap.TimeZoneMap;

@Component
public class TimeZoneMapBean {
    private final TimeZoneMap timeZoneMap;

    public TimeZoneMapBean() {
        timeZoneMap = TimeZoneMap.forEverywhere();
    }

    public String getTimeZone(Double latitude, Double longitude) {
        return timeZoneMap.getOverlappingTimeZone(latitude, longitude).getZoneId();
    }
}
