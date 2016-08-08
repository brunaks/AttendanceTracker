package test;

import Core.*;
import Core.Class;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Time;
import java.util.List;

/**
 * Created by Bruna Koch Schmitt on 07/08/2016.
 */
public class AddClassesTest {

    private FakeAddClassesReceiver receiver;
    private ClassRepository classRepository;
    private AddClass addClasses;

    @Before
    public void setUp() {
        receiver = new FakeAddClassesReceiver();
        classRepository = new ClassRepositoryInMemory();
        addClasses = new AddClass(classRepository, receiver);
    }

    public ClassRequest givenClass(String className, String professorName, Schedule.Days day,
                                   int startTimeHour, int startTimeMinutes, Schedule.TimePeriod startTimePeriod,
                                   int endTimeHour, int endTimeMinutes, Schedule.TimePeriod endTimePeriod) {

        ClassRequest classRequest = new ClassRequest();
        classRequest.className = className;
        classRequest.professsorName = professorName;

        ScheduleRequest scheduleRequest = new ScheduleRequest();
        scheduleRequest.startTimeHours = startTimeHour;
        scheduleRequest.startTimeMinutes = startTimeMinutes;
        scheduleRequest.startTimePeriod = startTimePeriod;

        scheduleRequest.endTimeHours = endTimeHour;
        scheduleRequest.endTimeMinutes = endTimeMinutes;
        scheduleRequest.endTimePeriod = endTimePeriod;

        scheduleRequest.day = day;
        classRequest.schedules = new ScheduleRequest[] { scheduleRequest };
        return classRequest;
    }

    @Test
    public void addOneClassFromRequest() {

        ClassRequest classRequest = givenClass("Class 1", "Professor", Schedule.Days.MONDAY,
                                                7, 0, Schedule.TimePeriod.PM,
                                                10, 30, Schedule.TimePeriod.PM);
        addClasses.addClassFromRequest(classRequest);

        ClassReader classReader = new ClassReader(classRepository);
        List<Class> classes = classReader.getAll();
        Class actualClass = classes.get(0);

        Assert.assertEquals(classRequest.className, actualClass.getName());
        Assert.assertEquals("19:00", actualClass.getSchedule().get(0).getStartTime().toString());
        Assert.assertEquals("22:30", actualClass.getSchedule().get(0).getEndTime().toString());
        Assert.assertEquals(Schedule.Days.MONDAY, actualClass.getSchedule().get(0).getDay());
        Assert.assertEquals(classRequest.professsorName, actualClass.getProfessorName());
        Assert.assertTrue(receiver.success);
    }

    @Test
    public void addTwoClassesFromRequest() {

        ClassRequest classRequest = givenClass("Class 1", "Professor", Schedule.Days.MONDAY,
                7, 0, Schedule.TimePeriod.PM,
                10, 30, Schedule.TimePeriod.PM);

        addClasses.addClassFromRequest(classRequest);
        Assert.assertTrue(receiver.success);

        ClassRequest classRequest2 = givenClass("Class 1", "Professor", Schedule.Days.TUESDAY,
                8, 0, Schedule.TimePeriod.PM,
                10, 0, Schedule.TimePeriod.PM);

        addClasses.addClassFromRequest(classRequest2);
        Assert.assertTrue(receiver.success);

        ClassReader classReader = new ClassReader(classRepository);
        List<Class> classes = classReader.getAll();

        Assert.assertEquals(2, classes.size());
        Class actualClass1 = classes.get(0);
        Class actualClass2 = classes.get(1);

        Assert.assertEquals(classRequest.className, actualClass1.getName());
        Assert.assertEquals("19:00", actualClass1.getSchedule().get(0).getStartTime().toString());
        Assert.assertEquals("22:30", actualClass1.getSchedule().get(0).getEndTime().toString());
        Assert.assertEquals(Schedule.Days.MONDAY, actualClass1.getSchedule().get(0).getDay());
        Assert.assertEquals(classRequest.professsorName, actualClass1.getProfessorName());

        Assert.assertEquals(classRequest2.className, actualClass2.getName());
        Assert.assertEquals("20:00", actualClass2.getSchedule().get(0).getStartTime().toString());
        Assert.assertEquals("22:00", actualClass2.getSchedule().get(0).getEndTime().toString());
        Assert.assertEquals(Schedule.Days.TUESDAY, actualClass2.getSchedule().get(0).getDay());
        Assert.assertEquals(classRequest.professsorName, actualClass2.getProfessorName());
    }

    @Test
    public void addTwoClassesWithSameScheduleFromRequest() {

        ClassRequest classRequest = givenClass("Class 1", "Professor", Schedule.Days.MONDAY,
                7, 0, Schedule.TimePeriod.PM,
                10, 30, Schedule.TimePeriod.PM);

        addClasses.addClassFromRequest(classRequest);
        Assert.assertTrue(receiver.success);

        ClassRequest classRequest2 = givenClass("Class 1", "Professor", Schedule.Days.MONDAY,
                7, 0, Schedule.TimePeriod.PM,
                10, 30, Schedule.TimePeriod.PM);

        addClasses.addClassFromRequest(classRequest2);
        Assert.assertFalse(receiver.success);

        ClassReader classReader = new ClassReader(classRepository);
        List<Class> classes = classReader.getAll();

        Assert.assertEquals(1, classes.size());
        Class actualClass1 = classes.get(0);

        Assert.assertEquals(classRequest.className, actualClass1.getName());
        Assert.assertEquals("19:00", actualClass1.getSchedule().get(0).getStartTime().toString());
        Assert.assertEquals("22:30", actualClass1.getSchedule().get(0).getEndTime().toString());
        Assert.assertEquals(Schedule.Days.MONDAY, actualClass1.getSchedule().get(0).getDay());
        Assert.assertEquals(classRequest.professsorName, actualClass1.getProfessorName());
    }
}
