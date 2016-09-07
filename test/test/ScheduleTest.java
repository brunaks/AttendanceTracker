package test;

import Core.Schedule;
import Core.Time;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalTime;

/**
 * Created by I848075 on 08/08/2016.
 */
public class ScheduleTest {

    @Test
    public void PMHourShouldBeReturnedCorrectly() {

        Schedule schedule = new Schedule();
        schedule.addStartTime(10, 30, Time.TimePeriod.PM);
        Assert.assertEquals("10:30 PM", schedule.getStartTime());
        schedule.addEndTime(11, 30, Time.TimePeriod.PM);
        Assert.assertEquals("11:30 PM", schedule.getEndTime());
    }

    @Test
    public void AMHourShouldBeReturnedCorrectly() {
        Schedule schedule = new Schedule();
        schedule.addStartTime(10, 30, Time.TimePeriod.AM);
        Assert.assertEquals("10:30 AM", schedule.getStartTime());
        schedule.addEndTime(11, 30, Time.TimePeriod.AM);
        Assert.assertEquals("11:30 AM", schedule.getEndTime());
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
}
