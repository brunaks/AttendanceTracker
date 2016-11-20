package Core;

import Core.Entities.Schedule;
import Core.Entities.Time;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by I848075 on 08/08/2016.
 */
public class ClassRequest {

    public String className;
    public String professorName;
    public List<ScheduleRequest> schedules;

    public static List<ScheduleRequest> convertFrom(List<Schedule> schedules) {
        ArrayList<ScheduleRequest> scheduleRequests = new ArrayList<>();
        for (Schedule schedule : schedules) {
            ScheduleRequest scheduleRequest = new ScheduleRequest();

            scheduleRequest.startTimeHours = Integer.parseInt(schedule.getStartTime().substring(0,2));
            scheduleRequest.startTimeMinutes = Integer.parseInt(schedule.getStartTime().substring(3,5));
            scheduleRequest.startTimePeriod = Time.TimePeriod.valueOf(schedule.getStartTime().substring(6, 8));

            scheduleRequest.endTimeHours = Integer.parseInt(schedule.getEndTime().substring(0,2));
            scheduleRequest.endTimeMinutes = Integer.parseInt(schedule.getEndTime().substring(3,5));
            scheduleRequest.endTimePeriod = Time.TimePeriod.valueOf(schedule.getEndTime().substring(6, 8));

            scheduleRequest.day = schedule.getDay();

            scheduleRequests.add(scheduleRequest);
        }
        return scheduleRequests;
    }
}
