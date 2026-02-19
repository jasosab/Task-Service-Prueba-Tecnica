package servlet;

import com.google.gson.Gson;
import dao.TaskDAO;
import model.Task;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/tasks/*")
public class TaskServlet extends HttpServlet {

    private TaskDAO dao = new TaskDAO();
    private Gson gson = new Gson();

    private void setAccessControlHeaders(HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        setAccessControlHeaders(resp);
        String path = req.getPathInfo();

        try {
            if (path == null || path.equals("/")) {
                List<Task> tasks = dao.getAllTasks();
                resp.getWriter().write(gson.toJson(tasks));
            } else {
                int id = Integer.parseInt(path.substring(1));
                Task task = dao.getTaskById(id);
                if (task != null) {
                    resp.getWriter().write(gson.toJson(task));
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                }
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        setAccessControlHeaders(resp);
        try {
            BufferedReader reader = req.getReader();
            Task task = gson.fromJson(reader, Task.class);

            // Llama a TASK_PKG.CREATE_TASK a través del DAO [cite: 36, 45]
            int id = dao.createTask(task);

            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().write("{\"id\":" + id + "}");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        setAccessControlHeaders(resp);
        try {
            BufferedReader reader = req.getReader();
            Task task = gson.fromJson(reader, Task.class);

            dao.updateTask(task);

            resp.getWriter().write("{\"status\":\"updated\"}");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        resp.setHeader("Access-Control-Allow-Methods", "DELETE, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");

        String path = req.getPathInfo();
        try {
            if (path != null && path.length() > 1) {
                int taskId = Integer.parseInt(path.substring(1));
                
                dao.deleteTask(taskId);

                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write("{\"status\":\"deleted\"}");
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}