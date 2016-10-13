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
        MONDAY ("MO"), TUESDAY ("TU"), WEDNESDAY ("WE"), THURSDAY ("TH"), FRIDAY ("FR"), SATURDAY ("SA"), SUNDAY ("SU");

        private String code;

        Days(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        public static Days getFromCode(String code) {
            for (Days day: Days.values())  {
                if (day.getCode().equalsIgnoreCase(code)) {
                    return day;
                }
            }
            return null;
        }
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
