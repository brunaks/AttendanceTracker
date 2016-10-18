package Core;

import Core.Entities.Schedule;
import Core.Entities.Time;

/**
 * Created by I848075 on 08/08/2016.
 */
public class ScheduleRequest {

    public int startTimeHours;
    public int startTimeMinutes;
    public Time.TimePeriod startTimePeriod;

    public int endTimeHours;
    public int endTimeMinutes;
    public Time.TimePeriod endTimePeriod;

    public Schedule.Days day;
}
