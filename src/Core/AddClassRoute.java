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
/*        ClassRequest classRequest = new ClassRequest();
        classRequest.className = request.getString("className");
        classRequest.professsorName = request.getString("professorName");
        ScheduleRequest scheduleRequest = new ScheduleRequest();
        String startTime = request.getString("startTime");
        String endTime = request.getString("endTime");
        String[] startTimeAfterSplit = startTime.split(":");

        String startTimeHours = startTimeAfterSplit[0];
        String startTimeMinutes = startTimeAfterSplit[1].substring(0, 2);
        String startTimePeriod = startTimeAfterSplit

        scheduleRequest.startTimeHours = request.get("startTime").toString().split(":")[0];
        classRequest.schedules[0] = new ScheduleRequest().;*/
        return null;
    }
}
