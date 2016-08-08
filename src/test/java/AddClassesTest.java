import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by Bruna Koch Schmitt on 07/08/2016.
 */
public class AddClassesTest {

    private FakeAddClassesReceiver receiver;
    private ClassRepository classRepository;
    private AddClasses addClasses;

    @Before
    public void setUp() {
        receiver = new FakeAddClassesReceiver();
        classRepository = new ClassRepositoryInMemory();
        addClasses = new AddClasses(classRepository, receiver);
    }

    @Test
    public void addOneClass() {

        Schedule schedule = new Schedule();
        schedule.addDay(Schedule.Days.MONDAY);
        schedule.addStartTime(7, 0, Schedule.TimePeriod.PM);
        schedule.addEndTime(10, 30, Schedule.TimePeriod.PM);

        Class myClass = new Class();
        myClass.addName("Class 1");
        myClass.addSchedule(schedule);
        myClass.addProfessorName("Professor");

        addClasses.addClass(myClass);

        ClassReader classReader = new ClassReader(classRepository);
        List<Class> classes = classReader.getAll();

        assertSameClass(myClass, classes.get(0));
        Assert.assertTrue(receiver.success);
    }

    @Test
    public void addTwoClasses() {

        Schedule schedule = new Schedule();
        schedule.addDay(Schedule.Days.MONDAY);
        schedule.addStartTime(7, 0, Schedule.TimePeriod.PM);
        schedule.addEndTime(10, 30, Schedule.TimePeriod.PM);

        Class myClass = new Class();
        myClass.addName("Class 1");
        myClass.addSchedule(schedule);
        myClass.addProfessorName("Professor");

        addClasses.addClass(myClass);

        Assert.assertTrue(receiver.success);

        Schedule schedule2 = new Schedule();
        schedule2.addDay(Schedule.Days.TUESDAY);
        schedule2.addStartTime(7, 0, Schedule.TimePeriod.PM);
        schedule2.addEndTime(10, 30, Schedule.TimePeriod.PM);

        Class myClass2 = new Class();
        myClass2.addName("Class 1");
        myClass2.addSchedule(schedule2);
        myClass2.addProfessorName("Professor");

        addClasses.addClass(myClass2);

        Assert.assertTrue(receiver.success);

        ClassReader classReader = new ClassReader(classRepository);
        List<Class> classes = classReader.getAll();

        assertSameClass(myClass, classes.get(0));
        assertSameClass(myClass2, classes.get(1));
    }

    @Test
    public void cannotAddTwoClassesSameSchedule() {

        Schedule schedule = new Schedule();
        schedule.addDay(Schedule.Days.MONDAY);
        schedule.addStartTime(7, 0, Schedule.TimePeriod.PM);
        schedule.addEndTime(10, 0, Schedule.TimePeriod.PM);

        Class myClass = new Class();
        myClass.addName("Class 1");
        myClass.addSchedule(schedule);
        myClass.addProfessorName("Professor");

        addClasses.addClass(myClass);

        Assert.assertTrue(receiver.success);

        Schedule schedule2 = new Schedule();
        schedule2.addDay(Schedule.Days.MONDAY);
        schedule2.addStartTime(7, 0, Schedule.TimePeriod.PM);
        schedule2.addEndTime(10, 30, Schedule.TimePeriod.PM);

        Class myClass2 = new Class();
        myClass2.addName("Class 1");
        myClass2.addSchedule(schedule);
        myClass2.addProfessorName("Professor");

        addClasses.addClass(myClass2);

        Assert.assertFalse(receiver.success);

        ClassReader classReader = new ClassReader(classRepository);
        List<Class> classes = classReader.getAll();

        Assert.assertEquals(1, classes.size());
        assertSameClass(myClass, classes.get(0));
    }

    private void assertSameClass(Class expectedClass, Class actualClass) {
        Assert.assertEquals(expectedClass.getName(), actualClass.getName());
        Assert.assertEquals(expectedClass.getSchedule().get(0).getStartTime().toString(),
                            actualClass.getSchedule().get(0).getStartTime().toString());
        Assert.assertEquals(expectedClass.getSchedule().get(0).getEndTime().toString(),
                            actualClass.getSchedule().get(0).getEndTime().toString());
        Assert.assertEquals(expectedClass.getProfessorName(), actualClass.getProfessorName());
    }
}
