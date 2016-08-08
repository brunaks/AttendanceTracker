package Core;

/**
 * Created by I848075 on 08/08/2016.
 */
public class ScheduleRequest {

    public int startTimeHours;
    public int startTimeMinutes;
    public Schedule.TimePeriod startTimePeriod;

    public int endTimeHours;
    public int endTimeMinutes;
    public Schedule.TimePeriod endTimePeriod;

    public Schedule.Days day;
}
