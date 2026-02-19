package dao;

import model.Task;
import oracle.jdbc.OracleTypes;
import util.DBConnection;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {

    public List<Task> getAllTasks() {

        List<Task> tasks = new ArrayList<>();

        String sql =
                "{ ? = call TASK_PKG.GET_ALL_TASKS }";

        try (
                Connection conn = DBConnection.getConnection();
                CallableStatement stmt =
                        conn.prepareCall(sql)
        ) {

            stmt.registerOutParameter(1,
                    OracleTypes.CURSOR);

            stmt.execute();

            ResultSet rs =
                    (ResultSet) stmt.getObject(1);

            while (rs.next()) {

                Task task = new Task();

                task.setTaskId(rs.getInt("TASK_ID"));
                task.setTitle(rs.getString("TITLE"));
                task.setDescription(rs.getString("DESCRIPTION"));
                task.setCompleted(rs.getInt("COMPLETED") == 1);
                task.setCreatedAt(rs.getTimestamp("CREATED_AT"));
                task.setUpdatedAt(rs.getTimestamp("UPDATED_AT"));

                tasks.add(task);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return tasks;
    }

    public Task getTaskById(int id) {

        Task task = null;

        String sql =
                "{ ? = call TASK_PKG.GET_TASK_BY_ID(?) }";

        try (
                Connection conn = DBConnection.getConnection();
                CallableStatement stmt =
                        conn.prepareCall(sql)
        ) {

            stmt.registerOutParameter(1,
                    OracleTypes.CURSOR);

            stmt.setInt(2, id);

            stmt.execute();

            ResultSet rs =
                    (ResultSet) stmt.getObject(1);

            if (rs.next()) {

                task = new Task();

                task.setTaskId(rs.getInt("TASK_ID"));
                task.setTitle(rs.getString("TITLE"));
                task.setDescription(rs.getString("DESCRIPTION"));
                task.setCompleted(rs.getInt("COMPLETED") == 1);
                task.setCreatedAt(rs.getTimestamp("CREATED_AT"));
                task.setUpdatedAt(rs.getTimestamp("UPDATED_AT"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return task;
    }

    public int createTask(Task task) {

        String sql =
                "{ call TASK_PKG.CREATE_TASK(?,?,?) }";

        int id = -1;

        try (
                Connection conn = DBConnection.getConnection();
                CallableStatement stmt =
                        conn.prepareCall(sql)
        ) {

            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDescription());

            stmt.registerOutParameter(3,
                    Types.INTEGER);

            stmt.execute();

            id = stmt.getInt(3);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;
    }

    public void updateTask(Task task) {

        String sql =
                "{ call TASK_PKG.UPDATE_TASK(?,?,?,?) }";

        try (
                Connection conn = DBConnection.getConnection();
                CallableStatement stmt =
                        conn.prepareCall(sql)
        ) {

            stmt.setInt(1, task.getTaskId());
            stmt.setString(2, task.getTitle());
            stmt.setString(3, task.getDescription());
            stmt.setInt(4, task.isCompleted() ? 1 : 0);

            stmt.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteTask(int id) {

        String sql =
                "{ call TASK_PKG.DELETE_TASK(?) }";

        try (
                Connection conn = DBConnection.getConnection();
                CallableStatement stmt =
                        conn.prepareCall(sql)
        ) {

            stmt.setInt(1, id);

            stmt.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
