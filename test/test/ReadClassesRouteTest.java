package test;

import Core.Entities.*;
import Core.Entities.Class;
import Core.Persistence.ClassRepositoryInMemory;
import Core.Routes.ReadClassesRoute;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;
import java.util.function.ToIntBiFunction;

/**
 * Created by Bruna Koch Schmitt on 18/11/2016.
 */
public class ReadClassesRouteTest {

    @Test
    public void readOneClasses() throws Exception {
        ClassRepositoryInMemory classRepositoryInMemory = new ClassRepositoryInMemory();
        Class oneClass = new Class();
        String id = UUID.randomUUID().toString();
        oneClass.setId(id);
        oneClass.setName("Class Name");
        oneClass.setProfessorName("Professor Name");
        Schedule schedule = new Schedule();
        schedule.addStartTime(07, 00, Time.TimePeriod.PM);
        schedule.addEndTime(10, 00, Time.TimePeriod.PM);
        schedule.addDay(Schedule.Days.MONDAY);
        oneClass.addSchedule(schedule);
        classRepositoryInMemory.add(oneClass);
        ReadClassesRoute readClassesRoute = new ReadClassesRoute(classRepositoryInMemory);
        String result = readClassesRoute.handle(null, null).toString();
        JSONArray parsedResult = new JSONArray(result);
        JSONObject jsonObject = parsedResult.getJSONObject(0);

        Assert.assertEquals("Class Name", jsonObject.get("className"));
        Assert.assertEquals("Professor Name", jsonObject.get("professorName"));
        JSONArray schedules = (JSONArray)jsonObject.get("schedules");
        JSONObject parsedSchedule = schedules.getJSONObject(0);
        Assert.assertEquals(7, parsedSchedule.get("startTimeHours"));
        Assert.assertEquals(0, parsedSchedule.get("startTimeMinutes"));
        Assert.assertEquals("PM", parsedSchedule.get("startTimePeriod"));
        Assert.assertEquals(10, parsedSchedule.get("endTimeHours"));
        Assert.assertEquals(0, parsedSchedule.get("endTimeMinutes"));
        Assert.assertEquals("PM", parsedSchedule.get("endTimePeriod"));
        Assert.assertEquals("MONDAY", parsedSchedule.get("day"));
    }
}
