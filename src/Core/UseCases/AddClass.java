package Core.UseCases;

import Core.*;
import Core.Entities.Class;
import Core.Entities.Schedule;
import Core.Persistence.ClassRepository;

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
        List<Class> classes = repository.getAll();

        for (Schedule schedule : myClass.getSchedule()) {
            if (schedule.endTimeIsBeforeStartTime()) {
                receiver.endTimeIsBeforeStartTime();
                return;
            }
        }

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
        aClass.setName(classRequest.className);
        aClass.setProfessorName(classRequest.professsorName);
        Schedule schedule = new Schedule();
        schedule.addStartTime(classRequest.schedules.get(0).startTimeHours, classRequest.schedules.get(0).startTimeMinutes, classRequest.schedules.get(0).startTimePeriod);
        schedule.addEndTime(classRequest.schedules.get(0).endTimeHours, classRequest.schedules.get(0).endTimeMinutes, classRequest.schedules.get(0).endTimePeriod);
        schedule.addDay(classRequest.schedules.get(0).day);
        aClass.addSchedule(schedule);
        addClass(aClass);
    }
}
