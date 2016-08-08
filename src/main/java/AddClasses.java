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
        repository.add(myClass);
        receiver.registrationSuccessful();
    }
}
