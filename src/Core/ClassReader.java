package Core;

import java.util.List;

/**
 * Created by Bruna Koch Schmitt on 07/08/2016.
 */
public class ClassReader {

    private ClassRepository repository;

    public ClassReader(ClassRepository repository) {
        this.repository = repository;
    }

    public List<Class> getAll() {
        return repository.getAll();
    }
}
