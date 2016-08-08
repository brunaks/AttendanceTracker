package Core;

import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Created by Bruna Koch Schmitt on 08/08/2016.
 */
public class AddClassRoute implements Route {

    private final ClassRepository repository;
    private final AddClassReceiver receiver;
    Gson converter = new Gson();

    public AddClassRoute(ClassRepository classRepositoryInMemory, AddClassReceiver addClassesReceiver) {
        this.repository = classRepositoryInMemory;
        this.receiver = addClassesReceiver;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        Class myClass = new Class();
        myClass.addName(request.queryParams("className"));
        Schedule schedule = new Schedule();
        schedule.addStartTime(Integer.parseInt(request.queryParams("startHour")),
                Integer.parseInt(request.queryParams("startMinutes")),
                Schedule.TimePeriod.valueOf(request.queryParams("startPeriod")));
        schedule.addEndTime(Integer.parseInt(request.queryParams("endHour")),
                Integer.parseInt(request.queryParams("endMinutes")),
                Schedule.TimePeriod.valueOf(request.queryParams("endPeriod")));
        myClass.addSchedule(schedule);
        myClass.addProfessorName(request.queryParams("professorName"));
        AddClass addClasses = new AddClass(repository, receiver);
        addClasses.addClass(myClass);
        return converter.toJson(receiver);
    }
}
