package test;

import Core.AddClassesReceiver;
import Core.ClassRequest;
import Core.Entities.Class;
import Core.Entities.Schedule;
import Core.Entities.Time;
import Core.Entities.Time.TimePeriod;
import Core.Persistence.ClassRepositoryInMemory;
import Core.ScheduleRequest;
import Core.UseCases.AddClass;
import Core.UseCases.ReadClasses;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import static Core.Entities.Schedule.Days.MONDAY;
import static Core.Entities.Schedule.Days.TUESDAY;
import static Core.Entities.Time.TimePeriod.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Bruna Koch Schmitt on 16/10/2016.
 */
public class ReadClassesTest {

    AddClass addClass;
    ClassRepositoryInMemory classRepositoryInMemory;
    AddClassesReceiver addClassesReceiver = new AddClassesReceiver();
    ReadClasses readClasses;

    @Before
    public void setUp() throws Exception {
        classRepositoryInMemory = new ClassRepositoryInMemory();
        addClass = new AddClass(classRepositoryInMemory, addClassesReceiver);
        readClasses = new ReadClasses(classRepositoryInMemory);
    }

    @Test
    public void noClassAdded_shouldReadNoClassToo() {
        assertEquals(0, readClasses.getAll().size());
    }

    @Test
    public void addedOneClass_readOneClass() {
        ClassRequest classRequest = givenClass("Class1", "Professor1", 7, 0, PM, 10, 0, PM, MONDAY);
        addClass.addClassFromRequest(classRequest);
        List<ClassRequest> classes = readClasses.getAll();
        assertEquals(1, classes.size());
        assertNotEquals(classes.get(0), classRequest);
        assertClassesAreEqual(classRequest, classes.get(0));
    }

    @Test
    public void addedTwoClasses_readTwoClasses() {
        ClassRequest classRequest = givenClass("Class1", "Professor1", 7, 0, PM, 10, 0, PM, MONDAY);
        addClass.addClassFromRequest(classRequest);
        ClassRequest classRequest2 = givenClass("Class2", "Professor2", 7, 0, PM, 10, 0, PM, TUESDAY);
        addClass.addClassFromRequest(classRequest2);
        List<ClassRequest> classes = readClasses.getAll();
        assertEquals(2, classes.size());
        assertNotEquals(classRequest, classes.get(0));
        assertClassesAreEqual(classRequest, classes.get(0));
        assertNotEquals(classRequest2, classes.get(1));
        assertClassesAreEqual(classRequest2, classes.get(1));
    }

    private ClassRequest givenClass(String className, String professorName, int startHours, int startMinutes, TimePeriod startPeriod, int endHours, int endMinutes, TimePeriod endPeriod, Schedule.Days day) {
        ClassRequest classRequest = new ClassRequest();
        classRequest.className = className;
        classRequest.professorName = professorName;
        ScheduleRequest schedule = new ScheduleRequest();
        schedule.startTimeHours = startHours;
        schedule.startTimeMinutes = startMinutes;
        schedule.startTimePeriod = startPeriod;
        schedule.endTimeHours = endHours;
        schedule.endTimeMinutes = endMinutes;
        schedule.endTimePeriod = endPeriod;
        schedule.day = day;
        ArrayList<ScheduleRequest> schedules = new ArrayList<>();
        schedules.add(schedule);
        classRequest.schedules = schedules;
        return classRequest;
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





