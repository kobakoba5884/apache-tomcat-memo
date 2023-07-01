package hello.world;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import hello.world.models.Greeting;

public class HelloWorldServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Greeting greeting = new Greeting("hello");

        ObjectMapper mapper = new ObjectMapper();
        String greetingJson = mapper.writeValueAsString(greeting);

        System.out.println(greetingJson);

        JsonNode greetingJsonNode = mapper.readTree(greetingJson);

        String greetingStr = greetingJsonNode.get("greeting").asText();

        String printGreeting = String.format("<h1>%s</h1>", greetingStr);

        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<body>");
        out.println(printGreeting);
        out.println("</body>");
        out.println("</html>");
    }
}
