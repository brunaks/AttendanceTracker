package main;

import Core.*;
import Core.Persistence.PostgresqlClassRepository;
import Core.Routes.AddClassRoute;
import spark.Spark;

/**
 * Created by Bruna Koch Schmitt on 08/08/2016.
 */
public class MainRoute {

    public static void main(String[] args) {

        Spark.port(Integer.parseInt(System.getenv("PORT")));
        Spark.externalStaticFileLocation("resources/public");

        //ClassRepositoryInMemory classRepositoryInMemory = new ClassRepositoryInMemory();
        PostgresqlClassRepository classRepository = new PostgresqlClassRepository();
        AddClassReceiver addClassesReceiver = new AddClassesReceiver();

        Spark.post("/addClass", new AddClassRoute(classRepository, addClassesReceiver));
        Spark.get("/listClasses", new ReadClassesRoute(classRepository));
    }
}
