package Core;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bruna Koch Schmitt on 07/08/2016.
 */
public class Class {

    private String name;
    private List<Schedule> schedule = new ArrayList<Schedule>();
    private String professorName;

    public void addName(String name) {
        this.name = name;
    }

    public void addSchedule(Schedule schedule) {
        this.schedule.add(schedule);
    }

    public void addProfessorName(String professorName) {
        this.professorName = professorName;
    }

    public String getName() {
        return name;
    }

    public List<Schedule> getSchedule() {
        return schedule;
    }

    public String getProfessorName() {
        return professorName;
    }
}
