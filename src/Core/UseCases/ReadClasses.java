package Core.UseCases;

import Core.ClassRequest;
import Core.Entities.*;
import Core.Entities.Class;
import Core.Persistence.ClassRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bruna Koch Schmitt on 07/08/2016.
 */
public class ReadClasses {

    private ClassRepository repository;

    public ReadClasses(ClassRepository repository) {
        this.repository = repository;
    }

    public List<ClassRequest> getAll() {
        return buildClassRequestsFrom(repository.getAll());
    }

    private List<ClassRequest> buildClassRequestsFrom(List<Class> classes) {
        ArrayList<ClassRequest> classRequests = new ArrayList<>();
        for (Class myClass : classes) {
            ClassRequest classRequest = new ClassRequest();
            classRequest.className = myClass.getName();
            classRequest.professsorName = myClass.getProfessorName();
            classRequest.schedules = ClassRequest.convertFrom(myClass.getSchedule());
            classRequests.add(classRequest);
        }
        return classRequests;
    }
}
