package Core;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bruna Koch Schmitt on 07/08/2016.
 */
public class Class {

    private String id;
    private String name;
    private List<Schedule> schedule = new ArrayList<Schedule>();
    private String professorName;

    public void setName(String name) {
        this.name = name;
    }

    public void addSchedule(Schedule schedule) {
        this.schedule.add(schedule);
    }

    public void setProfessorName(String professorName) {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
