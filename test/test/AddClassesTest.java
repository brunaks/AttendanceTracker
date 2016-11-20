package test;

import Core.*;
import Core.Entities.Class;
import Core.Entities.Schedule;
import Core.Entities.Time;
import Core.Persistence.ClassRepository;
import Core.Persistence.ClassRepositoryInMemory;
import Core.UseCases.AddClass;
import Core.UseCases.ReadClasses;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Bruna Koch Schmitt on 07/08/2016.
 */
public class AddClassesTest {

    private AddClassesReceiver receiver;
    private ClassRepository classRepository;
    private AddClass addClasses;

    @Before
    public void setUp() {
        receiver = new AddClassesReceiver();
        classRepository = new ClassRepositoryInMemory();
        addClasses = new AddClass(classRepository, receiver);
    }

    public ClassRequest givenClass(String className, String professorName, Schedule.Days day,
                                   int startTimeHour, int startTimeMinutes, Time.TimePeriod startTimePeriod,
                                   int endTimeHour, int endTimeMinutes, Time.TimePeriod endTimePeriod) {

        ClassRequest classRequest = new ClassRequest();
        classRequest.className = className;
        classRequest.professorName = professorName;

        ScheduleRequest scheduleRequest = new ScheduleRequest();
        scheduleRequest.startTimeHours = startTimeHour;
        scheduleRequest.startTimeMinutes = startTimeMinutes;
        scheduleRequest.startTimePeriod = startTimePeriod;

        scheduleRequest.endTimeHours = endTimeHour;
        scheduleRequest.endTimeMinutes = endTimeMinutes;
        scheduleRequest.endTimePeriod = endTimePeriod;

        scheduleRequest.day = day;
        classRequest.schedules = new ArrayList<>();
        classRequest.schedules.add(scheduleRequest);
        return classRequest;
    }

    @Test
    public void addOneClassFromRequest() {

        ClassRequest classRequest = givenClass("Class 1", "Professor", Schedule.Days.MONDAY,
                7, 0, Time.TimePeriod.PM,
                10, 30, Time.TimePeriod.PM);
        addClasses.addClassFromRequest(classRequest);

        ReadClasses readClasses = new ReadClasses(classRepository);
        List<ClassRequest> classes = readClasses.getAll();
        assertClassesAreEqual(classRequest, classes.get(0));
        Assert.assertTrue(receiver.success);
    }

    @Test
    public void addTwoClassesFromRequest() {

        ClassRequest classRequest = givenClass("Class 1", "Professor", Schedule.Days.MONDAY,
                7, 0, Time.TimePeriod.PM,
                10, 30, Time.TimePeriod.PM);

        addClasses.addClassFromRequest(classRequest);
        Assert.assertTrue(receiver.success);

        ClassRequest classRequest2 = givenClass("Class 1", "Professor", Schedule.Days.TUESDAY,
                8, 0, Time.TimePeriod.PM,
                10, 0, Time.TimePeriod.PM);

        addClasses.addClassFromRequest(classRequest2);
        Assert.assertTrue(receiver.success);

        ReadClasses readClasses = new ReadClasses(classRepository);
        List<ClassRequest> classes = readClasses.getAll();

        Assert.assertEquals(2, classes.size());
        assertClassesAreEqual(classRequest, classes.get(0));
        assertClassesAreEqual(classRequest, classes.get(1));
    }

    @Test
    public void addTwoClassesWithSameScheduleFromRequest() {

        ClassRequest classRequest = givenClass("Class 1", "Professor", Schedule.Days.MONDAY,
                7, 0, Time.TimePeriod.PM,
                10, 30, Time.TimePeriod.PM);

        addClasses.addClassFromRequest(classRequest);
        Assert.assertTrue(receiver.success);

        ClassRequest classRequest2 = givenClass("Class 1", "Professor", Schedule.Days.MONDAY,
                7, 0, Time.TimePeriod.PM,
                10, 30, Time.TimePeriod.PM);

        addClasses.addClassFromRequest(classRequest2);
        Assert.assertFalse(receiver.success);

        ReadClasses readClasses = new ReadClasses(classRepository);
        List<ClassRequest> classes = readClasses.getAll();

        Assert.assertEquals(1, classes.size());
        assertClassesAreEqual(classRequest, classes.get(0));
    }

    @Test
    public void onlyAddClassWithValidSchedule() {
        ClassRequest classRequest = givenClass("Class 1", "Professor", Schedule.Days.MONDAY,
                10, 31, Time.TimePeriod.PM,
                10, 30, Time.TimePeriod.PM);
        addClasses.addClassFromRequest(classRequest);

        ReadClasses readClasses = new ReadClasses(classRepository);
        List<ClassRequest> classes = readClasses.getAll();

        Assert.assertEquals(0, classes.size());
        Assert.assertTrue(receiver.endTimeBeforeStartTime);
    }

    private void assertClassesAreEqual(ClassRequest classRequest, ClassRequest retrievedClass) {
        assertEquals(classRequest.className, retrievedClass.className);
        assertEquals(classRequest.professorName, retrievedClass.professorName);
        assertEquals(classRequest.schedules.size(), retrievedClass.schedules.size());
        for (int index = 0; index < classRequest.schedules.size(); index++) {
            ScheduleRequest schedule = classRequest.schedules.get(index);
            ScheduleRequest retrievedSchedule = classRequest.schedules.get(index);
            assertEquals(schedule.startTimeHours, retrievedSchedule.startTimeHours);
            assertEquals(schedule.startTimeMinutes, retrievedSchedule.startTimeMinutes);
            assertEquals(schedule.startTimePeriod, retrievedSchedule.startTimePeriod);

            assertEquals(schedule.endTimeHours, retrievedSchedule.endTimeHours);
            assertEquals(schedule.endTimeMinutes, retrievedSchedule.endTimeMinutes);
            assertEquals(schedule.endTimePeriod, retrievedSchedule.endTimePeriod);

            assertEquals(schedule.day,retrievedSchedule.day);
        }
    }
}

