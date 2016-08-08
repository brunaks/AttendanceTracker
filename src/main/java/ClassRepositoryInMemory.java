import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bruna Koch Schmitt on 07/08/2016.
 */
public class ClassRepositoryInMemory implements ClassRepository {

    private List<Class> classes = new ArrayList<Class>();

    public ClassRepositoryInMemory() {
    }

    public void add(Class myClass) {
        this.classes.add(myClass);
    }

    public List<Class> getAll() {
        return classes;
    }
}
