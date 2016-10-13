package Core;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Bruna Koch Schmitt on 08/09/2016.
 */
public class PostgresqlClassRepository implements ClassRepository {

    @Override
    public void add(Class myClass) {
        getConnection();
        String scheduleId = generateID();
        String classId = generateID();
        addClass(classId, myClass);
        addSchedule(scheduleId, myClass, classId);
    }

    private String generateID() {
        return UUID.randomUUID().toString();
    }

    // Need to fix the insert
    private void addSchedule(String scheduleId, Class myClass, String classId) {
        String sql = "insert into schedule (id, start_time, end_time, day, class_id) values (?,?,?,?,?)";
        try {
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            stmt.setString(1, scheduleId);
            stmt.setString(2, myClass.getSchedule().get(0).getStartTime().toString());
            stmt.setString(3, myClass.getSchedule().get(0).getEndTime().toString());
            stmt.setString(4, myClass.getSchedule().get(0).getDay().getCode());
            stmt.setString(5, classId);
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void addClass(String classId, Class myClass) {
        String sql = "insert into class (id, name, professor_name) values (?,?,?)";
        try {
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            stmt.setString(1, classId);
            stmt.setString(2, myClass.getName());
            stmt.setString(3, myClass.getProfessorName());
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection getConnection() {
        ConnectionFactory connectionFactory = new PostgresqlConnectionFactory();
        try {
            return connectionFactory.getConnection();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Class> getAll() {
        String sql = "select * from class";
        ResultSet result;
        try {
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            result = stmt.executeQuery();
            List<Class> classes = buildClassInfo(result);
            return buildSchedules(classes);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Class> buildSchedules(List<Class> classes) {
        for (Class aClass : classes) {
            ResultSet result = getSchedulesById(aClass.getId());
            try {
                while (result.next()) {
                    Schedule schedule = new Schedule();
                    String startHour = result.getString("start_time").substring(0, 2);
                    String startMinutes = result.getString("start_time").substring(3, 5);
                    Time.TimePeriod startPeriod = Time.TimePeriod.valueOf(result.getString("start_time").substring(6));
                    schedule.addStartTime(Integer.parseInt(startHour), Integer.parseInt(startMinutes), startPeriod);

                    String endHour = result.getString("end_time").substring(0, 2);
                    String endMinutes = result.getString("end_time").substring(3, 5);
                    Time.TimePeriod endPeriod = Time.TimePeriod.valueOf(result.getString("end_time").substring(6));
                    schedule.addEndTime(Integer.parseInt(endHour), Integer.parseInt(endMinutes), endPeriod);

                    schedule.addDay(Schedule.Days.getFromCode(result.getString("day")));

                    aClass.addSchedule(schedule);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return classes;
    }

    private ResultSet getSchedulesById(String classId) {
        String sql = "select * from schedule where class_id = ?";
        try {
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            stmt.setString(1, classId);
            return stmt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Class> buildClassInfo(ResultSet result) {
        ArrayList<Class> classes = new ArrayList<>();
        try {
            while (result.next()) {
                Class aClass = new Class();
                aClass.setName(result.getString("name"));
                aClass.setProfessorName(result.getString("professor_name"));
                aClass.setId(result.getString("id"));
                classes.add(aClass);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return classes;
    }
}
