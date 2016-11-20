package Core.Routes;

import Core.Persistence.ClassRepository;
import Core.Persistence.PostgresqlClassRepository;
import Core.UseCases.ReadClasses;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.List;

/**
 * Created by Bruna Koch Schmitt on 17/10/2016.
 */
public class ReadClassesRoute implements Route {

    private ClassRepository classRepository;

    public ReadClassesRoute(ClassRepository classRepository) {
        this.classRepository = classRepository;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        ReadClasses readClasses = new ReadClasses(classRepository);
        List classes = readClasses.getAll();
        Gson converter = new Gson();
        return converter.toJson(classes);
    }
}
