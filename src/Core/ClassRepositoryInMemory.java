package Core;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bruna Koch Schmitt on 07/08/2016.
 */
public class ClassRepositoryInMemory implements ClassRepository {

    private List<java.lang.Class> classes = new ArrayList<java.lang.Class>();

    public ClassRepositoryInMemory() {
    }

    public void add(java.lang.Class myClass) {
        this.classes.add(myClass);
    }

    public List<java.lang.Class> getAll() {
        return classes;
    }
}
