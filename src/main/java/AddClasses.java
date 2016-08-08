import java.util.List;

/**
 * Created by Bruna Koch Schmitt on 07/08/2016.
 */
public class AddClasses {

    private final AddClassesReceiver receiver;
    private final ClassRepository repository;

    public AddClasses(ClassRepository repository, AddClassesReceiver receiver) {
        this.repository = repository;
        this.receiver = receiver;
    }

    public void addClass(Class myClass) {
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
}
