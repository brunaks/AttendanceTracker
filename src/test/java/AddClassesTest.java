import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by Bruna Koch Schmitt on 07/08/2016.
 */
public class AddClassesTest {

    @Test
    public void add_one_class() {

        FakeAddClassesReceiver receiver = new FakeAddClassesReceiver();
        ClassRepository classRepository = new ClassRepositoryInMemory();

        AddClasses addClasses = new AddClasses(classRepository, receiver);

        Schedule schedule = new Schedule();
        schedule.addDay(Schedule.Days.MONDAY);
        schedule.addStartTime(7.0, Schedule.TimePeriod.PM);
        schedule.addEndTime(10.30, Schedule.TimePeriod.PM);

        Class myClass = new Class();
        myClass.addName("Class 1");
        myClass.addSchedule(schedule);
        myClass.addProfessorName("Professor");

        addClasses.addClass(myClass);

        ClassReader classReader = new ClassReader(classRepository);
        List<Class> classes = classReader.getAll();

        Assert.assertEquals(myClass.getName(), classes.get(0).getName());
        Assert.assertEquals(myClass.getSchedule().get(0).getStartTime().time, classes.get(0).getSchedule().get(0).getStartTime().time);
        Assert.assertEquals(myClass.getSchedule().get(0).getStartTime().period, classes.get(0).getSchedule().get(0).getStartTime().period);
        Assert.assertEquals(myClass.getSchedule().get(0).getEndTime().time, classes.get(0).getSchedule().get(0).getEndTime().time);
        Assert.assertEquals(myClass.getSchedule().get(0).getEndTime().period, classes.get(0).getSchedule().get(0).getEndTime().period);
        Assert.assertEquals(myClass.getProfessorName(), classes.get(0).getProfessorName());
        Assert.assertTrue(receiver.success);
    }
}
