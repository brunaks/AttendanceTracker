package Core;

import java.time.LocalTime;

/**
 * Created by Bruna Koch Schmitt on 07/08/2016.
 */
public class Schedule {

    private LocalTime startTime;
    private LocalTime endTime;
    private int startHours;
    private int startMinutes;
    private TimePeriod startPeriod;
    private int endHours;
    private int endMinutes;
    private TimePeriod endPeriod;
    private Days day;

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public Days getDay() {
        return day;
    }

    public enum Days {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }

    public enum TimePeriod {
        AM, PM
    }

    public void addDay(Days day) {
        this.day = day;
    }

    public void addStartTime(int hour, int minutes, TimePeriod period) {
        if (period == TimePeriod.AM)
            startTime = LocalTime.of(hour, minutes);
        else
            startTime = LocalTime.of(hour+12, minutes);
    }

    public void addEndTime(int hour, int minutes, TimePeriod period) {
        if (period == TimePeriod.AM)
            endTime = LocalTime.of(hour, minutes);
        else
            endTime = LocalTime.of(hour+12, minutes);
    }

    public boolean isOverlappedWith(Schedule schedule) {
        LocalTime startTime = LocalTime.of(this.startHours, this.startMinutes);
        LocalTime endTime = LocalTime.of(this.endHours, this.endMinutes);

        return this.day == schedule.day && !startTime.isAfter(schedule.endTime) && !schedule.startTime.isAfter(this.endTime);
    }
}
