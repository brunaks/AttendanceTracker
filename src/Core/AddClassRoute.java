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
//        AddClass addClasses = new AddClass(repository, receiver);
//        addClasses.addClass(classRequest);
//
//        ClassRequest classRequest = converter.fromJson(request.body(), ClassRequest.class);
//        return converter.toJson(receiver);
        return "";
    }
}
