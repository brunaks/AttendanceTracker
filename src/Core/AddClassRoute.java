package Core;

import com.google.gson.Gson;
import org.json.JSONObject;
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
        AddClass addClasses = new AddClass(repository, receiver);
        JSONObject jsonRequest = new JSONObject(request.body());
        ClassRequest classRequest = convertRequest(jsonRequest);
        addClasses.addClassFromRequest(classRequest);
        return converter.toJson(receiver);
    }

    private ClassRequest convertRequest(JSONObject request) {
        ClassRequest classRequest = new ClassRequest();
        classRequest.className = request.getString("className");
        classRequest.professsorName = request.getString("professorName");

        ScheduleRequest scheduleRequest = new ScheduleRequest();
        String startTime = request.getString("startTime");
        String endTime = request.getString("endTime");

        scheduleRequest.startTimeHours = Integer.parseInt(startTime.substring(0, 2));
        scheduleRequest.startTimeMinutes = Integer.parseInt(startTime.substring(4, 6));
        scheduleRequest.startTimePeriod = Time.TimePeriod.valueOf(startTime.substring(7, 9));

        scheduleRequest.endTimeHours = Integer.parseInt(endTime.substring(0, 2));
        scheduleRequest.endTimeMinutes = Integer.parseInt(endTime.substring(4, 6));
        scheduleRequest.endTimePeriod = Time.TimePeriod.valueOf(endTime.substring(7, 9));

        classRequest.schedules[0] = scheduleRequest;
        return classRequest;
    }
}
