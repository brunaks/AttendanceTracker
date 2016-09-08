package Core;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

/**
 * Created by Bruna Koch Schmitt on 08/09/2016.
 */
public class PostgresqlClassRepository implements ClassRepository {

    private Connection connection;

    @Override
    public void add(Class myClass) {
        getConnection();
        String scheduleId = generateID();
        String classId = generateID();
        addSchedule(scheduleId, myClass);
        addClass(scheduleId, classId, myClass);
    }

    private String generateID() {
        return UUID.randomUUID().toString();
    }

    private void addSchedule(String scheduleId, Class myClass) {
        String sql = "insert into schedule (id, start_time, end_time) values (?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, scheduleId);
            stmt.setString(2, myClass.getSchedule().get(0).getStartTime().toString());
            stmt.setString(3, myClass.getSchedule().get(0).getEndTime().toString());
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void addClass(String scheduleId, String classId, Class myClass) {
        String sql = "insert into class (id, name, professor_name, schedule_id) values (?,?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, classId);
            stmt.setString(2, myClass.getName());
            stmt.setString(3, myClass.getProfessorName());
            stmt.setString(4, scheduleId);
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getConnection() {
        ConnectionFactory connectionFactory = new PostgresqlConnectionFactory();
        try {
            connection = connectionFactory.getConnection();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Class> getAll() {
        return null;
    }
}
