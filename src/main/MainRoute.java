package main;

import Core.AddClassRoute;
import Core.AddClassReceiver;
import Core.ClassRepositoryInMemory;
import Core.FakeAddClassesReceiver;
import spark.Spark;

/**
 * Created by Bruna Koch Schmitt on 08/08/2016.
 */
public class MainRoute {

    public static void main(String[] args) {

        Spark.port(Integer.parseInt(System.getenv("PORT")));
        Spark.externalStaticFileLocation("resources/public");

        ClassRepositoryInMemory classRepositoryInMemory = new ClassRepositoryInMemory();
        AddClassReceiver addClassesReceiver = new FakeAddClassesReceiver();

        Spark.post("/addClass", new AddClassRoute(classRepositoryInMemory, addClassesReceiver));
    }
}
