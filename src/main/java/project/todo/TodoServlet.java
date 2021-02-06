package project.todo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Todo", urlPatterns = {"/api/todos/*"})
public class TodoServlet extends HttpServlet {
    private final Logger logger = LoggerFactory.getLogger(TodoServlet.class);
    private final TodoRepository todoRepository;
    private final ObjectMapper objectMapper;
    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";
    private static final String IOE_EXCEPTION_MESSAGE = "Read from service exception {}";

    public TodoServlet(TodoRepository todoRepository, ObjectMapper objectMapper) {
        this.todoRepository = todoRepository;
        this.objectMapper = objectMapper;
    }

    /**
     * Servlet container needs it
     */
    @SuppressWarnings("unused")
    public TodoServlet() {
        this(new TodoRepository(), new ObjectMapper());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        logger.info("Got request with parameters {}", req.getParameterMap());
        resp.setContentType(CONTENT_TYPE);
        try {
            objectMapper.writeValue(resp.getOutputStream(), todoRepository.findAll());
        } catch (IOException e) {
            logger.warn(IOE_EXCEPTION_MESSAGE, e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        Integer id = null;
        try {
            id = Integer.valueOf(req.getPathInfo().substring(1));
            var todo = todoRepository.toggleTodo(id);
            resp.setContentType(CONTENT_TYPE);
            objectMapper.writeValue(resp.getOutputStream(), todo);
        } catch (NumberFormatException e) {
            logger.warn("Wrong path used: {}", id);
        } catch (IOException e) {
            logger.warn(IOE_EXCEPTION_MESSAGE, e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            var newTodo = objectMapper.readValue(req.getInputStream(), Todo.class);
            resp.setContentType(CONTENT_TYPE);
            objectMapper.writeValue(resp.getOutputStream(), todoRepository.addTodo(newTodo));
        } catch (IOException e) {
            logger.warn(IOE_EXCEPTION_MESSAGE, e.getMessage());
        }
    }
}
