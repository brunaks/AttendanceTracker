import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by Bruna Koch Schmitt on 07/08/2016.
 */
public class AddClassesTest {

    @Test
    public void add_one_class() {

        AddClasses addClasses = new AddClasses();

        Schedule schedule = new Schedule();
        schedule.addDay(Schedule.Days.MONDAY);
        schedule.addStartTime(7.0, Schedule.TimePeriod.PM);
        schedule.addEndTime(10.30, Schedule.TimePeriod.PM);

        Class myClass = new Class();
        myClass.addName("Class 1");
        myClass.addSchedule(schedule);
        myClass.addProfessor("Professor");

        addClasses.addClass(myClass);

        ClassReader classReader = new ClassReader();
        List<Class> classes = classReader.getAll();

        Assert.assertEquals(myClass.getName(), classes.get(1).getName());
    }
}
