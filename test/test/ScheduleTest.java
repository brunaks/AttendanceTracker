package test;

import Core.Schedule;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalTime;

/**
 * Created by I848075 on 08/08/2016.
 */
public class ScheduleTest {

    @Test
    public void conversionFromPM() {

        Schedule schedule = new Schedule();
        schedule.addStartTime(10, 30, Schedule.TimePeriod.PM);

        LocalTime startTimeActual = schedule.getStartTime();

        Assert.assertEquals("10:30 PM", startTimeActual);
    }
}
