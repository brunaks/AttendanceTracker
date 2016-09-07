package Core;

import java.time.LocalTime;

/**
 * Created by Bruna Koch Schmitt on 24/08/2016.
 */
public class Time {

    protected int hour;
    protected int minutes;
    protected TimePeriod period;

    public String get() {
        return getFormattedHour() + ":" + getFormattedMinutes() + " " + period.toString();
    }

    public boolean isAfter(Time time) {
        return convertToLocalTime(this).isAfter(convertToLocalTime(time));
    }

    public String getFormattedHour() {
        if (hour < 10)
            return "0" + hour;
        else
            return Integer.toString(hour);
    }

    public String getFormattedMinutes() {
        if (minutes < 10)
            return "0" + minutes;
        else
            return Integer.toString(minutes);
    }

    public enum TimePeriod {
        AM, PM
    }

    public Time(int hour, int minutes, TimePeriod period) {
        this.hour = hour;
        this.minutes = minutes;
        this.period = period;
    }

    public LocalTime convertToLocalTime(Time time) {
        if (time.period == TimePeriod.PM) {
            if (time.hour + 12 == 24)
                return LocalTime.of(0, time.minutes);
            else
                return LocalTime.of(time.hour + 12, time.minutes);
        }
        else
            return LocalTime.of(time.hour, time.minutes);
    }
}
