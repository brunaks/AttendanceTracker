import java.time.LocalTime;
import java.util.List;

/**
 * Created by Bruna Koch Schmitt on 07/08/2016.
 */
public class Schedule {

    private LocalTime startTime;
    private LocalTime endTime;
    private Days day;

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
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
        return this.day == schedule.day && !this.startTime.isAfter(schedule.getEndTime()) && !schedule.startTime.isAfter(this.getEndTime());
    }
}
