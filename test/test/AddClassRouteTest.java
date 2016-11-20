package test;

import Core.ClassRequest;
import Core.Entities.Schedule;
import Core.Entities.Time;
import Core.Routes.AddClassRoute;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Bruna Koch Schmitt on 15/11/2016.
 */
public class AddClassRouteTest {

    @Test
    public void convertRequestToClass() {
        JSONObject request = new JSONObject();
        request.put("className", "Class 1".toString());
        request.put("professorName", "Professor 1");
        request.put("startTime", "07:00 PM");
        request.put("endTime", "10:00 PM");
        request.put("day", "MO");
        AddClassRoute addClassRoute = new AddClassRoute(null, null);
        ClassRequest classRequest = addClassRoute.convertRequest(request);
        Assert.assertEquals("Class 1", classRequest.className);
        Assert.assertEquals("Professor 1", classRequest.professorName);
        Assert.assertEquals(07, classRequest.schedules.get(0).startTimeHours);
        Assert.assertEquals(00, classRequest.schedules.get(0).startTimeMinutes);
        Assert.assertEquals(Time.TimePeriod.PM, classRequest.schedules.get(0).startTimePeriod);
        Assert.assertEquals(10, classRequest.schedules.get(0).endTimeHours);
        Assert.assertEquals(00, classRequest.schedules.get(0).endTimeMinutes);
        Assert.assertEquals(Time.TimePeriod.PM, classRequest.schedules.get(0).endTimePeriod);
        Assert.assertEquals(Schedule.Days.MONDAY, classRequest.schedules.get(0).day);
    }
}
