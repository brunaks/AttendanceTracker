package Core;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Bruna Koch Schmitt on 07/08/2016.
 */
public class Schedule {

    private Time startTime;
    private Time endTime;
    private Days day;

    public String getStartTime() {
        return startTime.get();
    }

    public String getEndTime() {
        return endTime.get();
    }

    public Days getDay() {
        return day;
    }

    public boolean endTimeIsBeforeStartTime() {
        return startTime.isAfter(endTime);
    }

    public enum Days {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }

    public void addDay(Days day) {
        this.day = day;
    }

    public void addStartTime(int hour, int minutes, Time.TimePeriod period) {
        startTime = new Time(hour, minutes, period);
    }

    public void addEndTime(int hour, int minutes, Time.TimePeriod period) {
        endTime = new Time(hour, minutes, period);
    }

    public boolean isOverlappedWith(Schedule schedule) {
        return this.day == schedule.day && !startTime.isAfter(schedule.endTime) && !schedule.startTime.isAfter(endTime);
    }
}
