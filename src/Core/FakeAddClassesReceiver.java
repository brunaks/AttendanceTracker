package Core;

/**
 * Created by Bruna Koch Schmitt on 07/08/2016.
 */
public class FakeAddClassesReceiver implements AddClassReceiver {

    public boolean success;

    public void registrationSuccessful() {
        success = true;
    }

    public void registrationFailed() {
        success = false;
    }
}
