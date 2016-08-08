package Core;

import java.util.List;

/**
 * Created by Bruna Koch Schmitt on 07/08/2016.
 */
public class AddClass {

    private final AddClassReceiver receiver;
    private final ClassRepository repository;

    public AddClass(ClassRepository repository, AddClassReceiver receiver) {
        this.repository = repository;
        this.receiver = receiver;
    }

    private void addClass(Class myClass) {
        ClassReader reader = new ClassReader(repository);
        List<Class> classes = reader.getAll();

        for (Class oneClass : classes) {
            for (Schedule scheduleAlreadyAdded : oneClass.getSchedule()) {
                for (Schedule schedule : myClass.getSchedule()) {
                    if (scheduleAlreadyAdded.isOverlappedWith(schedule) == true) {
                        receiver.registrationFailed();
                        return;
                    }
                }
            }
        }

        repository.add(myClass);
        receiver.registrationSuccessful();
    }

    public void addClassFromRequest(ClassRequest classRequest) {
        Class aClass = new Class();
        aClass.addName(classRequest.className);
        aClass.addProfessorName(classRequest.professsorName);
        Schedule schedule = new Schedule();
        schedule.addStartTime(classRequest.schedules[0].startTimeHours, classRequest.schedules[0].startTimeMinutes, classRequest.schedules[0].startTimePeriod);
        schedule.addEndTime(classRequest.schedules[0].endTimeHours, classRequest.schedules[0].endTimeMinutes, classRequest.schedules[0].endTimePeriod);
        schedule.addDay(classRequest.schedules[0].day);
        aClass.addSchedule(schedule);
        addClass(aClass);
    }
}
