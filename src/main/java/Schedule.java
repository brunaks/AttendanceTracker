/**
 * Created by Bruna Koch Schmitt on 07/08/2016.
 */
public class Schedule {

    private Time startTime = new Time();
    private Time endTime = new Time();
    private Days day;

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
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

    public void addStartTime(Double time, TimePeriod period) {
        this.startTime.time = time;
        this.startTime.period = period;
    }

    public void addEndTime(Double time, TimePeriod period) {
        this.endTime.time = time;
        this.endTime.period = period;
    }

    public class Time {
        public Double time;
        public TimePeriod period;
    }
}
