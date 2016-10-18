package test;

import Core.Entities.Schedule;
import Core.Entities.Time;
import org.junit.Assert;
import org.junit.Test;

import static Core.Entities.Schedule.Days.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by I848075 on 08/08/2016.
 */
public class ScheduleTest {

    @Test
    public void PMHourShouldBeReturnedCorrectly() {

        Schedule schedule = new Schedule();
        schedule.addStartTime(10, 30, Time.TimePeriod.PM);
        assertEquals("10:30 PM", schedule.getStartTime());
        schedule.addEndTime(11, 30, Time.TimePeriod.PM);
        assertEquals("11:30 PM", schedule.getEndTime());
    }

    @Test
    public void AMHourShouldBeReturnedCorrectly() {
        Schedule schedule = new Schedule();
        schedule.addStartTime(10, 30, Time.TimePeriod.AM);
        assertEquals("10:30 AM", schedule.getStartTime());
        schedule.addEndTime(11, 30, Time.TimePeriod.AM);
        assertEquals("11:30 AM", schedule.getEndTime());
    }

    @Test
    public void endTimeShouldBeAfterStartTime() {
        Schedule schedule = new Schedule();
        schedule.addStartTime(10, 30, Time.TimePeriod.AM);
        schedule.addEndTime(10, 31, Time.TimePeriod.AM);

        Assert.assertFalse(schedule.endTimeIsBeforeStartTime());
    }

    @Test
    public void endTimeisBeforeStartTime_scheduleNotValid() {
        Schedule schedule = new Schedule();
        schedule.addStartTime(10, 31, Time.TimePeriod.AM);
        schedule.addEndTime(10, 30, Time.TimePeriod.AM);

        Assert.assertTrue(schedule.endTimeIsBeforeStartTime());
    }

    @Test
    public void overlappingSchedulesWithSameStartAndEnd() {
        Schedule schedule = new Schedule();
        schedule.addStartTime(10, 30, Time.TimePeriod.AM);
        schedule.addEndTime(11, 30, Time.TimePeriod.AM);

        Schedule scheduleOverlapped = new Schedule();
        scheduleOverlapped.addStartTime(10, 30, Time.TimePeriod.AM);
        scheduleOverlapped.addEndTime(11, 30, Time.TimePeriod.AM);

        Assert.assertTrue(schedule.isOverlappedWith(scheduleOverlapped));
    }

    @Test
    public void overlappingSchedulesWithStartOfFirstScheduleBeforeStartOfSecondSchedule() {
        Schedule schedule = new Schedule();
        schedule.addStartTime(9, 00, Time.TimePeriod.AM);
        schedule.addEndTime(10, 00, Time.TimePeriod.AM);

        Schedule scheduleOverlapped = new Schedule();
        scheduleOverlapped.addStartTime(9, 30, Time.TimePeriod.AM);
        scheduleOverlapped.addEndTime(10, 30, Time.TimePeriod.AM);

        Assert.assertTrue(schedule.isOverlappedWith(scheduleOverlapped));
    }

    @Test
    public void overlappingSchedulesWithStartOfSecondScheduleBeforeStartOfFirstSchedule() {
        Schedule schedule = new Schedule();
        schedule.addStartTime(9, 30, Time.TimePeriod.AM);
        schedule.addEndTime(10, 30, Time.TimePeriod.AM);

        Schedule scheduleOverlapped = new Schedule();
        scheduleOverlapped.addStartTime(9, 00, Time.TimePeriod.AM);
        scheduleOverlapped.addEndTime(10, 00, Time.TimePeriod.AM);

        Assert.assertTrue(schedule.isOverlappedWith(scheduleOverlapped));
    }

    @Test
    public void overlappingSchedulesFirstScheduleContainsSecondScheduleNoCollapsingBoundaries() {
        Schedule schedule = new Schedule();
        schedule.addStartTime(9, 00, Time.TimePeriod.AM);
        schedule.addEndTime(11, 00, Time.TimePeriod.AM);

        Schedule scheduleOverlapped = new Schedule();
        scheduleOverlapped.addStartTime(10, 00, Time.TimePeriod.AM);
        scheduleOverlapped.addEndTime(10, 30, Time.TimePeriod.AM);

        Assert.assertTrue(schedule.isOverlappedWith(scheduleOverlapped));
    }

    @Test
    public void overlappingSchedulesSecondScheduleContainsFirstScheduleNoCollapsingBoundaries() {
        Schedule schedule = new Schedule();
        schedule.addStartTime(10, 00, Time.TimePeriod.AM);
        schedule.addEndTime(10, 30, Time.TimePeriod.AM);

        Schedule scheduleOverlapped = new Schedule();
        scheduleOverlapped.addStartTime(9, 00, Time.TimePeriod.AM);
        scheduleOverlapped.addEndTime(11, 00, Time.TimePeriod.AM);

        Assert.assertTrue(schedule.isOverlappedWith(scheduleOverlapped));
    }

    @Test
    public void overlappingSchedulesFirstScheduleContainsSecondScheduleWithStartCollapsingBoundary() {
        Schedule schedule = new Schedule();
        schedule.addStartTime(9, 00, Time.TimePeriod.AM);
        schedule.addEndTime(11, 00, Time.TimePeriod.AM);

        Schedule scheduleOverlapped = new Schedule();
        scheduleOverlapped.addStartTime(9, 00, Time.TimePeriod.AM);
        scheduleOverlapped.addEndTime(10, 30, Time.TimePeriod.AM);

        Assert.assertTrue(schedule.isOverlappedWith(scheduleOverlapped));
    }

    @Test
    public void overlappingSchedulesSecondScheduleContainsFirstScheduleWithStartCollapsingBoundary() {
        Schedule schedule = new Schedule();
        schedule.addStartTime(9, 00, Time.TimePeriod.AM);
        schedule.addEndTime(10, 30, Time.TimePeriod.AM);

        Schedule scheduleOverlapped = new Schedule();
        scheduleOverlapped.addStartTime(9, 00, Time.TimePeriod.AM);
        scheduleOverlapped.addEndTime(11, 00, Time.TimePeriod.AM);

        Assert.assertTrue(schedule.isOverlappedWith(scheduleOverlapped));
    }

    @Test
    public void overlappingSchedulesFirstScheduleContainsSecondScheduleWithEndCollapsingBoundary() {
        Schedule schedule = new Schedule();
        schedule.addStartTime(9, 00, Time.TimePeriod.AM);
        schedule.addEndTime(11, 00, Time.TimePeriod.AM);

        Schedule scheduleOverlapped = new Schedule();
        scheduleOverlapped.addStartTime(9, 30, Time.TimePeriod.AM);
        scheduleOverlapped.addEndTime(11, 00, Time.TimePeriod.AM);

        Assert.assertTrue(schedule.isOverlappedWith(scheduleOverlapped));
    }

    @Test
    public void overlappingSchedulesSecondScheduleContainsFirstScheduleWithEndCollapsingBoundary() {
        Schedule schedule = new Schedule();
        schedule.addStartTime(9, 30, Time.TimePeriod.AM);
        schedule.addEndTime(11, 00, Time.TimePeriod.AM);

        Schedule scheduleOverlapped = new Schedule();
        scheduleOverlapped.addStartTime(9, 00, Time.TimePeriod.AM);
        scheduleOverlapped.addEndTime(11, 00, Time.TimePeriod.AM);

        Assert.assertTrue(schedule.isOverlappedWith(scheduleOverlapped));
    }

    @Test
    public void overlappingSchedulesOneNextToAnotherWithCollapsingBoundary() {
        Schedule schedule = new Schedule();
        schedule.addStartTime(9, 30, Time.TimePeriod.AM);
        schedule.addEndTime(11, 00, Time.TimePeriod.AM);

        Schedule scheduleOverlapped = new Schedule();
        scheduleOverlapped.addStartTime(11, 00, Time.TimePeriod.AM);
        scheduleOverlapped.addEndTime(01, 00, Time.TimePeriod.PM);

        Assert.assertTrue(schedule.isOverlappedWith(scheduleOverlapped));
    }

    @Test
    public void getDayFromCode() {
        assertEquals(MONDAY, getFromCode("MO"));
        assertEquals(TUESDAY, getFromCode("TU"));
        assertEquals(WEDNESDAY, getFromCode("WE"));
        assertEquals(THURSDAY, getFromCode("TH"));
        assertEquals(FRIDAY, getFromCode("FR"));
        assertEquals(SATURDAY, getFromCode("SA"));
        assertEquals(SUNDAY, getFromCode("SU"));
    }

    @Test
    public void getDayFromCodeIgnoringCase() {
        assertEquals(MONDAY, getFromCode("mo"));
        assertEquals(TUESDAY, getFromCode("tu"));
        assertEquals(WEDNESDAY, getFromCode("we"));
        assertEquals(THURSDAY, getFromCode("th"));
        assertEquals(FRIDAY, getFromCode("fr"));
        assertEquals(SATURDAY, getFromCode("sa"));
        assertEquals(SUNDAY, getFromCode("su"));
    }
}
