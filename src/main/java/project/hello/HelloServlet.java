package project.hello;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Hello", urlPatterns = {"/api"})
public class HelloServlet extends HttpServlet {
  private final Logger logger = LoggerFactory.getLogger(HelloServlet.class);
  private static final String NAME_PARAM = "name";
  private static final String LANG_PARAM = "lang";
  private final HelloService service;

    /**
     * Servlet container needs it
     */
  @SuppressWarnings("unused")
  public HelloServlet(){
      this(new HelloService());
  }

  public HelloServlet(HelloService service){
      this.service = service;
  }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("Got request with parameters {}", req.getParameterMap());
        var name = req.getParameter(NAME_PARAM);
        var lang = req.getParameter(LANG_PARAM);
        Integer langId = null;
        try {
            langId = Integer.valueOf(lang);
        } catch (NumberFormatException e) {
            logger.warn("Non-numeric language id used: {}", lang);
        }
        try {
            resp.getWriter()
                    .write(service.prepareGreeting(name, langId));
        }catch (IOException e){
            logger.warn("Read from service exception {}", e.getMessage());
        }
    }
}
