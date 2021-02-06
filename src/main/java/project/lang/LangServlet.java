package project.lang;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "Langs", urlPatterns = {"/api/langs"})
public class LangServlet extends HttpServlet {
  private final Logger logger = LoggerFactory.getLogger(LangServlet.class);

    private final LangService service;
    private final ObjectMapper objectMapper;

    /**
     * Servlet container needs it
     */
  @SuppressWarnings("unused")
  public LangServlet(){
      this(new LangService(), new ObjectMapper());
  }

  public LangServlet(LangService service, ObjectMapper objectMapper){
      this.service = service;
      this.objectMapper = objectMapper;
  }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("Got request with parameters {}", req.getParameterMap());
        resp.setContentType("application/json;charset=UTF-8");
        try {
            objectMapper.writeValue(resp.getOutputStream(), service.findAll());
        }catch (IOException e){
            logger.warn("Read from service exception {}", e.getMessage());
        }
    }
}
